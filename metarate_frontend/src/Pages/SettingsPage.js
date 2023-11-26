import { useEffect, useState } from "react";
import "../css/SettingsPage.css";
import TokenManager from "../APIs/TokenManager";
import UserAPI from "../APIs/UserAPI";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

const SettingsPage = (props) => {
  const [profileUrl, setProfileUrl] = useState(null);
  const [username, setUsername] = useState("");
  const [user, setUser] = useState(null);
  const [claims, setClaims] = useState(TokenManager.getClaims());
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const navigate = useNavigate();

  const passwordsMatch = newPassword === confirmPassword;
  const showPasswordsMatching = !passwordsMatch && newPassword !== "" && confirmPassword !== "";

  const toastMessage = sessionStorage.getItem("toastSuccess");
  if(toastMessage) {
    toast.success(toastMessage);
    sessionStorage.removeItem("toastSuccess");
  }

  useEffect(() => {
    setClaims(TokenManager.getClaims());
    if (claims) {
      UserAPI.getUser(claims.id)
        .then((userData) => {
          setUser(userData);
          setProfileUrl(userData.profilePic || ""); // Prefill profile picture URL if available
          setUsername(userData.username || ""); // Prefill username if available
        })
        .catch((error) => console.error(error));
    }
  }, []);

  const handleUrlChange = (event) => {
    setProfileUrl(event.target.value);
  };

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault()
    const userData = {
      "username": username,
      "profilePic": profileUrl
    };

    UserAPI.updateUser(user.id, userData)
      .then(() => {
        sessionStorage.setItem('username', userData.username);
        sessionStorage.setItem('toastSuccess', 'Information updated successfully!');
        window.location.reload();
      })
      .catch((error) => {
        console.error("Error updating user:", error);
        toast.error("Error updating user!");
      });
  }

  const handlePasswordSubmit = (e) => {
    e.preventDefault();
    if (passwordsMatch) {
      const passwordData = {
        "oldPassword": currentPassword,
        "newPassword": newPassword
      }

      console.log(passwordData);

      UserAPI.updateUserPassword(user.id, passwordData)
        .then(() => {
          sessionStorage.setItem('toastSuccess', 'Password updated successfully!');
          window.location.reload();
        })
        .catch(() => {
          toast.error("Current password is incorrect!");
        });
  }
  else if (!passwordsMatch) {
    toast.error("Passwords do not match!");
  }
}

  return (
    <section id="settings">
      <form className="settings-container" onSubmit={handleSubmit}>
          <h2>Account Settings</h2>
          <div className="settings-row">
            <div className="settings-column">
              <h3 className="label-for-pfp">Profile picture</h3>
              <input
                type="text"
                className="settings-input"
                placeholder="Enter URL here"
                value={profileUrl || ""}
                onChange={handleUrlChange}
                required
              />
            </div>
            <div className="settings-column">
              <h3>Preview</h3>
              <img src={profileUrl} alt="No image available" />
            </div>
            <div className="settings-column">
              <h3>Username</h3>
              <input
                type="text"
                className="settings-input"
                placeholder="Username"
                value={username}
                onChange={handleUsernameChange}
                required
              />
            </div>
            <div className="settings-confirm">
                <button type="submit" className="settings-button">Save Changes</button>
              </div>
          </div>
        </form>
        <form className="settings-container" onSubmit={handlePasswordSubmit}>
          <h2>Change Password</h2>
              <div className="settings-row">
                <div className="settings-column">
                  <h3>Password</h3>
                  <input 
                    type="password"
                    className="settings-input"
                    placeholder="Current Password"
                    onChange={(e) => setCurrentPassword(e.target.value)}
                    required/>
                </div>
                <div className="settings-column">
                  <h3></h3>
                  <input 
                    type="password"
                    className="settings-input"
                    placeholder="New Password"
                    onChange={(e) => setNewPassword(e.target.value)}
                    required/>
                </div>
                <div className="settings-column">
                  <h3></h3>
                  <div>
                  <input 
                    type="password"
                    className="settings-input"
                    placeholder="Confirm Password"
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    required/>
                    {showPasswordsMatching && <p className="passwords-matching">Passwords do not match</p>}
                  </div>
                </div>
                <div className="settings-confirm">
                <button type="submit" className="settings-button">Save Password</button>
              </div>
            </div>
        </form>
    </section>
  );
}

export default SettingsPage;
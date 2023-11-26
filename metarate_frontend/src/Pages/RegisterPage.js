import { useState } from "react";
import { useNavigate } from "react-router-dom";
import UserAPI from "../APIs/UserAPI";
import "../css/loginpage.css";
import { ToastContainer, toast } from "react-toastify";


function RegisterPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const navigate = useNavigate();

  const passwordsMatch = password === confirmPassword;
  const showPasswordsMatching = !passwordsMatch && password !== "" && confirmPassword !== "";

  const handleSubmit = (e) => {
    e.preventDefault();
    if (passwordsMatch) {
      const userData = {
        username: username,
        password: password
      };

      UserAPI.createUser(userData)
        .then(() => {
          // User created successfully, navigate to "/login"
          toast.success("Account created successfully!");
          navigate("/login");
        })
        .catch((error) => {
          // Handle error during user creation (e.g., display error message)
          console.error("Error creating user:", error);
        });
    }
    else if (!passwordsMatch) {
      toast.error("Passwords do not match!");
    }
  };

  return (
    <div className="container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Create an account</h2>
        <div className="form-group">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Confirm password:</label>
          <input
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
          />
          {showPasswordsMatching && (
            <div className="passwords-matching">Passwords do not match</div>
          )}
        </div>
        <button className="loginbtn" type="submit">
          Create Account
        </button>
      </form>
    </div>
  );
}

export default RegisterPage;

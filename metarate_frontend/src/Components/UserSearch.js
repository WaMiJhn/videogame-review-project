import React, { useState, useEffect } from 'react';
import Select from 'react-select';
import UserAPI from '../APIs/UserAPI';

function UserSearch({ onSearch }) {
  const [searchValue, setSearchValue] = useState('');
  const [options, setOptions] = useState([]);
  const [selectedOption, setSelectedOption] = useState(null);

  useEffect(() => {
    UserAPI.getUsers()
      .then((users) => {
        const options = users.map((user) => ({
          value: user.id,
          label: user.username,
        }));
        setOptions(options);
      })
      .catch((error) => {
        console.error('Error fetching users:', error);
      });
  }, []);

  const handleSearchChange = (selectedOption) => {
    setSelectedOption(selectedOption);
    setSearchValue(selectedOption?.label || '');
    onSearch(selectedOption?.label || '');
  };

  return (
    <div className="user-searchbar">
      <Select
        className="user-dropdown"
        options={options}
        onChange={handleSearchChange}
        placeholder="Search user"
        value={selectedOption}
        isClearable
      />
    </div>
  );
}

export default UserSearch;

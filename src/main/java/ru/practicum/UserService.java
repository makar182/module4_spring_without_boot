package ru.practicum;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto saveUser(UserDto userDto);

    List<UserShortWithIP> getUsersEmailWithIp(String emailSearch);
}
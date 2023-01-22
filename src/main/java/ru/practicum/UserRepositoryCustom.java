package ru.practicum;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserShortWithIP> findAllByEmailContainingIgnoreCaseWithIP(String emailSearch);
}
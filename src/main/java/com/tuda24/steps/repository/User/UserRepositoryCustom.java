package com.tuda24.steps.repository.User;

import com.tuda24.steps.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findByUsernameWithRoles(String username);

    Optional<User> findByPhoneNumberNative(String phoneNumber);
}

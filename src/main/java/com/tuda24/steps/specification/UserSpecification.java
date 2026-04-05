package com.tuda24.steps.specification;

import com.tuda24.steps.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasUsername(String username) {
        return (root, query, cb) -> username == null ? null :
                cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, cb) -> email == null ? null :
                cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<User> hasPhoneNumber(String phoneNumber) {
        return (root, query, cb) -> phoneNumber == null ? null :
                cb.like(root.get("phoneNumber"), "%" + phoneNumber + "%");
    }

    public static Specification<User> isActive(Boolean active) {
        return (root, query, cb) -> active == null ? null :
                cb.equal(root.get("active"), active);
    }

    public static Specification<User> hasRole(String roleName) {
        return (root, query, cb) -> {
            if (roleName == null) return null;
            var roles = root.join("roles");
            return cb.like(cb.lower(roles.get("name")), "%" + roleName.toLowerCase() + "%");
        };
    }
}

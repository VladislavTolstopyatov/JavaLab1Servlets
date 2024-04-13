package entities;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
    ADMIN,
    USER;

    public static Optional<Role> find(final String role) {
        return Arrays.stream(Role.values())
                .filter(userRole -> userRole.name().equals(role))
                .findFirst();
    }
}

package fontys.metarate.business;

import fontys.metarate.domain.user.User;

import java.util.Optional;

public interface GetUserUseCase {
    Optional<User> getUser(Long userId);
}

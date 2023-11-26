package fontys.metarate.business;

import fontys.metarate.domain.user.CreateUserRequest;
import fontys.metarate.domain.user.CreateUserResponse;

public interface CreateUserUseCase {
    CreateUserResponse createUser(CreateUserRequest request);
}

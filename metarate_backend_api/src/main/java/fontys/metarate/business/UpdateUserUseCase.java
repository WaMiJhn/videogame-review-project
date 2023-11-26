package fontys.metarate.business;

import fontys.metarate.domain.user.UpdateUserRequest;

public interface UpdateUserUseCase {
    void updateUser(UpdateUserRequest request);
}

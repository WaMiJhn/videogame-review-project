package fontys.metarate.business;

import fontys.metarate.domain.user.UpdatePasswordRequest;

public interface UpdatePasswordUseCase {
    void updatePassword(UpdatePasswordRequest request);
}

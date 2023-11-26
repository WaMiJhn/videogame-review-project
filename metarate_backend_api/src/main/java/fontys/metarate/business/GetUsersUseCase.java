package fontys.metarate.business;

import fontys.metarate.domain.user.GetAllUsersRequest;
import fontys.metarate.domain.user.GetAllUsersResponse;

public interface GetUsersUseCase {
    GetAllUsersResponse getUsers(GetAllUsersRequest request);
}

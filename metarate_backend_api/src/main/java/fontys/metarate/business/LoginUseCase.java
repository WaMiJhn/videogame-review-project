package fontys.metarate.business;

import fontys.metarate.domain.login.LoginRequest;
import fontys.metarate.domain.login.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest request);
}

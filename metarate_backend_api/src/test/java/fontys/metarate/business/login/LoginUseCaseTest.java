package fontys.metarate.business.login;

import fontys.metarate.business.AccessTokenEncoder;
import fontys.metarate.business.exception.InvalidCredentialsException;
import fontys.metarate.business.impl.LoginUseCaseImpl;
import fontys.metarate.domain.AccessToken;
import fontys.metarate.domain.login.LoginRequest;
import fontys.metarate.domain.login.LoginResponse;
import fontys.metarate.persistence.UserRepository;
import fontys.metarate.persistence.entity.RoleEnum;
import fontys.metarate.persistence.entity.UserEntity;
import fontys.metarate.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    @Test
    void testLogin_ValidCredentials() {
        // Prepare
        LoginRequest loginRequest = new LoginRequest("username", "password");
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("username");
        userEntity.setPassword("encodedPassword");
        UserRoleEntity userRole = UserRoleEntity.builder()
                .role(RoleEnum.USER)
                .user(userEntity)
                .build();
        userEntity.setUserRoles(Set.of(userRole));

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(userEntity);
        when(passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())).thenReturn(true);
        when(accessTokenEncoder.encode(any(AccessToken.class))).thenReturn("accessToken");

        // Act
        LoginResponse response = loginUseCase.login(loginRequest);

        // Assert
        assertEquals("accessToken", response.getAccessToken());
    }

    @Test
    void testLogin_InvalidUsername() {
        // Prepare
        LoginRequest loginRequest = new LoginRequest("nonExistingUser", "password");
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(null);

        // Act and Assert
        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(loginRequest));
    }

    @Test
    void testLogin_InvalidPassword() {
        // Prepare
        LoginRequest loginRequest = new LoginRequest("username", "wrongPassword");
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("username");
        userEntity.setPassword("encodedPassword");

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(userEntity);
        when(passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())).thenReturn(false);

        // Act and Assert
        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(loginRequest));
    }
}


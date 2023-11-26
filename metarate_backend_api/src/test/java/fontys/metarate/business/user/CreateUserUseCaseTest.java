package fontys.metarate.business.user;

import fontys.metarate.business.exception.UsernameAlreadyExistsException;
import fontys.metarate.business.impl.user.CreateUserUseCaseImpl;
import fontys.metarate.domain.user.CreateUserRequest;
import fontys.metarate.domain.user.CreateUserResponse;
import fontys.metarate.persistence.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    void testCreateUser_Success() {
        // Prepare
        CreateUserRequest request = new CreateUserRequest("username", "password");

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        // Act
        CreateUserResponse response = createUserUseCase.createUser(request);

        // Assert
        assertNotNull(response);
        // Add additional assertions if necessary
    }

    @Test
    void testCreateUser_UsernameAlreadyExists() {
        // Prepare
        CreateUserRequest request = new CreateUserRequest("username", "password");

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        // Act & Assert
        assertThrows(UsernameAlreadyExistsException.class, () -> createUserUseCase.createUser(request));
    }
}

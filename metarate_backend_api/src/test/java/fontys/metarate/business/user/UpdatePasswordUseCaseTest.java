package fontys.metarate.business.user;

import fontys.metarate.business.exception.UnauthorizedDataAccessException;
import fontys.metarate.business.exception.UserNotFoundException;
import fontys.metarate.business.impl.user.UpdatePasswordUseCaseImpl;
import fontys.metarate.domain.AccessToken;
import fontys.metarate.domain.user.UpdatePasswordRequest;
import fontys.metarate.persistence.UserRepository;
import fontys.metarate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePasswordUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessToken requestAccessToken;

    @InjectMocks
    private UpdatePasswordUseCaseImpl updatePasswordUseCase;

    @Test
    void updatePassword_shouldThrowUserNotFoundExceptionWhenUserNotFound() {
        // Arrange
        Long userId = 1L;
        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> updatePasswordUseCase.updatePassword(request));
        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(userRepository, passwordEncoder, requestAccessToken);
    }

    @Test
    void updatePassword_shouldThrowUnauthorizedDataAccessExceptionWhenUserNotOwnedByUser() {
        // Arrange
        Long userId = 1L;
        Long loggedInUserId = 2L;

        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setId(userId);

        UserEntity existingUser = createUserEntity(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(requestAccessToken.getUserId()).thenReturn(loggedInUserId);

        // Act & Assert
        assertThrows(UnauthorizedDataAccessException.class, () -> updatePasswordUseCase.updatePassword(request));
        verify(userRepository, times(1)).findById(userId);
        verify(requestAccessToken, times(1)).getUserId();
        verifyNoMoreInteractions(userRepository, passwordEncoder, requestAccessToken);
    }

    @Test
    void updatePassword_shouldThrowUnauthorizedDataAccessExceptionWhenPasswordNotMatching() {
        // Arrange
        Long userId = 1L;
        Long loggedInUserId = 1L;

        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setId(userId);
        request.setOldPassword("oldPassword");
        request.setNewPassword("newPassword");

        UserEntity existingUser = createUserEntity(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(requestAccessToken.getUserId()).thenReturn(loggedInUserId);
        when(passwordEncoder.matches(request.getOldPassword(), existingUser.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(UnauthorizedDataAccessException.class, () -> updatePasswordUseCase.updatePassword(request));
        verify(userRepository, times(1)).findById(userId);
        verify(requestAccessToken, times(1)).getUserId();
        verify(passwordEncoder, times(1)).matches(request.getOldPassword(), existingUser.getPassword());
        verifyNoMoreInteractions(userRepository, passwordEncoder, requestAccessToken);
    }

    // Helper method to create a UserEntity
    private UserEntity createUserEntity(Long userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setUsername("username");
        userEntity.setPassword("password");
        userEntity.setProfilePic("profilePic");
        return userEntity;
    }
}
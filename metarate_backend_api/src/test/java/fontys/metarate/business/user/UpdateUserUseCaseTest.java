package fontys.metarate.business.user;

import fontys.metarate.business.exception.UnauthorizedDataAccessException;
import fontys.metarate.business.exception.UserNotFoundException;
import fontys.metarate.business.impl.user.UpdateUserUseCaseImpl;
import fontys.metarate.domain.AccessToken;
import fontys.metarate.domain.user.UpdateUserRequest;
import fontys.metarate.persistence.UserRepository;
import fontys.metarate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccessToken requestAccessToken;

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    @Test
    void updateUser_shouldThrowUserNotFoundExceptionWhenUserNotFound() {
        // Arrange
        Long userId = 1L;
        UpdateUserRequest request = new UpdateUserRequest();
        request.setId(userId);
        request.setUsername("newUsername");
        request.setProfilePic("newProfilePic");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> updateUserUseCase.updateUser(request));
        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUser_shouldThrowUnauthorizedDataAccessExceptionWhenUserNotOwnedByUser() {
        // Arrange
        Long userId = 1L;
        Long loggedInUserId = 2L;
        UpdateUserRequest request = new UpdateUserRequest();
        request.setId(userId);
        request.setUsername("newUsername");
        request.setProfilePic("newProfilePic");

        UserEntity existingUser = createUserEntity(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(requestAccessToken.getUserId()).thenReturn(loggedInUserId);

        // Act & Assert
        assertThrows(UnauthorizedDataAccessException.class, () -> updateUserUseCase.updateUser(request));
        verify(userRepository, times(1)).findById(userId);
        verify(requestAccessToken, times(1)).getUserId();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUser_shouldUpdateUserFields() {
        // Arrange
        Long userId = 1L;
        Long loggedInUserId = 1L;
        UpdateUserRequest request = new UpdateUserRequest();
        request.setId(userId);
        request.setUsername("newUsername");
        request.setProfilePic("newProfilePic");

        UserEntity existingUser = createUserEntity(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(requestAccessToken.getUserId()).thenReturn(loggedInUserId);

        // Act
        updateUserUseCase.updateUser(request);

        // Assert
        verify(userRepository, times(1)).findById(userId);
        verify(requestAccessToken, times(1)).getUserId();
        verify(userRepository, times(1)).save(existingUser);
        assertEquals(request.getUsername(), existingUser.getUsername());
        assertEquals(request.getProfilePic(), existingUser.getProfilePic());
    }

    private UserEntity createUserEntity(Long userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setUsername("oldUsername");
        userEntity.setProfilePic("oldProfilePic");
        return userEntity;
    }
}


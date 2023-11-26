package fontys.metarate.business.user;

import fontys.metarate.business.exception.UserNotFoundException;
import fontys.metarate.business.impl.user.GetUserUseCaseImpl;
import fontys.metarate.domain.user.User;
import fontys.metarate.persistence.UserRepository;
import fontys.metarate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserUseCaseImpl getUserUseCase;

    @Test
    void testGetUser_ExistingUser() {
        // Prepare
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("user1")
                .password("password1")
                .profilePic("pic1")
                .build();
        Optional<UserEntity> optionalUserEntity = Optional.of(userEntity);

        when(userRepository.findById(1L)).thenReturn(optionalUserEntity);

        // Act
        Optional<User> userOptional = getUserUseCase.getUser(1L);

        // Assert
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();
        assertEquals(userEntity.getId(), user.getId());
        assertEquals(userEntity.getUsername(), user.getUsername());
        assertEquals(userEntity.getProfilePic(), user.getProfilePic());

    }


    @Test
    void testGetUser_ThrowsUserNotFoundException() {
        // Prepare
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> getUserUseCase.getUser(1L));
    }
}

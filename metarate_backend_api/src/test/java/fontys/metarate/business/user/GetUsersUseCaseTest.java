package fontys.metarate.business.user;

import fontys.metarate.business.impl.user.GetUsersUseCaseImpl;
import fontys.metarate.domain.user.GetAllUsersRequest;
import fontys.metarate.domain.user.GetAllUsersResponse;
import fontys.metarate.persistence.UserRepository;
import fontys.metarate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUsersUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUsersUseCaseImpl getUsersUseCase;

    @Test
    void testGetUsers() {
        // Prepare
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(UserEntity.builder()
                .id(1L)
                .username("user1")
                .password("password1")
                .profilePic("pic1")
                .build());
        userEntities.add(UserEntity.builder()
                .id(2L)
                .username("user2")
                .password("password2")
                .profilePic("pic2")
                .build());

        when(userRepository.findAll()).thenReturn(userEntities);

        // Act
        GetAllUsersResponse response = getUsersUseCase.getUsers(new GetAllUsersRequest());

        // Assert
        assertNotNull(response);
        assertNotNull(response.getUsers());
        assertEquals(2, response.getUsers().size());
    }
}

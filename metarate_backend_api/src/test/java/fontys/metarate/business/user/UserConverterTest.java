package fontys.metarate.business.user;

import fontys.metarate.business.impl.user.UserConverter;
import fontys.metarate.domain.user.User;
import fontys.metarate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserConverterTest {

    @Test
    void testConvert() {
        // Prepare
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("user1")
                .password("password1")
                .profilePic("pic1")
                .build();
        // Act
        User user = UserConverter.convert(userEntity);

        // Assert
        assertEquals(userEntity.getId(), user.getId());
        assertEquals(userEntity.getUsername(), user.getUsername());
        assertEquals(userEntity.getProfilePic(), user.getProfilePic());
    }
}


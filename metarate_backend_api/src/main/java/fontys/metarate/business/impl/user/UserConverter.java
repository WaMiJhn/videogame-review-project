package fontys.metarate.business.impl.user;

import fontys.metarate.domain.user.User;
import fontys.metarate.persistence.entity.UserEntity;

public final class UserConverter {
    private UserConverter() {
    }
    public static User convert(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .profilePic(userEntity.getProfilePic())
                .build();
    }
}

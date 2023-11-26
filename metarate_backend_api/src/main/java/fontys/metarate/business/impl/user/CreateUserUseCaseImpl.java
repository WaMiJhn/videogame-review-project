package fontys.metarate.business.impl.user;

import fontys.metarate.business.CreateUserUseCase;
import fontys.metarate.business.exception.UsernameAlreadyExistsException;
import fontys.metarate.domain.user.CreateUserRequest;
import fontys.metarate.domain.user.CreateUserResponse;
import fontys.metarate.persistence.UserRepository;
import fontys.metarate.persistence.entity.RoleEnum;
import fontys.metarate.persistence.entity.UserEntity;
import fontys.metarate.persistence.entity.UserRoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity newUser = UserEntity.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .profilePic("https://soccerpointeclaire.com/wp-content/uploads/2021/06/default-profile-pic-e1513291410505.jpg")
                .build();
        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(RoleEnum.USER)
                        .build()));
        userRepository.save(newUser);

        return CreateUserResponse.builder()
                .id(newUser.getId())
                .build();
    }
}


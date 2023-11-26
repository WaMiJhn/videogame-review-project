package fontys.metarate.business.impl.user;

import fontys.metarate.business.UpdateUserUseCase;
import fontys.metarate.business.exception.UnauthorizedDataAccessException;
import fontys.metarate.business.exception.UserNotFoundException;
import fontys.metarate.domain.AccessToken;
import fontys.metarate.domain.user.UpdateUserRequest;
import fontys.metarate.persistence.UserRepository;
import fontys.metarate.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final AccessToken requestAccessToken;

    @Override
    public void updateUser(UpdateUserRequest request) {
        Optional<UserEntity> userOptional = userRepository.findById(request.getId());
        UserEntity user = userOptional.orElseThrow(UserNotFoundException::new);

        // Check if the user belongs to the logged-in user
        if (!requestAccessToken.getUserId().equals(user.getId())) {
            throw new UnauthorizedDataAccessException("USER_NOT_OWNED_BY_USER");
        }
        updateFields(request, user);
    }

    private void updateFields(UpdateUserRequest request, UserEntity user) {
        user.setUsername(request.getUsername());
        user.setProfilePic(request.getProfilePic());

        userRepository.save(user);
    }
}

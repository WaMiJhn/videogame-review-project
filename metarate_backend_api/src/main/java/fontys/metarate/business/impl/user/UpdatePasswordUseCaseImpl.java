package fontys.metarate.business.impl.user;

import fontys.metarate.business.UpdatePasswordUseCase;
import fontys.metarate.business.exception.UnauthorizedDataAccessException;
import fontys.metarate.business.exception.UserNotFoundException;
import fontys.metarate.domain.AccessToken;
import fontys.metarate.domain.user.UpdatePasswordRequest;
import fontys.metarate.persistence.UserRepository;
import fontys.metarate.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdatePasswordUseCaseImpl implements UpdatePasswordUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessToken requestAccessToken;

    @Override
    public void updatePassword(UpdatePasswordRequest request) {
        Optional<UserEntity> userOptional = userRepository.findById(request.getId());
        UserEntity user = userOptional.orElseThrow(UserNotFoundException::new);

        // Check if the user belongs to the logged-in user
        if (!requestAccessToken.getUserId().equals(user.getId())) {
            throw new UnauthorizedDataAccessException("USER_NOT_OWNED_BY_USER");
        }

        // Check if the old password matches
        if (!matchesPassword(request.getOldPassword(), user.getPassword())) {
            throw new UnauthorizedDataAccessException("PASSWORD_NOT_MATCHING");
        }

        updateFields(request, user);
    }

    private boolean matchesPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    private void updateFields(UpdatePasswordRequest request, UserEntity user) {
        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());

        user.setPassword(encodedNewPassword);
        userRepository.save(user);
    }

}

package fontys.metarate.business.impl.user;

import fontys.metarate.business.GetUserUseCase;
import fontys.metarate.business.exception.UserNotFoundException;
import fontys.metarate.domain.user.User;
import fontys.metarate.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private UserRepository userRepository;

    @Override
    public Optional<User> getUser(Long userId) {
        return Optional.of(userRepository.findById(userId)
                .map(UserConverter::convert)
                .orElseThrow(UserNotFoundException::new));
    }
}

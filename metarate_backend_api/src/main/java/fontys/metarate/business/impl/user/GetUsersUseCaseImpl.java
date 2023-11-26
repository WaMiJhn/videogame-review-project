package fontys.metarate.business.impl.user;

import fontys.metarate.business.GetUsersUseCase;
import fontys.metarate.domain.AccessToken;
import fontys.metarate.domain.user.GetAllUsersRequest;
import fontys.metarate.domain.user.GetAllUsersResponse;
import fontys.metarate.domain.user.User;
import fontys.metarate.persistence.UserRepository;
import fontys.metarate.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetUsersUseCaseImpl implements GetUsersUseCase {
    private final UserRepository userRepository;

    @Override
    public GetAllUsersResponse getUsers(GetAllUsersRequest request) {
        List<User> users;
        if (request.getUsername() != null) {
            UserEntity userEntity = userRepository.findByUsername(request.getUsername());
            if (userEntity != null) {
                users = List.of(UserConverter.convert(userEntity));
            } else {
                users = List.of();
            }
        } else {
            users = userRepository.findAll().stream()
                    .map(UserConverter::convert)
                    .toList();
        }

        final GetAllUsersResponse response = new GetAllUsersResponse();
        response.setUsers(users);
        return response;
    }

}




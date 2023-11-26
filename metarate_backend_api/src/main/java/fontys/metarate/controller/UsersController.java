package fontys.metarate.controller;

import fontys.metarate.business.*;
import fontys.metarate.configuration.security.isauthenticated.IsAuthenticated;
import fontys.metarate.domain.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UsersController {
    private final GetUsersUseCase getUsersUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;

    @GetMapping
    public ResponseEntity<GetAllUsersResponse> getAllUsers(@RequestParam(required = false) String username) {
        GetAllUsersRequest request = GetAllUsersRequest.builder()
                .username(username)
                .build();
        GetAllUsersResponse response = getUsersUseCase.getUsers(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping({"{userId}"})
    public ResponseEntity<User> getUser(@PathVariable(value = "userId") final long id) {
        final Optional<User> userOptional = getUserUseCase.getUser(id);
        return userOptional.map(user -> ResponseEntity.ok().body(user)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @IsAuthenticated
    @Transactional
    @PutMapping("{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable(value = "userId") final Long id,
                                           @RequestBody @Valid UpdateUserRequest request) {
        request.setId(id);
        updateUserUseCase.updateUser(request);
        return ResponseEntity.noContent().build();
    }

    @IsAuthenticated
    @Transactional
    @PutMapping("{userId}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable(value = "userId") final Long id,
                                               @RequestBody @Valid UpdatePasswordRequest request) {
        request.setId(id);
        updatePasswordUseCase.updatePassword(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        CreateUserResponse response = createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

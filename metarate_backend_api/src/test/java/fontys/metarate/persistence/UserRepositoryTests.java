package fontys.metarate.persistence;

import fontys.metarate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        // Create a user
        UserEntity user = createUser("john", "password");

        // Save the user
        userRepository.save(user);

        // Find the user by username
        UserEntity foundUser = userRepository.findByUsername("john");

        // Verify the user is found
        assertNotNull(foundUser);
        assertEquals("john", foundUser.getUsername());
    }

    @Test
    void testExistsByUsername() {
        // Create a user
        UserEntity user = createUser("john", "password");

        // Save the user
        userRepository.save(user);

        // Check if the user exists by username
        boolean exists = userRepository.existsByUsername("john");

        // Verify the existence of the user
        assertTrue(exists);
    }

    private UserEntity createUser(String username, String password) {
        return UserEntity.builder()
                .username(username)
                .password(password)
                .build();
    }
}

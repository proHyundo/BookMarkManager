Sure, I can help you with that. Here's an example of how you can write unit tests for the UserServiceImpl class using JUnit 5 and Mockito in a Spring Boot project:

java
Copy code
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenizer jwtTokenizer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, passwordEncoder, jwtTokenizer);
    }

    @Test
    public void testFindUserById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.findUserById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        when(userRepository.save(user)).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        User result = userService.saveUser(user);
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    public void testGenerateToken() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        when(jwtTokenizer.generateToken(user)).thenReturn("token");
        String result = userService.generateToken(user);
        assertNotNull(result);
        assertEquals("token", result);
    }

}
In this example, we use @Mock to create mock objects for UserRepository, PasswordEncoder, and JwtTokenizer, which are dependencies of the UserServiceImpl class. We also use @BeforeEach to initialize the UserServiceImpl object with the mock dependencies.

In each test method, we use when to define the behavior of the mock objects, and then call the corresponding method in the UserServiceImpl class. Finally, we use assertions to verify that the expected results are returned by the method.

Of course, this is just an example, and you'll need to adapt it to your specific use case. But hopefully it gives you an idea of how to get started with writing unit tests for your UserServiceImpl class.
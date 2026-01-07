package dev.waitzero.waitzero.user;



import dev.waitzero.waitzero.model.entity.*;
import dev.waitzero.waitzero.model.service.UserServiceModel;
import dev.waitzero.waitzero.repository.UserRepository;
import dev.waitzero.waitzero.service.UserServiceImpl;
import dev.waitzero.waitzero.util.CurrentUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.Mockito.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;


    @Mock(answer = Answers.RETURNS_SELF)
    private CurrentUser currentUser;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void isNameExists_returnsTrueWhenUserFound() {
        String username = "bory";

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(new User()));

        boolean exists = userService.isNameExists(username);

        assertTrue(exists);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void isNameExists_returnsFalseWhenUserMissing() {
        String username = "unknown";

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        boolean exists = userService.isNameExists(username);

        assertFalse(exists);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void loginUser_setsCurrentUserIdUsernameAndRole() {
        Long id = 5L;
        String username = "staffUser";

        User userEntity = new User();
        userEntity.setId(id);
        userEntity.setUsername(username);
        userEntity.setRole(UserRole.STAFF);


        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

        userService.loginUser(id, username);


        verify(currentUser).setId(id);
        verify(currentUser).setUsername(username);
        verify(currentUser).setRole(UserRole.STAFF);
    }

    @Test
    void logout_clearsCurrentUser() {
        userService.logout();

        verify(currentUser).setId(null);
        verify(currentUser).setUsername(null);
    }


    @Test
    void findUserByUsernameAndPassword_returnsNullWhenPasswordMismatch() {

        String username = "bory";
        String rawPassword = "wrong";

        when(userRepository.findByUsernameAndPassword(username, rawPassword))
                .thenReturn(Optional.empty());

        UserServiceModel result =
                userService.findUserByUsernameAndPassword(username, rawPassword);

        assertNull(result);

        verify(userRepository).findByUsernameAndPassword(username, rawPassword);
    }
}

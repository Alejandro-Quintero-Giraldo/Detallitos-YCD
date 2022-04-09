package co.com.detallitosycd.app.rest.repository;

import co.com.detallitosycd.app.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    User user = new User("1234567890", "randomName", "randomCellPhone",
            "randomAddress", "randomEmail", "randomPass");

    @Test
    void saveUser(){
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User user1 = userRepository.save(user);
        Assertions.assertNotNull(user1);
        Assertions.assertEquals(user.getUserId(), user1.getUserId());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void findById(){
        String userId = "1234567890";
        Mockito.when(userRepository.findUserByUserId(userId)).thenReturn(user);
        User user2 = userRepository.findUserByUserId(userId);
        Assertions.assertEquals(user2.getUserId(), userId);
        Mockito.verify(userRepository, Mockito.times(1)).findUserByUserId(userId);
    }
}
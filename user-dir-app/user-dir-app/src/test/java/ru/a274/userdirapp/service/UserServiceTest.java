package ru.a274.userdirapp.service;

import org.junit.jupiter.api.BeforeAll;

import static org.mockito.ArgumentMatchers.anyString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;
import ru.a274.userdirapp.model.Status;
import ru.a274.userdirapp.model.User;
import ru.a274.userdirapp.repository.UserRepo;

import java.util.List;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> captor;

    @Mock
    private UserRepo userRepo;
    private static User user1, user2;

    @BeforeAll
    static void create() {
        user1 = new User();
        user1.setId("someId");
        user1.setLogin("login1");
        user1.setEmail("test@test.com");
        user1.setStatus(Status.ACTIVE.name());

        user2 = new User();
        user2.setId("someId2");
        user2.setLogin("login2");
        user2.setEmail("test2@test.com");
        user2.setStatus(Status.ACTIVE.name());
    }

    @Test
    void getUserById() {
        Mockito.when(userRepo.findById(anyString())).thenReturn(java.util.Optional.ofNullable(user1));
        assertEquals(user1, userRepo.findById("someId").get());
    }

    @Test
    void getAll() {
        Mockito.when(userRepo.findAll()).thenReturn(List.of(user1, user2));
        assertEquals(2, userRepo.findAll().size());
    }

    @Test
    void saveUser() {
        userService.saveUser(user1);
        Mockito.verify(userRepo).save(captor.capture());
        User capturedUser = captor.getValue();
        assertEquals("login1", capturedUser.getLogin());
    }

}
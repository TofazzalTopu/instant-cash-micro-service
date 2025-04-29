package com.info.bank.service;

import com.info.bank.entity.User;
import com.info.bank.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@EnableCaching
public class UserServiceTest {

    //    private static RedisServer redisServer;
    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOps;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @BeforeEach
    void setup() {
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOps);
        Mockito.when(valueOps.get("key")).thenReturn("mockedValue");
    }


    @Test
    void testFindById_shouldUseCache() {
        Long userId = 1L;
        User user = new User(userId, "John Doe", 1L);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // First call - should hit the repository
        Optional<User> result1 = userService.findById(userId);
        assertTrue(result1.isPresent());
        assertEquals("John Doe", result1.get().getName());

        // Second call - should hit cache, not repository
        Optional<User> result2 = userService.findById(userId);

        // Ensure repository called only once
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        assertEquals(result1.get(), result2.get());
    }

    @BeforeAll
    public static void startRedis() throws Exception {
//        redisServer = new RedisServer(6379);
//        redisServer.start();
    }

    @AfterAll
    public static void stopRedis() throws Exception {
//        redisServer.stop();
    }

    @Test
    public void testRedisOps() {
        // Your RedisTemplate-based test
    }
}

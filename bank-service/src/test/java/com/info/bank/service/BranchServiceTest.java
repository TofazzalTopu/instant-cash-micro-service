package com.info.bank.service;

import com.info.bank.entity.Branch;
import com.info.bank.entity.BranchKey;
import com.info.bank.repository.BranchRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EnableCaching
@ExtendWith(SpringExtension.class)
class BranchServiceTest {

    @Autowired
    private BranchService branchService;

    @MockBean
    private BranchRepository branchRepository;

    private static GenericContainer<?> redisContainer;

    @BeforeAll
    public static void startRedis() {
        // Start a Redis container using the latest Redis image
        redisContainer = new GenericContainer<>("redis:latest")
                .withExposedPorts(6379)
                .waitingFor(Wait.forListeningPort());
        redisContainer.start();
    }

    @AfterAll
    public static void stopRedis() {
        // Stop the Redis container
        redisContainer.stop();
    }

    @Test
    void testFindById_shouldUseCache() {
        Long branchId = 1L;
        int branchCode = 123456;
        short entityNumber = 1;
        Short numberOfOfficersAvailable = 1;
        BranchKey branchKey = new BranchKey(entityNumber, branchCode);
        Branch branch = new Branch(branchKey, "Main Branch", 123313, numberOfOfficersAvailable);

        Mockito.when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));

        // First call - should hit the repository
        Branch result1 = branchService.findById(branchId);
        assertNotNull(result1);
        assertEquals("Main Branch", result1.getName());

        // Second call - should hit the cache, not repository
        Branch result2 = branchService.findById(branchId);

        // Ensure repository called only once (cache should be hit on the second call)
        Mockito.verify(branchRepository, Mockito.times(1)).findById(branchId);

        // Assert that both results are the same (cache is working)
        assertEquals(result1, result2);
    }
}

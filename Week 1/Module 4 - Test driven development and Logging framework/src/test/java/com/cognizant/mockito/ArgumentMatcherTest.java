package com.cognizant.mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.ArgumentMatchers.startsWith;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cognizant.service.UserRepository;
import com.cognizant.service.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Matching arguments in different ways")
public class ArgumentMatcherTest {
    
    @Mock
    private UserRepository mockRepo;
    
    private UserService svcInstance;
    
    @BeforeEach
    void setUp() {
        svcInstance = new UserService(mockRepo);
    }
    
    @Test
    @DisplayName("Any integer should match here")
    void testAnyMatcher() {
        when(mockRepo.findById(anyInt())).thenReturn("Any User");
        
        String res1 = svcInstance.getUserById(1);
        String res2 = svcInstance.getUserById(999);
        
        assertEquals("Any User", res1);
        assertEquals("Any User", res2);
        verify(mockRepo, times(2)).findById(anyInt());
    }
    
    @Test
    @DisplayName("Any string should be accepted")
    void testAnyStringMatcher() {
        when(mockRepo.save(anyString())).thenReturn(true);
        
        boolean res1 = svcInstance.saveUser("Alice");
        boolean res2 = svcInstance.saveUser("Bob");
        
        assertTrue(res1);
        assertTrue(res2);
        verify(mockRepo).save("Alice");
        verify(mockRepo).save("Bob");
    }
    
    @Test
    @DisplayName("Only an exact value should match")
    void testEqMatcher() {
        when(mockRepo.findById(eq(1))).thenReturn("John Doe");
        
        String res = svcInstance.getUserById(1);
        
        assertEquals("John Doe", res);
        verify(mockRepo).findById(eq(1));
    }
    
    @Test
    @DisplayName("Passing null should be handled properly")
    void testIsNullMatcher() {
        boolean res = svcInstance.saveUser(null);
        
        assertFalse(res);
        verify(mockRepo, never()).save(any());
    }
    
    @Test
    @DisplayName("Non-null values should work fine")
    void testIsNotNullMatcher() {
        when(mockRepo.save(isNotNull())).thenReturn(true);
        
        boolean res = svcInstance.saveUser("Valid User");
        
        assertTrue(res);
        verify(mockRepo).save(isNotNull());
    }
    
    @Test
    @DisplayName("String that contains a keyword should match")
    void testContainsMatcher() {
        when(mockRepo.save(contains("admin"))).thenReturn(true);
        
        svcInstance.saveUser("admin_user");
        
        verify(mockRepo).save(contains("admin"));
    }
    
    @Test
    @DisplayName("String starting with a prefix should match")
    void testStartsWithMatcher() {
        when(mockRepo.save(startsWith("test"))).thenReturn(true);
        
        svcInstance.saveUser("test_user");
        
        verify(mockRepo).save(startsWith("test"));
    }
    
    @Test
    @DisplayName("String ending with a suffix should match")
    void testEndsWithMatcher() {
        when(mockRepo.save(endsWith("@test.com"))).thenReturn(true);
        
        svcInstance.saveUser("user@test.com");
        
        verify(mockRepo).save(endsWith("@test.com"));
    }
    
    @Test
    @DisplayName("Regex pattern matching should work")
    void testMatchesMatcher() {
        when(mockRepo.save(matches(".*@.*\\.com"))).thenReturn(true);
        
        svcInstance.saveUser("user@example.com");
        
        verify(mockRepo).save(matches(".*@.*\\.com"));
    }
    
    @Test
    @DisplayName("Custom condition should filter correctly")
    void testArgThatMatcher() {
        when(mockRepo.save(argThat(s -> s != null && s.length() > 5)))
            .thenReturn(true);
        
        boolean res1 = svcInstance.saveUser("LongUsername");
        
        assertTrue(res1);
        verify(mockRepo).save(argThat(s -> s != null && s.length() > 5));
    }
    
    @Test
    @DisplayName("We can verify the exact argument passed")
    void testArgumentVerification() {
        when(mockRepo.save(anyString())).thenReturn(true);
        
        svcInstance.saveUser("TestUser");
        
        verify(mockRepo).save("TestUser");
    }
    
    @Test
    @DisplayName("Using several matchers at the same time")
    void testMultipleMatchers() {
        svcInstance.getUserById(1);
        svcInstance.getUserById(2);
        svcInstance.saveUser("Alice");
        
        verify(mockRepo, times(2)).findById(anyInt());
        verify(mockRepo).save(anyString());
    }
    
    @Test
    @DisplayName("Mixing exact values and matchers together")
    void testCombiningMatchers() {
        when(mockRepo.findById(1)).thenReturn("Specific User");
        
        String res = svcInstance.getUserById(1);
        
        assertEquals("Specific User", res);
        verify(mockRepo).findById(1);
    }
    
    @Test
    @DisplayName("Getting a list back should work")
    void testAnyListMatcher() {
        List<String> userList = Arrays.asList("User1", "User2");
        when(mockRepo.findAll()).thenReturn(userList);
        
        List<String> res = svcInstance.getAllUsers();
        
        assertEquals(2, res.size());
        verify(mockRepo).findAll();
    }
}

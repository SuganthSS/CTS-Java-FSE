package com.cognizant.mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cognizant.service.UserRepository;
import com.cognizant.service.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Basic mocking with Mockito")
public class MockitoBasicTest {
    
    @Mock
    private UserRepository mockRepo;
    
    private UserService svcInstance;
    
    @BeforeEach
    void setUp() {
        svcInstance = new UserService(mockRepo);
    }
    
    @Test
    @DisplayName("Finding a user by ID gives the right name")
    void testGetUserById() {
        when(mockRepo.findById(1)).thenReturn("John Doe");
        
        String res = svcInstance.getUserById(1);
        
        assertEquals("John Doe", res);
        verify(mockRepo, times(1)).findById(1);
    }
    
    @Test
    @DisplayName("Getting all users returns the full list")
    void testGetAllUsers() {
        List<String> mockUserList = Arrays.asList("Alice", "Bob", "Charlie");
        when(mockRepo.findAll()).thenReturn(mockUserList);
        
        List<String> res = svcInstance.getAllUsers();
        
        assertEquals(3, res.size());
        assertTrue(res.contains("Alice"));
        assertTrue(res.contains("Bob"));
        verify(mockRepo).findAll();
    }
    
    @Test
    @DisplayName("Saving a valid user should work")
    void testSaveUserValid() {
        when(mockRepo.save("John")).thenReturn(true);
        
        boolean res = svcInstance.saveUser("John");
        
        assertTrue(res);
        verify(mockRepo).save("John");
    }
    
    @Test
    @DisplayName("Saving a null name should be rejected")
    void testSaveUserNull() {
        boolean res = svcInstance.saveUser(null);
        
        assertFalse(res);
        verify(mockRepo, never()).save(any());
    }
    
    @Test
    @DisplayName("Saving a blank name should be rejected")
    void testSaveUserEmpty() {
        boolean res = svcInstance.saveUser("   ");
        
        assertFalse(res);
        verify(mockRepo, never()).save(any());
    }
    
    @Test
    @DisplayName("Deleting a user should go through")
    void testDeleteUser() {
        when(mockRepo.delete(1)).thenReturn(true);
        
        boolean res = svcInstance.deleteUser(1);
        
        assertTrue(res);
        verify(mockRepo).delete(1);
    }
    
    @Test
    @DisplayName("Counting users gives the right number")
    void testGetUserCount() {
        when(mockRepo.count()).thenReturn(5);
        
        int cnt = svcInstance.getUserCount();
        
        assertEquals(5, cnt);
        verify(mockRepo, times(1)).count();
    }
    
    @Test
    @DisplayName("Looking up multiple users works fine")
    void testMultipleInteractions() {
        when(mockRepo.findById(1)).thenReturn("User1");
        when(mockRepo.findById(2)).thenReturn("User2");
        
        String u1 = svcInstance.getUserById(1);
        String u2 = svcInstance.getUserById(2);
        
        assertEquals("User1", u1);
        assertEquals("User2", u2);
        verify(mockRepo).findById(1);
        verify(mockRepo).findById(2);
    }
    
    @Test
    @DisplayName("A missing user should come back as null")
    void testMockDefaultBehavior() {
        String res = svcInstance.getUserById(999);
        
        assertNull(res);
        verify(mockRepo).findById(999);
    }
    
    @Test
    @DisplayName("No extra calls should happen on the mock")
    void testVerifyNoMoreInteractions() {
        when(mockRepo.findById(1)).thenReturn("John");
        
        svcInstance.getUserById(1);
        
        verify(mockRepo).findById(1);
        verifyNoMoreInteractions(mockRepo);
    }
    
    @Test
    @DisplayName("Each call can return something different")
    void testStubbingMultipleReturnValues() {
        when(mockRepo.count())
            .thenReturn(1)
            .thenReturn(2)
            .thenReturn(3);
        
        assertEquals(1, svcInstance.getUserCount());
        assertEquals(2, svcInstance.getUserCount());
        assertEquals(3, svcInstance.getUserCount());
        
        verify(mockRepo, times(3)).count();
    }
}

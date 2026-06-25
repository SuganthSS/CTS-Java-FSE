package com.cognizant.springtest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cognizant.service.UserRepository;
import com.cognizant.service.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing the user service")
public class ServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        System.out.println("Getting the service test ready");
    }
    
    @Test
    @DisplayName("Finding a user by ID gives the right name")
    void testGetUserById() {
        when(userRepository.findById(1)).thenReturn("John Doe");
        
        String res = userService.getUserById(1);
        
        assertEquals("John Doe", res);
        verify(userRepository, times(1)).findById(1);
    }
    
    @Test
    @DisplayName("Looking up several users all works")
    void testGetMultipleUsers() {
        when(userRepository.findById(1)).thenReturn("Alice");
        when(userRepository.findById(2)).thenReturn("Bob");
        when(userRepository.findById(3)).thenReturn("Charlie");
        
        String u1 = userService.getUserById(1);
        String u2 = userService.getUserById(2);
        String u3 = userService.getUserById(3);
        
        assertEquals("Alice", u1);
        assertEquals("Bob", u2);
        assertEquals("Charlie", u3);
        
        verify(userRepository).findById(1);
        verify(userRepository).findById(2);
        verify(userRepository).findById(3);
    }
    
    @Test
    @DisplayName("Getting all users returns the whole list")
    void testGetAllUsers() {
        List<String> mockUserList = Arrays.asList("Alice", "Bob", "Charlie", "David");
        when(userRepository.findAll()).thenReturn(mockUserList);
        
        List<String> res = userService.getAllUsers();
        
        assertNotNull(res);
        assertEquals(4, res.size());
        assertEquals("Alice", res.get(0));
        assertEquals("Bob", res.get(1));
        assertEquals("Charlie", res.get(2));
        assertEquals("David", res.get(3));
        
        verify(userRepository).findAll();
    }
    
    @Test
    @DisplayName("Getting all users when there are none gives empty")
    void testGetAllUsersEmpty() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());
        
        List<String> res = userService.getAllUsers();
        
        assertNotNull(res);
        assertTrue(res.isEmpty());
        verify(userRepository).findAll();
    }
    
    @Test
    @DisplayName("Saving a real username should succeed")
    void testSaveUserValid() {
        when(userRepository.save("ValidUser")).thenReturn(true);
        
        boolean res = userService.saveUser("ValidUser");
        
        assertTrue(res);
        verify(userRepository).save("ValidUser");
    }
    
    @Test
    @DisplayName("Saving null should be refused")
    void testSaveUserNull() {
        boolean res = userService.saveUser(null);
        
        assertFalse(res);
        verify(userRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("Saving an empty name should be refused")
    void testSaveUserEmpty() {
        boolean res = userService.saveUser("");
        
        assertFalse(res);
        verify(userRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("Saving just spaces should be refused")
    void testSaveUserWhitespace() {
        boolean res = userService.saveUser("   ");
        
        assertFalse(res);
        verify(userRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("All bad inputs get rejected properly")
    void testSaveUserValidation() {
        assertFalse(userService.saveUser(null));
        assertFalse(userService.saveUser(""));
        assertFalse(userService.saveUser("  "));
        assertFalse(userService.saveUser("\t"));
        
        verify(userRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("Deleting a user should go through")
    void testDeleteUserSuccess() {
        when(userRepository.delete(1)).thenReturn(true);
        
        boolean res = userService.deleteUser(1);
        
        assertTrue(res);
        verify(userRepository).delete(1);
    }
    
    @Test
    @DisplayName("Deleting a user that doesn't exist should fail")
    void testDeleteUserFailure() {
        when(userRepository.delete(999)).thenReturn(false);
        
        boolean res = userService.deleteUser(999);
        
        assertFalse(res);
        verify(userRepository).delete(999);
    }
    
    @Test
    @DisplayName("Counting users gives the right number")
    void testGetUserCount() {
        when(userRepository.count()).thenReturn(10);
        
        int cnt = userService.getUserCount();
        
        assertEquals(10, cnt);
        verify(userRepository).count();
    }
    
    @Test
    @DisplayName("Counting when there are no users gives zero")
    void testGetUserCountZero() {
        when(userRepository.count()).thenReturn(0);
        
        int cnt = userService.getUserCount();
        
        assertEquals(0, cnt);
        verify(userRepository).count();
    }
    
    @Test
    @DisplayName("Looking up a missing user gives null back")
    void testHandleNullFromRepository() {
        when(userRepository.findById(999)).thenReturn(null);
        
        String res = userService.getUserById(999);
        
        assertNull(res);
        verify(userRepository).findById(999);
    }
    
    @Test
    @DisplayName("Full lifecycle: create, find, count, delete")
    void testUserLifecycle() {
        when(userRepository.save("TestUser")).thenReturn(true);
        when(userRepository.findById(1)).thenReturn("TestUser");
        when(userRepository.count()).thenReturn(1);
        when(userRepository.delete(1)).thenReturn(true);
        
        boolean saveRes = userService.saveUser("TestUser");
        assertTrue(saveRes);
        verify(userRepository).save("TestUser");
        
        String fetchedUser = userService.getUserById(1);
        assertEquals("TestUser", fetchedUser);
        verify(userRepository).findById(1);
        
        int cnt = userService.getUserCount();
        assertEquals(1, cnt);
        verify(userRepository).count();
        
        boolean deleteRes = userService.deleteUser(1);
        assertTrue(deleteRes);
        verify(userRepository).delete(1);
    }
    
    @Test
    @DisplayName("The service is properly wired up")
    void testDependencyInjection() {
        assertNotNull(userService);
        
        when(userRepository.count()).thenReturn(5);
        int cnt = userService.getUserCount();
        
        assertEquals(5, cnt);
        verify(userRepository).count();
    }
    
    @Test
    @DisplayName("Running several operations in a row works")
    void testMultipleOperations() {
        when(userRepository.save(anyString())).thenReturn(true);
        when(userRepository.findAll()).thenReturn(Arrays.asList("User1", "User2"));
        when(userRepository.count()).thenReturn(2);
        
        userService.saveUser("User1");
        userService.saveUser("User2");
        List<String> userList = userService.getAllUsers();
        int cnt = userService.getUserCount();
        
        assertEquals(2, userList.size());
        assertEquals(2, cnt);
        
        verify(userRepository, times(2)).save(anyString());
        verify(userRepository).findAll();
        verify(userRepository).count();
    }
    
    @Test
    @DisplayName("Database errors should bubble up properly")
    void testRepositoryException() {
        when(userRepository.findById(1)).thenThrow(new RuntimeException("Database error"));
        
        assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1);
        });
        
        verify(userRepository).findById(1);
    }
}

package com.cognizant.springtest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cognizant.controller.UserController;
import com.cognizant.service.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing the user controller")
public class ControllerTest {
    
    @Mock
    private UserService userService;
    
    @InjectMocks
    private UserController userController;
    
    @BeforeEach
    void setUp() {
        System.out.println("Getting the controller test ready");
    }
    
    @Test
    @DisplayName("Looking up a user gives the right name")
    void testGetUser() {
        when(userService.getUserById(1)).thenReturn("John Doe");
        
        String res = userController.getUser(1);
        
        assertEquals("John Doe", res);
        verify(userService).getUserById(1);
    }
    
    @Test
    @DisplayName("Looking up a user that doesn't exist gives null")
    void testGetUserNotFound() {
        when(userService.getUserById(999)).thenReturn(null);
        
        String res = userController.getUser(999);
        
        assertNull(res);
        verify(userService).getUserById(999);
    }
    
    @Test
    @DisplayName("Listing users returns everyone")
    void testListUsers() {
        List<String> mockUserList = Arrays.asList("Alice", "Bob", "Charlie");
        when(userService.getAllUsers()).thenReturn(mockUserList);
        
        List<String> res = userController.listUsers();
        
        assertNotNull(res);
        assertEquals(3, res.size());
        assertTrue(res.contains("Alice"));
        assertTrue(res.contains("Bob"));
        assertTrue(res.contains("Charlie"));
        verify(userService).getAllUsers();
    }
    
    @Test
    @DisplayName("Listing users when there are none gives an empty list")
    void testListUsersEmpty() {
        when(userService.getAllUsers()).thenReturn(Arrays.asList());
        
        List<String> res = userController.listUsers();
        
        assertNotNull(res);
        assertTrue(res.isEmpty());
        verify(userService).getAllUsers();
    }
    
    @Test
    @DisplayName("Creating a user should give a success message")
    void testCreateUserSuccess() {
        when(userService.saveUser("NewUser")).thenReturn(true);
        
        String res = userController.createUser("NewUser");
        
        assertEquals("Great, the user was created", res);
        verify(userService).saveUser("NewUser");
    }
    
    @Test
    @DisplayName("Creating a user can fail too")
    void testCreateUserFailure() {
        when(userService.saveUser("InvalidUser")).thenReturn(false);
        
        String res = userController.createUser("InvalidUser");
        
        assertEquals("Oops, couldn't create the user", res);
        verify(userService).saveUser("InvalidUser");
    }
    
    @Test
    @DisplayName("Creating with no name should fail")
    void testCreateUserNull() {
        when(userService.saveUser(null)).thenReturn(false);
        
        String res = userController.createUser(null);
        
        assertEquals("Oops, couldn't create the user", res);
        verify(userService).saveUser(null);
    }
    
    @Test
    @DisplayName("Deleting a user should give a success message")
    void testDeleteUserSuccess() {
        when(userService.deleteUser(1)).thenReturn(true);
        
        String res = userController.deleteUser(1);
        
        assertEquals("Done, the user was removed", res);
        verify(userService).deleteUser(1);
    }
    
    @Test
    @DisplayName("Deleting a user can fail too")
    void testDeleteUserFailure() {
        when(userService.deleteUser(999)).thenReturn(false);
        
        String res = userController.deleteUser(999);
        
        assertEquals("Sorry, couldn't remove the user", res);
        verify(userService).deleteUser(999);
    }
    
    @Test
    @DisplayName("Handling several requests at once works")
    void testMultipleRequests() {
        when(userService.getUserById(1)).thenReturn("User1");
        when(userService.getUserById(2)).thenReturn("User2");
        when(userService.saveUser("User3")).thenReturn(true);
        
        String u1 = userController.getUser(1);
        String u2 = userController.getUser(2);
        String createRes = userController.createUser("User3");
        
        assertEquals("User1", u1);
        assertEquals("User2", u2);
        assertEquals("Great, the user was created", createRes);
        
        verify(userService).getUserById(1);
        verify(userService).getUserById(2);
        verify(userService).saveUser("User3");
    }
    
    @Test
    @DisplayName("The controller is properly wired up")
    void testDependencyInjection() {
        assertNotNull(userController);
        
        when(userService.getUserById(1)).thenReturn("Test");
        String res = userController.getUser(1);
        
        assertEquals("Test", res);
    }
    
    @Test
    @DisplayName("Full create, read, list, delete flow works")
    void testCRUDOperations() {
        when(userService.saveUser("TestUser")).thenReturn(true);
        when(userService.getUserById(1)).thenReturn("TestUser");
        when(userService.getAllUsers()).thenReturn(Arrays.asList("TestUser"));
        when(userService.deleteUser(1)).thenReturn(true);
        
        String createRes = userController.createUser("TestUser");
        assertEquals("Great, the user was created", createRes);
        
        String getRes = userController.getUser(1);
        assertEquals("TestUser", getRes);
        
        List<String> listRes = userController.listUsers();
        assertEquals(1, listRes.size());
        
        String deleteRes = userController.deleteUser(1);
        assertEquals("Done, the user was removed", deleteRes);
        
        verify(userService).saveUser("TestUser");
        verify(userService).getUserById(1);
        verify(userService).getAllUsers();
        verify(userService).deleteUser(1);
    }
}

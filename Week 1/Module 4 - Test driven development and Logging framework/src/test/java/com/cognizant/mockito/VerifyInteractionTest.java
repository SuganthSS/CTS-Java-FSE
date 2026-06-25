package com.cognizant.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InOrder;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cognizant.service.UserRepository;
import com.cognizant.service.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Checking how the mock was used")
public class VerifyInteractionTest {
    
    @Mock
    private UserRepository mockRepo;
    
    private UserService svcInstance;
    
    @BeforeEach
    void setUp() {
        svcInstance = new UserService(mockRepo);
    }
    
    @Test
    @DisplayName("The method should be called exactly once")
    void testVerifyMethodCalledOnce() {
        when(mockRepo.findById(1)).thenReturn("John");
        
        svcInstance.getUserById(1);
        
        verify(mockRepo, times(1)).findById(1);
        verify(mockRepo).findById(1);
    }
    
    @Test
    @DisplayName("Calling it three times should be tracked")
    void testVerifyMethodCalledMultipleTimes() {
        when(mockRepo.count()).thenReturn(5);
        
        svcInstance.getUserCount();
        svcInstance.getUserCount();
        svcInstance.getUserCount();
        
        verify(mockRepo, times(3)).count();
    }
    
    @Test
    @DisplayName("Some methods should never have been called")
    void testVerifyMethodNeverCalled() {
        svcInstance.getUserById(1);
        
        verify(mockRepo, never()).delete(anyInt());
        verify(mockRepo, never()).save(anyString());
    }
    
    @Test
    @DisplayName("It should be called at least one time")
    void testVerifyAtLeastOnce() {
        when(mockRepo.findById(1)).thenReturn("John");
        
        svcInstance.getUserById(1);
        svcInstance.getUserById(1);
        
        verify(mockRepo, atLeastOnce()).findById(1);
    }
    
    @Test
    @DisplayName("It should be called at least twice")
    void testVerifyAtLeast() {
        when(mockRepo.count()).thenReturn(10);
        
        svcInstance.getUserCount();
        svcInstance.getUserCount();
        svcInstance.getUserCount();
        
        verify(mockRepo, atLeast(2)).count();
    }
    
    @Test
    @DisplayName("It should not be called more than three times")
    void testVerifyAtMost() {
        when(mockRepo.findById(1)).thenReturn("John");
        
        svcInstance.getUserById(1);
        svcInstance.getUserById(1);
        
        verify(mockRepo, atMost(3)).findById(1);
    }
    
    @Test
    @DisplayName("The mock should not have been touched at all")
    void testVerifyNoInteractions() {
        verifyNoInteractions(mockRepo);
    }
    
    @Test
    @DisplayName("After our call, nothing else should happen")
    void testVerifyNoMoreInteractions() {
        when(mockRepo.findById(1)).thenReturn("John");
        
        svcInstance.getUserById(1);
        
        verify(mockRepo).findById(1);
        verifyNoMoreInteractions(mockRepo);
    }
    
    @Test
    @DisplayName("The calls should happen in the right order")
    void testVerifyInOrder() {
        when(mockRepo.save("Alice")).thenReturn(true);
        when(mockRepo.findById(1)).thenReturn("Alice");
        when(mockRepo.delete(1)).thenReturn(true);
        
        svcInstance.saveUser("Alice");
        svcInstance.getUserById(1);
        svcInstance.deleteUser(1);
        
        InOrder inOrder = inOrder(mockRepo);
        inOrder.verify(mockRepo).save("Alice");
        inOrder.verify(mockRepo).findById(1);
        inOrder.verify(mockRepo).delete(1);
    }
    
    @Test
    @DisplayName("The exact argument we passed should be verified")
    void testVerifySpecificArguments() {
        when(mockRepo.save(anyString())).thenReturn(true);
        
        svcInstance.saveUser("TestUser");
        
        verify(mockRepo).save("TestUser");
    }
    
    @Test
    @DisplayName("Matchers should help verify the right calls")
    void testVerifyWithMatchers() {
        when(mockRepo.findById(anyInt())).thenReturn("User");
        
        svcInstance.getUserById(5);
        
        verify(mockRepo).findById(anyInt());
        verify(mockRepo).findById(eq(5));
    }
    
    @Test
    @DisplayName("We should know exactly how many times it was called")
    void testVerifyExactInvocations() {
        when(mockRepo.findById(anyInt())).thenReturn("User");
        
        svcInstance.getUserById(1);
        svcInstance.getUserById(2);
        
        verify(mockRepo, times(2)).findById(anyInt());
        verify(mockRepo, times(1)).findById(1);
        verify(mockRepo, times(1)).findById(2);
    }
    
    @Test
    @DisplayName("Only one method should have been called")
    void testVerifyOnly() {
        when(mockRepo.count()).thenReturn(5);
        
        svcInstance.getUserCount();
        
        verify(mockRepo, only()).count();
    }
    
    @Test
    @DisplayName("Multiple methods should all be verified together")
    void testComplexInteractionVerification() {
        when(mockRepo.save(anyString())).thenReturn(true);
        when(mockRepo.findById(anyInt())).thenReturn("User");
        when(mockRepo.count()).thenReturn(1);
        
        svcInstance.saveUser("Alice");
        svcInstance.getUserById(1);
        svcInstance.getUserCount();
        
        verify(mockRepo).save("Alice");
        verify(mockRepo).findById(1);
        verify(mockRepo).count();
        verifyNoMoreInteractions(mockRepo);
    }
    
    @Test
    @DisplayName("The call should happen within a second")
    void testVerifyWithTimeout() {
        when(mockRepo.findById(1)).thenReturn("John");
        
        svcInstance.getUserById(1);
        
        verify(mockRepo, timeout(1000)).findById(1);
    }
}

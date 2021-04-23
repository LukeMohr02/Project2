package com.fishinginstreams.security;

import com.fishinginstreams.model.Angler;
import com.fishinginstreams.repository.AnglerRepo;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class FisUserDetailsServiceTest {

    @InjectMocks
    FisUserDetailsService fisUserDetailsService;

    @Mock
    AnglerRepo mockAnglerRepo;

    Angler testAngler;
    Angler testNullAngler;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        testAngler = new Angler();
        testAngler.setUsername("Test");
        testNullAngler = null;
    }

    @Test
    public void testLoadUserByUsername(){
        Mockito.doReturn(testAngler).when(mockAnglerRepo).findByUsername(testAngler.getUsername());
        assertEquals(fisUserDetailsService.loadUserByUsername(testAngler.getUsername()), testAngler);
        verify(mockAnglerRepo, times(2)).findByUsername(testAngler.getUsername());
    }

    @Test
    public void testLoadUserByUsernameUsernameNotFoundException(){
        Mockito.doReturn(testNullAngler).when(mockAnglerRepo).findByUsername(testAngler.getUsername());
        assertThrows(UsernameNotFoundException.class, () -> fisUserDetailsService.loadUserByUsername("Test"));
        verify(mockAnglerRepo, times(1)).findByUsername(testAngler.getUsername());
    }

}
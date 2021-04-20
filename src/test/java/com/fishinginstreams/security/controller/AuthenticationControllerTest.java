package com.fishinginstreams.security.controller;

import com.fishinginstreams.exception.IncorrectCredentialsException;
import com.fishinginstreams.security.FisUserDetailsService;
import com.fishinginstreams.security.model.AuthenticationRequest;
import com.fishinginstreams.util.JwtUtil;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AuthenticationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AuthenticationManager mockAuthenticationManager;

    @MockBean
    private FisUserDetailsService mockFisUserDetailsService;

    @MockBean
    private JwtUtil mockJwtUtil;

    AuthenticationRequest newAuthenticationRequest;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        newAuthenticationRequest = new AuthenticationRequest("Test", "Test");
    }

    @Test
    public void testCreateAuthenticationToken() throws Exception{
        Mockito.doReturn(null).when(mockFisUserDetailsService).loadUserByUsername(any(String.class));
        Mockito.doReturn("Test").when(mockJwtUtil).generateToken(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .content(new Gson().toJson(newAuthenticationRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockFisUserDetailsService, times(1)).loadUserByUsername(any(String.class));
        verify(mockJwtUtil, times(1)).generateToken(null);
    }

    @Test
    public void testCreateAuthenticationTokenWithBadCredentialsException() throws Exception{
        Mockito.doReturn(null).when(mockFisUserDetailsService).loadUserByUsername(any(String.class));
        Mockito.doReturn("Test").when(mockJwtUtil).generateToken(null);
        Mockito.doThrow(BadCredentialsException.class).when(mockAuthenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .content(new Gson().toJson(newAuthenticationRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof IncorrectCredentialsException));
        verify(mockFisUserDetailsService, times(1)).loadUserByUsername(any(String.class));
        verify(mockJwtUtil, times(1)).generateToken(null);
    }
}
package com.fishinginstreams.filters;

import com.fishinginstreams.security.FisUserDetailsService;
import com.fishinginstreams.util.JwtUtil;
import org.apache.catalina.connector.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JwtRequestFilterTest {

    @InjectMocks
    JwtRequestFilter jwtRequestFilter;

    @Mock
    FisUserDetailsService mockFisUserDetailsService;

    @Mock
    JwtUtil mockJwtUtil;

    @Mock
    HttpServletRequest mockRequest;

    @Mock
    HttpServletResponse mockResponse;

    @Mock
    FilterChain mockFilterChain;

    UserDetails userDetails = new UserDetails() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getPassword() {
            return "Test";
        }

        @Override
        public String getUsername() {
            return "Test";
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
    };


    @Test
    public void doFilterInternalTestWithOptions() throws ServletException, IOException {
        Mockito.doReturn("OPTIONS").when(mockRequest).getMethod();
        Mockito.doReturn("Bearer ").when(mockRequest).getHeader("Authorization");
        Mockito.doReturn("Test").when(mockJwtUtil).extractUsername(any(String.class));
        Mockito.doReturn(userDetails).when(mockFisUserDetailsService).loadUserByUsername(any(String.class));
        jwtRequestFilter.doFilterInternal(mockRequest,mockResponse,mockFilterChain);
    }

    @Test
    public void doFilterInternalTestWithGet() throws ServletException, IOException {
        Mockito.doReturn("GET").when(mockRequest).getMethod();
        Mockito.doReturn("Bearer ").when(mockRequest).getHeader(any(String.class));
        Mockito.doReturn("Test").when(mockJwtUtil).extractUsername(any(String.class));
        Mockito.doReturn(true).when(mockJwtUtil).validateToken(any(String.class), any(UserDetails.class));
        Mockito.doReturn(userDetails).when(mockFisUserDetailsService).loadUserByUsername(any(String.class));
        jwtRequestFilter.doFilterInternal(mockRequest,mockResponse,mockFilterChain);
        verify(mockRequest, times(1)).getMethod();
        verify(mockRequest, times(1)).getHeader(any(String.class));
        verify(mockJwtUtil, times(1)).extractUsername(any(String.class));
        verify(mockJwtUtil, times(1)).validateToken(any(String.class), any(UserDetails.class));
    }
}
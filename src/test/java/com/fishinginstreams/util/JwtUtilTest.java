package com.fishinginstreams.util;

import io.jsonwebtoken.MalformedJwtException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JwtUtilTest {

    @InjectMocks
    JwtUtil jwtUtil;

    InputStream testInputStream;
    Map<String, Object> claimsTest;
    String subjectTest;
    UserDetails testUserDetails;

    public JwtUtilTest() throws IOException {
    }

    @BeforeEach
    public void setup() throws IOException {
        testInputStream = null;
        claimsTest = new HashMap<>();
        subjectTest = "Test";
        testUserDetails = new UserDetails() {
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
    }

    @Test
    public void readPropertiesTest() throws IOException {
        assertNotNull(new JwtUtil());
    }

    @Test
    public void validateTokenTest() throws IOException {
        assertThrows(MalformedJwtException.class, () -> new JwtUtil().validateToken(subjectTest, testUserDetails));
    }
}
package com.fishinginstreams.security.controller;

import com.fishinginstreams.exception.IncorrectCredentialsException;
import com.fishinginstreams.security.FisUserDetailsService;
import com.fishinginstreams.security.model.AuthenticationRequest;
import com.fishinginstreams.security.model.AuthenticationResponse;
import com.fishinginstreams.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// TODO: frontend client should hold on to JWT in the request and send it back in a response header
//      {"Authorization":"Bearer <jwt>"}
@Controller
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private FisUserDetailsService fisUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        final UserDetails userDetails = fisUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            System.out.println("Username: " + authenticationRequest.getUsername());
            System.out.println("Password: " + authenticationRequest.getPassword());
            throw new IncorrectCredentialsException(e);
        }

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}

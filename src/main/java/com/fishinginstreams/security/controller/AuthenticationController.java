package com.fishinginstreams.security.controller;

import com.fishinginstreams.exception.IncorrectCredentialsException;
import com.fishinginstreams.exception.NotNullConstraintViolationException;
import com.fishinginstreams.exception.UniqueConstraintViolationException;
import com.fishinginstreams.model.Angler;
import com.fishinginstreams.repository.AnglerRepo;
import com.fishinginstreams.security.FisUserDetailsService;
import com.fishinginstreams.security.model.AuthenticationRequest;
import com.fishinginstreams.security.model.AuthenticationResponse;
import com.fishinginstreams.util.JwtUtil;
import org.hibernate.PropertyValueException;
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
@CrossOrigin
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private AnglerRepo repo;

    @Autowired
    private FisUserDetailsService fisUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/{action}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, @PathVariable("action") String action) throws Exception {
        
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        
        if (action.equals("signup") | action.equals("register")) {
            Angler registerer = new Angler();
            registerer.setUsername(username);
            registerer.setPassword(password);

            try {
                repo.save(registerer);
            } catch (PropertyValueException e) {
                throw new NotNullConstraintViolationException("username");
            } catch (Exception e) {
                throw new UniqueConstraintViolationException("username", registerer.getUsername());
            }
        }
        
        final UserDetails userDetails = fisUserDetailsService.loadUserByUsername(username);
        final String jwt = jwtUtil.generateToken(userDetails);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
        } catch (BadCredentialsException e) {
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            throw new IncorrectCredentialsException(e);
        }

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}

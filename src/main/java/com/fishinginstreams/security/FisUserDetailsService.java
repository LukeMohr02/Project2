package com.fishinginstreams.security;

import com.fishinginstreams.model.Angler;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FisUserDetailsService implements UserDetailsService {

    //TODO: THIS SHOULD NOT BE HARDCODED. Get the user from the database instead.
    Angler testAngler = new Angler();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        testAngler.setUsername("un");
//        testAngler.setPassword("pw");
//        return new Angler();
        return new User("un","pw",new ArrayList<>());
    }
}

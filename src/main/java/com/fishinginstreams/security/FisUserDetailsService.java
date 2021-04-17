package com.fishinginstreams.security;

import com.fishinginstreams.model.Angler;
import com.fishinginstreams.repository.AnglerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FisUserDetailsService implements UserDetailsService {

    @Autowired
    AnglerRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Angler angler = repo.findByUsername(username);
//
//        if (angler == null) {
//            throw new UsernameNotFoundException("Username not found: " + username);
//        }

        return new User("un","pw",new ArrayList<>());
//        return repo.findByUsername(username);
    }
}

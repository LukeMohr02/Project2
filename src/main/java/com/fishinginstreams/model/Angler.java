package com.fishinginstreams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Angler implements UserDetails {

    @Id
    @GeneratedValue
    int id;
    String username;
    String password;
    String firstName;
    String lastName;
    int age;
    String state;

    // UserDetail fields
    // TODO: figure out how to set these
    ArrayList<? extends GrantedAuthority> authorities;
    boolean accountNonExpired;
    boolean accountNonLocked;
    boolean credentialsNonExpired;
    boolean enabled;

//    @OneToMany(mappedBy = "userid", cascade = CascadeType.ALL)
    ArrayList<Catch> catches;

    {
//        catches = new ArrayList<>();
    }


}

package com.fishinginstreams.model;

import com.sun.istack.NotNull;
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
    private int id;
    @NotNull
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private String state;

    // UserDetail fields
    private ArrayList<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    private ArrayList<Catch> catches;

    private ArrayList<Groups> groups;

}

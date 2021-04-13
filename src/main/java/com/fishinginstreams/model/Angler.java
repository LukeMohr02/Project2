package com.fishinginstreams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(exclude = "groups")
@Table(indexes = {@Index(name = "username_index", columnList = "username", unique = true)})
public class Angler implements UserDetails {

    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true, nullable = false)
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

    private ArrayList<Groop> groups;

}

package com.fishinginstreams.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Angler {

    @Id
    @GeneratedValue
    int id;

    @Column
    String username;
    String password;
    String firstName;
    String lastName;
    int age;
    String state;

    //TODO: Angler -> Catche mapping
    @OneToMany(mappedBy = "userid", cascade = CascadeType.ALL)
    List<Catch> catches;
    {
        catches = new ArrayList<>();
    }

//    public Angler(String username, String password, String firstName, String lastName, int age, String state, List<Catch> catches) {
//        this.username = username;
//        this.password = password;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.age = age;
//        this.state = state;
//        this.catches = catches;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public List<Catch> getCatches() {
//        return catches;
//    }
//
//    public void addCatche(Catch catch) {
//        catches.add(catch);
//    }
}

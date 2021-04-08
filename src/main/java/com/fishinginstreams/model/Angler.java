package com.fishinginstreams.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Angler {

    @Id
    @GeneratedValue
    int id;
    String username;
    String password;
    String firstName;
    String lastName;
    int age;
    String state;

    //TODO: Angler -> Catche mapping
    List<Catch> catches;

//    {
//        catches = new ArrayList<>();
//    }

    public Angler() {
    }

    public Angler(String username, String password, String firstName, String lastName, int age, String state, List<Catch> catches) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.state = state;
        this.catches = catches;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Catch> getCatches() {
        return catches;
    }

//    public void addCatche(Catch catch) {
//        catches.add(catch);
//    }
}

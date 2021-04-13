package com.fishinginstreams.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "anglers")
public class Groop {

    @Id
    @GeneratedValue
    private int id;
    @NotNull
    private String groupName;
    private String groupDescription;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ANGLER_GROUPS",
            joinColumns = @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ANGLER_ID", referencedColumnName = "id")
    )
    private List<Angler> anglers;
}

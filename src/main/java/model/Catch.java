package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Catch {

    @Id
    @GeneratedValue
    int id;
    int userId;
    int fishId;
    int gearId;
}

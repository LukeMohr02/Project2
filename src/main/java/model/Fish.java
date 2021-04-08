package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URI;

@Entity
public class Fish {

    @Id
    @GeneratedValue
    int id;
    String species;
    double length;
    double weight;
    Enum habitat;
    Enum dangerLevel;
    URI image;

    public Fish() {
    }

    public Fish(String species, double length, double weight, Enum habitat, Enum dangerLevel, URI image) {
        this.species = species;
        this.length = length;
        this.weight = weight;
        this.habitat = habitat;
        this.dangerLevel = dangerLevel;
        this.image = image;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public URI getImage() {
        return image;
    }

    public void setImage(URI image) {
        this.image = image;
    }
}

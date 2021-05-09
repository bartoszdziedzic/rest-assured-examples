package com.testinglaboratory.testingbasics.exercises;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class Toy {
    private String make;
    private String name;
    private String colour;
    private String material;
//
//    public Toy(String make, String name, String colour, String material) {
//        this.name = name;
//        this.colour = colour;
//        this.material = material;
//    }
//
//    public String getMake() {
//        return make;
//    }
//
//    public void setMake(String make) {
//        this.make = make;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getColour() {
//        return colour;
//    }
//
//    public void setColour(String colour) {
//        this.colour = colour;
//    }
//
//    public String getMaterial() {
//        return material;
//    }
//
//    public void setMaterial(String material) {
//        this.material = material;
//    }

    public String greeting(){
        return "Hello! I am:"
                + "\n" + "Name: " + name
                + "\n" + "Colour: " + colour
                + "\n" + "Material: " + material;
    }

}
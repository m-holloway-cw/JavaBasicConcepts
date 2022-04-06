/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raxa.javaconcepts;

/**
 *
 * @author Raxa
 */
public class AssociationObjects {
    public static void main(String args[]){
        //Association
        Car car1 = new Car("Ford", 2000, 1234);
        Driver driver1 = new Driver("Bob", 50);
        System.out.println("The " + car1.year + " " + car1.carMake + " with license " + car1.licensePlate + " belongs to " + driver1.name + " age: " + driver1.age);
        //Aggregation
        Make ford = new Make("Ford");
        Model f150 = new Model("F150", "black");
        Type vehicle = new Type("Truck", ford, f150);
        System.out.println(vehicle.model.color + " " + vehicle.make.make + " " + vehicle.model.model + " " + vehicle.type);
    }
}
class Car{
    String carMake;
    int year;
    int licensePlate;
    Car(String make, int year, int licensePlate){
        this.carMake = make;
        this.year = year;
        this.licensePlate = licensePlate;
    }
}
class Driver{
    String name;
    int age;
    Driver(String name, int age){
        this.name = name;
        this.age = age;
    }
}
class Make{
    String make;
    Make(String make){
    this.make = make;
}
}
class Model{
    String model;
    String color;
    Model(String model, String color){
        this.model = model;
        this.color = color;
    }
}
class Type{
    String type;
    Make make;
    Model model;
    Type(String type, Make make, Model model){
        this.type = type;
        this.make = make;
        this.model = model;
    }
}
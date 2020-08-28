/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author sebas
 */
public class CarDTO {
    private String make;
    private double price;
    private int year;

    public CarDTO(String make, double price, int year) {
        this.make = make;
        this.price = price;
        this.year = year;
    }
    public CarDTO(){} //Required for the automatic demo
    
    //Getters and Setters ....
}


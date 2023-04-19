package support;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private double price;
    private String brand;
    private String measure;
    private Market seller;

    public Product(){}

    public Product(int id, String name, double price, String brand, String measure, Market seller){
        this.id = id;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.measure = measure;
        this.seller = seller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Market getSeller() {
        return seller;
    }

    public void setSeller(Market seller) {
        this.seller = seller;
    }

    public String getDescription() {
        return name + " " + brand + " " + measure;
    }

    public void setDescription(String name, String brand, String measure) {
        this.name = name;
        this.brand = brand;
        this.measure = measure;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return getDescription() + ", " + this.price + ", at the " + this.seller.getName();
    }
}

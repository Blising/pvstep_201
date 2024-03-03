package com.example.greenscape;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    private  String  name;
    private  String  size;
    private  String poshta;
    private  int   numbers;


    private List<String> ingredients = new ArrayList<>();

    public Order() {
    }

    public Order(String name, String size, String poshta, int numbers, List<String> ingredients) {
        this.name = name;
        this.size = size;
        this.poshta = poshta;
        this.numbers = numbers;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPoshta() {
        return poshta;
    }

    public void setPoshta(String poshta) {
        this.poshta = poshta;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Order{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", poshta='" + poshta + '\'' +
                ", numbers=" + numbers +
                ", ingredients=" + ingredients +
                '}';
    }
}

package com.example.store.signup.model;

public enum StarsType {
    ONE_STAR(1.0),
    TWO_STAR(2.0),
    THREE_STAR(3.0),
    FOUR_STAR(4.0),
    FIVE_STAR(5.0);


    double value;

    public double getValue() {
        return this.value;
    }

    StarsType(double value) {
        this.value = value;
    }

}

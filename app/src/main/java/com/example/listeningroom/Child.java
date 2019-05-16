package com.example.listeningroom;

public class Child {
    int number;
    String name;
    String status;

    public Child(int number, String name, String status) {
        this.number = number;
        this.name = name;
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package edu.whatcom.mywcc.models;

public class Room {
    public Building building;
    public String number; // some room numbers aren't actually numeric...

    public Room(Building building, String number) {
        this.building = building;
        this.number = number;
    }

    @Override
    public String toString() {
        return building.getId() + " " + number;
    }
}

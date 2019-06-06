package edu.whatcom.mywcc.models;

public class Building {
    private static Building baker = new Building("BKR", "Baker");
    private static Building cascade = new Building("CAS", "Cascade");

    private String id;
    private String name;

    public Building(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.id;
    }

    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public Room getRoom(String no) {
        return new Room(this, no);
    }

    public static Building getById(String id) {
        if(id.equals("BKR")) {
            return baker;
        } else if(id.equals("CAS")) {
            return cascade;
        } else {
            return new Building(id, "Unknown: " + id);
        }
    }
}

package org.example;

public class Provider {
    private int id;
    private String name;
    private String registryDate;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRegistryDate() {
        return registryDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRegistryDate(String registryDate) {
        this.registryDate = registryDate;
    }

    public void setName(String name) {
        this.name = name;
    }


}

package entities;

public class Provider {
    private String id;
    private String name;
    private String registryDate;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRegistryDate() {
        return registryDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRegistryDate(String registryDate) {
        this.registryDate = registryDate;
    }

    public void setName(String name) {
        this.name = name;
    }


}

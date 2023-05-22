package entities;

public class Provider {
    private String id;
    private String name;
    private String registryDate;

    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

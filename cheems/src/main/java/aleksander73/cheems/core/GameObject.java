package aleksander73.cheems.core;

public class GameObject {
    private String name;

    protected GameObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

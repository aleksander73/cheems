package aleksander73.cheems.core;

public class GameObject implements Script {
    private String name;

    protected GameObject(String name) {
        this.name = name;
    }

    @Override
    public void start() {}

    @Override
    public void update() {}

    public String getName() {
        return name;
    }
}

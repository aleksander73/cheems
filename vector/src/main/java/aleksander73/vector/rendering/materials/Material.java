package aleksander73.vector.rendering.materials;

import aleksander73.vector.core.Component;
import aleksander73.vector.core.GameEngine;
import aleksander73.vector.rendering.shaders.Shader;

public class Material extends Component {
    private Colour colour;
    private Texture texture;
    private Shader shader;

    public Material(Colour colour, Texture texture, Shader shader) {
        this.colour = (colour != null) ? colour : Colour.DEFAULT;
        this.texture = (texture != null) ? texture : GameEngine.getResourceSystem().getDefaultTex();;
        this.shader = shader;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Shader getShader() {
        return shader;
    }
}

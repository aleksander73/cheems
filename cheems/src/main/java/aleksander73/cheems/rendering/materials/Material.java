package aleksander73.cheems.rendering.materials;

import aleksander73.cheems.assets.ResourceManager;
import aleksander73.cheems.core.Component;
import aleksander73.cheems.rendering.shaders.Shader;

public class Material extends Component {
    private Colour colour;
    private Texture texture;
    private Shader shader;

    public Material(Colour colour, Texture texture, Shader shader) {
        this.colour = (colour != null) ? colour : Colour.DEFAULT;
        this.texture = (texture != null) ? texture : ResourceManager.getInstance().getDefaultTex();;
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

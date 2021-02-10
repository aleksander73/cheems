precision mediump float;

uniform vec4 u_colour;
uniform sampler2D u_texture;

varying vec2 v_texture_xy;

void main() {
    gl_FragColor = u_colour * texture2D(u_texture, v_texture_xy);
}

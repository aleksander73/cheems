uniform mat4 u_view_rotate;
uniform mat4 u_projection;

attribute vec3 a_position;
attribute vec2 a_texture_xy;

varying vec2 v_texture_xy;

void main() {
    gl_Position = u_projection * u_view_rotate * vec4(a_position, 1.0);
    v_texture_xy = a_texture_xy;
}

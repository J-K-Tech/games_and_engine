#version 120
attribute vec2 position;
attribute vec2 texCoord;
varying vec2 fragTexCoord;
void main() {
fragTexCoord = (position * 0.5 + 0.5);
    gl_Position = vec4(position.x,position.y, 0.0, 1.0);
}

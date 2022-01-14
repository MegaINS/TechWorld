#version 400

layout (location=0) in vec3 position;
layout (location=1) in vec4 color;
layout (location=2) in vec2 texCoord;


uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;

out Vertex{
    vec4 color;
    vec2 texCoord;
} Ver;


void main() {
    Ver.color = color;
    Ver.texCoord = texCoord;

    gl_Position = projectionMatrix  * modelMatrix * vec4(position, 1.0);
}

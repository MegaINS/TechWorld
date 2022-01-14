#version 330

layout (location=0) in vec3 position;
layout (location=2) in vec2 texCoord;


uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;

out vec2 mvVertexTexCoord;

void main() {


    mvVertexTexCoord = texCoord;

    vec4 mvPos = viewMatrix * modelMatrix * vec4(position, 1.0);

    gl_Position = projectionMatrix * mvPos;
}




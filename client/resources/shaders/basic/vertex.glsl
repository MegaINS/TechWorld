#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec4 color;
layout (location=2) in vec2 texCoord;


uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;

out vec3 mvVertexPos;
out vec4 mvVertexColor;
out vec2 mvVertexTexCoord;

void main() {

    vec4 mvPos = viewMatrix * modelMatrix * vec4(position, 1.0);
    mvVertexColor = color;
    mvVertexTexCoord = texCoord;
    mvVertexPos = mvPos.xyz;

    gl_Position = projectionMatrix * mvPos;
}

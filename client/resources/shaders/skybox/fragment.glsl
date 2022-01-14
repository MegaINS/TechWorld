#version 330

uniform sampler2D textureMap;
uniform vec3 ambientLight;


in vec2 mvVertexTexCoord;

out vec4 fragColor;



void main()
{
    fragColor = vec4(ambientLight, 1) * texture(textureMap, mvVertexTexCoord);
}
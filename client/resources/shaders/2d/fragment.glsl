#version 400

uniform sampler2D textureMap;

in Vertex{
    vec4 color;
    vec2 texCoord;
} Ver;

out vec4 fragColor;

void main() {




    vec4 texture = texture(textureMap, Ver.texCoord);
    fragColor = vec4(1) * Ver.color ;

    fragColor *=  texture;
}

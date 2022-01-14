#version 400

uniform sampler2D textureMap;
uniform int useTexture;

out vec4 fragColor;

in Vertex {
	vec2  texCoord;
	vec4  color;
} Vert;

    void main(){
	      fragColor = Vert.color;
	    if( useTexture == 1 ){
	        vec4 texture = texture(textureMap, Vert.texCoord);
            if(texture.a<0.1){
        	    discard;
            }
            fragColor *= texture ;
        }
    }

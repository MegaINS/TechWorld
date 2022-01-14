#version 330

uniform sampler2D textureMap;
uniform bvec2 dataTC;


in vec3 mvVertexPos;
in vec4 mvVertexColor;
in vec2 mvVertexTexCoord;

out vec4 fragColor;

//struct Fog
//{
//    int activeFog;
//    vec3 colour;
//    float density;
//};



vec4 calcFog(vec3 pos, vec4 colour/*, Fog fog, vec3 ambientLight, DirectionalLight dirLight*/){

    vec4 fogColour = vec4(220f/256f,1,1,1);
    float fogDensity = 0.00005;

    vec3 fogColor = /*fog.colour */fogColour.xyz /** (ambientLight + dirLight.colour * dirLight.intensity)*/;
    float distance = length(pos);
    float fogFactor = 1.0 / exp( (distance * fogDensity/*fog.density*/)* (distance *fogDensity/* fog.density*/));
    fogFactor = clamp( fogFactor, 0.0, 1.0 );

    vec3 resultColour = mix(fogColor, colour.xyz, fogFactor);
    return vec4(resultColour.xyz, colour.w);
}

void main() {
    vec4 texture = texture(textureMap, mvVertexTexCoord);
    if(texture.a<0.1){
        discard;
    }
    vec4 color = vec4(1) * mvVertexColor * texture;


    fragColor = calcFog(mvVertexPos, color/*, fog, ambientLight, directionalLight*/);

}

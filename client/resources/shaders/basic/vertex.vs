#version 400

layout (location=0) in vec3 position;
layout (location=1) in vec4 colors;
layout (location=2) in vec2 textures;
layout (location=3) in vec3 normal;




uniform mat4 view;
uniform mat4 model;
uniform mat4 projection;


out Vertex {
	vec2  texCoord;
	vec4  color;
} Vert;




void main()
{

 	Vert.color = colors;
    Vert.texCoord = textures;
  if(model!= 0){
  gl_Position = projection * view *  model * vec4(position, 1.0);
  }else{
  gl_Position =  projection  * view * vec4(position, 1.0);
  }
}
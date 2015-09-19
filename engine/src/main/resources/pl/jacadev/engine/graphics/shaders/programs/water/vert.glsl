#version 450

uniform mat4 perspMatrix;
uniform mat4 mMatrix;
uniform mat4 cameraMatrix;

in vec4 inVertex;

uniform vec3 camPos;
out vec3 vPos;
out vec3 vEyeDir;

void main(void) {
    vec4 pos = mMatrix * inVertex;
    vPos = pos.xyz;
    gl_Position = perspMatrix * cameraMatrix * pos;
}
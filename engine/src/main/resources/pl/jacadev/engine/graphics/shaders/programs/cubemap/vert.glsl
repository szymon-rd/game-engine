#version 430

attribute vec4 inVertex;
uniform mat4 perspMatrix;
uniform mat4 rotMatrix;
uniform mat4 mRotMatrix;

uniform bool rmirror;

out vec3 vTexCoord;

void main(void) {
    vTexCoord = normalize(inVertex.xyz);
    if(rmirror) gl_Position = perspMatrix * mRotMatrix * inVertex;
    else gl_Position = perspMatrix * rotMatrix * inVertex;
}
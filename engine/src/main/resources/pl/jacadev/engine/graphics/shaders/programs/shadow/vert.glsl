#version 450

uniform mat4 perspMatrix;
uniform mat4 mMatrix;
uniform mat4 cameraMatrix;

/* Basic data */
layout(location = 0) in vec4 inVertex;

void main(void) {
    gl_Position = perspMatrix * cameraMatrix * mMatrix * inVertex;
}
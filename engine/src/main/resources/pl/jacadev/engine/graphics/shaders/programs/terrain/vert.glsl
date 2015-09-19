#version 450

struct DirLight {
    vec3 color;
    vec3 amColor;
    vec3 dir;
    sampler2D shadowMap;
    mat4 persp;
    mat4 camera;
};

uniform mat4 perspMatrix;

uniform mat4 mMatrix;
uniform mat4 rotMatrix;

uniform mat4 cameraMatrix;
uniform vec3 cameraPos;

uniform bool rmirror;
uniform mat4 mCameraMatrix;

/* Lighting */
uniform DirLight directionalLight;
out vec3 vPos3;
out vec3 vEyeDir;
out vec4 lightPerspCoords;

/* Basic data */
layout(location = 0) in vec4 inVertex;
layout(location = 1) in vec2 inTexCoord; out vec2 vTexCoord;
layout(location = 2) in vec3 inNormal; out vec3 vNormal;

void main(void) {
    mat3 normalMatrix;
    normalMatrix[0] = rotMatrix[0].xyz;
    normalMatrix[1] = rotMatrix[1].xyz;
    normalMatrix[2] = rotMatrix[2].xyz;

    vec4 vPos = mMatrix * inVertex;
    vPos3 = vPos.xyz / vPos.w;
    vNormal = normalMatrix * inNormal;
    vEyeDir = normalize(vPos3 - cameraPos);

    lightPerspCoords = directionalLight.persp * directionalLight.camera * vPos;

    vTexCoord = inTexCoord;
    if(rmirror) gl_Position = perspMatrix * mCameraMatrix * vPos;
    else gl_Position = perspMatrix * cameraMatrix * vPos;
}
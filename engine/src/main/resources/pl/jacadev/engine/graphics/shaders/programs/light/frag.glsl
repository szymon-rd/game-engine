#version 450
precision highp float;

vec3 getLight();

struct DirLight {
    vec3 color;
    vec3 amColor;
    vec3 dir;
};

struct LightSource {
    vec3 pos;
    vec3 color;
    vec3 amColor;
    float attenuation;
    float gradient;
};

struct Fog {
 float density;
 float gradient;
};

//Lighting
uniform DirLight directionalLight;
const int MAX_LIGHTS = 10;
uniform LightSource lights[MAX_LIGHTS];
uniform int numLights;
uniform bool lightingEnabled = true;
uniform float shininess;

//Components
uniform Fog fog;

//Basic rendering stuff
uniform float brightness = 1;
uniform bool rmirror;
uniform float waterlvl;
uniform sampler2DArray textureA;
uniform vec3 cameraPos;

//From vertex shader
in vec3 vNormal;
in vec3 vPos3;
in vec3 vTexCoord;
in vec3 vEyeDir;

out vec4 fragColor;


void main(void) {
    //Light and texturing
    if(rmirror && (vPos3.y < waterlvl)) discard;
    else if(lightingEnabled) fragColor = vec4(getLight(), 1) * texture(textureA, vTexCoord);
    else fragColor = texture(textureA, vTexCoord);

    //Fog
    float dist = length(cameraPos.xz - vPos3.xz);
    fragColor.a *= clamp(exp(-pow((dist * fog.density), fog.gradient)), 0.0, 1.0);

    //Brightness
    fragColor.rgb *= brightness;
}

vec3 getLight(){
    vec3 totalLight = vec3(0);
    //Directional light
    //Ambient
    vec3 ambient = directionalLight.amColor;

    //Diffuse
    float diff = max(0, dot(vNormal, directionalLight.dir));

    //Specular
    float specular = 0;
    if(shininess > 0) {
        vec3 reflection = normalize(reflect(-directionalLight.dir, vNormal));
        float spec = max(0.0, dot(-vEyeDir, reflection));
        float specVal = pow(spec, shininess);
        specular = diff * specVal;
    }
    //Sum
    totalLight += (((diff + specular) * directionalLight.color) + ambient);

    //Spot light
    for(int i = 0; i < numLights; i++){

        //Attenuation
        float distance = length(lights[i].pos - vPos3);
        float att = exp(-pow((distance * lights[i].attenuation), lights[i].gradient));
        if(att < 0.01) continue;

        //Ambient
        vec3 ambient = lights[i].amColor;

        //Diffuse
        vec3 lightDir = normalize(lights[i].pos - vPos3);
        float diff = max(0, dot(vNormal, lightDir));

        //Specular
        float specular = 0;
        if(shininess > 0) {
            vec3 reflection = normalize(reflect(-lightDir, vNormal));
            float spec = max(0.0, dot(-vEyeDir, reflection));
            float specVal = pow(spec, shininess);
            specular = diff * specVal;
        }

        //Sum
        totalLight += (((diff + specular) * lights[i].color) + ambient) * att;
    }
    return totalLight;
}
#version 450
precision highp float;
vec4 getTexel(vec4 materialTxl);
float getShininess(vec4 materialTxl);
vec3 getLight(vec4 materialTxl);
float sampleShadowMap(sampler2D shadowMap, vec4 shadowMapCoords);
const int CHUNK_SIZE = 256;

struct DirLight {
    vec3 color;
    vec3 amColor;
    vec3 dir;
    sampler2D shadowMap;
    mat4 persp;
    mat4 camera;
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

struct Terrain {
    float shininess[3];
    sampler2DArray textures;
    sampler2D materialsMap;
};

//Lighting
uniform DirLight directionalLight;
const int MAX_LIGHTS = 10;
uniform LightSource lights[MAX_LIGHTS];
uniform int numLights;
uniform bool lightingEnabled = true;
uniform float brightness = 1;

//Components
uniform Terrain terrain;
uniform Fog fog;

//Basic rendering stuff
uniform vec3 cameraPos;
uniform bool rmirror;
uniform float waterlvl;

//From vertex shader
in vec3 vNormal;
in vec3 vPos3;
in vec2 vTexCoord;
in vec3 vEyeDir;
in vec4 lightPerspCoords;

out vec4 fragColor;

void main(void) {
    vec4 materialTxl = texture(terrain.materialsMap, vec2(fract(vPos3.x / CHUNK_SIZE), fract(vPos3.z / CHUNK_SIZE)));
    if(rmirror && (vPos3.y < waterlvl)) discard;
    else if(lightingEnabled){
        fragColor.rgb = pow(getLight(materialTxl), vec3(2)); //Some lighting effect, looks better. TODO Replace with some more sensible tone mapping.
        fragColor.a = 1;
        fragColor *= getTexel(materialTxl);
    } else{
        fragColor = getTexel(materialTxl);
    }
    float dist = length(cameraPos.xz - vPos3.xz);
    fragColor.a *= clamp(exp(-pow((dist * fog.density), fog.gradient)), 0.0, 1.0);
    fragColor.rgb *= brightness;
}


float sampleShadowMap(sampler2D shadowMap, vec4 shadowMapCoords){
    vec3 realCoords = ((shadowMapCoords.xyz) + 1) / 2;
    if(realCoords.x > 1.0 || realCoords.x < 0.0
        || realCoords.y > 1.0 || realCoords.y < 0.0
        || realCoords.z > 1.0 || realCoords.z < 0.0) return 1.0;
    else {
        float compareDepth = realCoords.z;
        vec2 moments = texture(shadowMap, realCoords.xy, -100).rg;
        float p = step(compareDepth, moments.r);
        float variance = max(moments.y - moments.x * moments.x, 0.00002);
        float d = compareDepth - moments.x;
        float pMax = smoothstep(0.2, 1.0, variance / (variance + d*d));
        return max(p, pMax);
    }
}

vec3 getLight(vec4 materialTxl){
    float shininess = getShininess(materialTxl);
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

    float shadowTexel = sampleShadowMap(directionalLight.shadowMap, lightPerspCoords);
    //Sum
    totalLight += (((diff + specular) * directionalLight.color) * shadowTexel + ambient);

    //Spot light
    for(int i = 0; i < numLights; i++){
        vec3 ambient = lights[i].amColor;

        //Diffuse
        vec3 lightDir = normalize(lights[i].pos - vPos3);
        float diff = max(0, dot(vNormal, lightDir));

        //Specular
        float specular = 0;
        if(shininess > 0) {
            vec3 vReflection = normalize(reflect(-lightDir, vNormal));
            float spec = max(0.0, dot(-vEyeDir, vReflection));
            float specVal = pow(spec, shininess);
            specular = diff * specVal;
        }
        float distance = length(lights[i].pos - vPos3);
        totalLight += (ambient + ((diff + specular) * lights[i].color)) * exp(-pow((distance * lights[i].attenuation), lights[i].gradient));
    }
    return totalLight;
}

vec4 getTexel(vec4 materialTxl){
    return (texture(terrain.textures, vec3(vTexCoord.s, vTexCoord.t, 0)) * materialTxl.r
                + texture(terrain.textures, vec3(vTexCoord.s, vTexCoord.t, 1)) * materialTxl.g
                + texture(terrain.textures, vec3(vTexCoord.s, vTexCoord.t, 2)) * materialTxl.b);

}

float getShininess(vec4 materialTxl){
    return (terrain.shininess[0] * materialTxl.r + terrain.shininess[1] * materialTxl.g + terrain.shininess[2] * materialTxl.b);
}
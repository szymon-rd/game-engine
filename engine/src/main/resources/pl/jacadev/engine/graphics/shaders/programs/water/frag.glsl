#version 450
precision highp float;

//Loads of constants
#define CHUNK_SIZE 256.0
#define NORMAL vec3(0,1,0)
#define DUDV_SIZE 50
#define WAVES_STR 0.015
#define NRM_MP_SIZE 5
#define NRM_MP_DIR vec2(8,8)
#define NRM_DIF_STR 0.2
#define WATER_COLOR vec3(0.7,0.7,1)
#define SHININESS 1000
#define SPEC_COEFF 0.5
#define REF_BASE 0.5
#define MIN_DEPTH 2
#define TRANSPARENCY 0.5

vec4 getLight(vec3 normal, vec3 direction);
float getDepthVisibility();

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
uniform float brightness = 1;

//Water rendering
uniform sampler2D mirrorTex;
uniform sampler2D dudvTex;
uniform sampler2D normalTex;
uniform float displacement;

//Basic rendering stuff
uniform vec3 camPos;
uniform int screenW;
uniform int screenH;
uniform bool rmirror;

//Components
uniform Fog fog;

//Info about chunks
uniform sampler2D hMap;

//From vertex shader
in vec3 vPos;
in vec3 vEyeDir;

out vec4 fragColor;

void main(void) {
    if(rmirror) discard;
    else{
        //Normal taken from normal map
        vec3 normal = NORMAL + (texture(normalTex, (vPos.xz) / NRM_MP_SIZE + displacement * NRM_MP_DIR).rgb * NRM_DIF_STR);

        //Onscreen position shifted by waves
        vec2 screenPos = vec2(gl_FragCoord.x / screenW, gl_FragCoord.y / screenH);
        vec3 waveShift = (vec3(0.5) - texture(dudvTex, (vPos.xz) / DUDV_SIZE + displacement).rgb) * WAVES_STR;

        vec2 waveShiftedPos = screenPos + (waveShift.rg);
        waveShiftedPos = vec2(clamp(waveShiftedPos.x, 0, 1), clamp(waveShiftedPos.y, 0, 1));

        //Lighting
        vec3 relativeVector = camPos - vPos;
        vec3 direction = normalize(relativeVector);
        vec4 totalLight = getLight(normal, direction);

        //Reflection
        vec3 reflectionColor = texture(mirrorTex, vec2(waveShiftedPos.x, 1 - waveShiftedPos.y), -100).rgb; /* Only god knows why there has to be a bias.*/

        //Reflection visibility and combining everything
        fragColor.a = (((1 - dot(direction, NORMAL)) + REF_BASE) / TRANSPARENCY) * getDepthVisibility();
        fragColor.a += totalLight.a;
        fragColor.rgb = reflectionColor + SPEC_COEFF * totalLight.rgb;
        fragColor.rgb *= brightness;

        //Fog
        float distance = length(relativeVector);
        fragColor.a *= clamp(exp(-pow((distance * fog.density), fog.gradient)), 0.0, 1.0);
    }
}

float getDepthVisibility(){ //If depth is lower than MIN_DEPTH returns depth/MIN_DEPTH, otherwise returns 1.
    vec2 chunkCoords = vec2(mod(vPos.x / CHUNK_SIZE, 1), mod(vPos.z / CHUNK_SIZE, 1));
    float height = texture(hMap, chunkCoords).r;
    float diff = (vPos.y - height);
    return clamp(log(diff / MIN_DEPTH), 0.001, 1);
}

vec4 getLight(vec3 normal, vec3 direction){

    //Directional light
    vec4 totalLight = vec4(0);
    vec3 vReflection = normalize(reflect(-directionalLight.dir, normal));
    float spec = max(0.0, dot(direction, vReflection));
    float specular = pow(spec, SHININESS);
    totalLight.rgb += specular * (directionalLight.color + directionalLight.amColor);

    //Spot light
    for(int i = 0; i < numLights; i++){
        vec3 lightDir = normalize(lights[i].pos - vPos);
        vec3 vReflection = normalize(reflect(-lightDir, normal));
        float spec = max(0.0, dot(direction, vReflection));
        float specular = pow(spec, SHININESS);
        float distance = length(lights[i].pos - vPos);
        totalLight.rgb += specular * (lights[i].color + lights[i].amColor) * exp(-pow((distance * lights[i].attenuation), lights[i].gradient));
    }
    totalLight.a = (totalLight.r + totalLight.g + totalLight.b) / 3;
    return totalLight;
}
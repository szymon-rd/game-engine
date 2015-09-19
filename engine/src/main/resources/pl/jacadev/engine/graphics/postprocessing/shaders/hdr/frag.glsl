#version 430
precision mediump float;

uniform sampler2DMS view;
uniform sampler2D brightnessMap;
uniform sampler2D pass1;
uniform sampler2D pass2;
uniform sampler2D pass3;
uniform sampler2D pass4;
uniform sampler2D pass5;
uniform sampler2D pass6;
uniform float exposure = 1.0;
uniform float bloomLevel = 0.7;
uniform int samples;

uniform bool debug = false;

in vec2 texCoord;

out vec4 fragColor;


void main(void) {

    vec4 brightPass = texture(brightnessMap, texCoord);
    vec4 blur1 = texture(pass1, texCoord);
    vec4 blur2 = texture(pass2, texCoord);
    vec4 blur3 = texture(pass3, texCoord);
    vec4 blur4 = texture(pass4, texCoord);
    vec4 blur5 = texture(pass5, texCoord);
    vec4 blur6 = texture(pass6, texCoord);

    vec4 bloom = brightPass + (blur1 + blur2 + blur3 + blur4 + blur5 + blur6) * bloomLevel;

    ivec2 size = textureSize(view);
    ivec2 iTexCoord = ivec2(floor(size * texCoord));
    vec3 color = vec3(0);
    for(int i = 0; i < samples; i++){
        color += texelFetch(view, iTexCoord, i).rgb;
    }
    color /= samples;
    color += bloom.rgb;

    if(!debug){
    fragColor.rgb = 1 - exp2(-color * exposure);
    } else fragColor.rgb = blur6.rgb;
    fragColor.a = 1;
}
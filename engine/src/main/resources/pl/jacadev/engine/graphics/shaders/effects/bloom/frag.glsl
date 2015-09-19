#version 430
precision mediump float;

uniform sampler2DMS view;
uniform int samples;
uniform float bloomLimit = 0.8;

in vec2 texCoord;

out vec4 fragBrightColor;

void main(void) {
    ivec2 size = textureSize(view);
    ivec2 iTexCoord = ivec2(floor(size * texCoord));
    vec4 color = vec4(0,0,0,1);
    for(int i = 0; i < samples; i++){
        color.rgb += texelFetch(view, iTexCoord, i).rgb;
    }

    color.rgb /= samples;

    vec3 brightColor = max(color.rgb - vec3(bloomLimit), vec3(0.0));
    float bright = dot(brightColor, vec3(1.0));
    bright = smoothstep(0.0,0.5,bright);

    fragBrightColor.rgb = mix(vec3(0.0), color.rgb, bright).rgb;
    fragBrightColor.a = 1.0;
}
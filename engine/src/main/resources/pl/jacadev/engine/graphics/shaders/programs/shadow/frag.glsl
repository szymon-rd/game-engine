#version 430
precision highp float;

out vec2 fragColor;

void main(void) {
    float depth = gl_FragCoord.z;

    float dx = dFdx(depth);
    float dy = dFdy(depth);
    float moment = depth * depth + 0.25 * (dx * dx + dy * dy);
    fragColor = vec2(depth, moment);
}
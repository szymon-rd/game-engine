#version 430
#define RGBA 0
#define RGB 1
#define RG 2
#define DEPTH 3
precision mediump float;

uniform sampler2D textureS;
uniform vec2 blurShifts[25];
uniform int format;

in vec2 texCoord;

out vec4 fragColorV4;
out vec3 fragColorV3;
out vec2 fragColorV2;
out float fragColorFloat;

void main(void) {
    switch(format) {
        case RGBA:
            vec4 samplesV4[25];

            for (int i = 0; i < 25; i++){
                samplesV4[i] = texture(textureS, clamp(texCoord + blurShifts[i], 0.0, 1.0), -100);
            }

            fragColorV4 = (
                (1.0 * (samplesV4[0] + samplesV4[4] + samplesV4[20] + samplesV4[24])) +
                (4.0 * (samplesV4[1] + samplesV4[3] + samplesV4[5] + samplesV4[9] + samplesV4[15] + samplesV4[19] + samplesV4[21] + samplesV4[23])) +
                (7.0 * (samplesV4[2] + samplesV4[10] + samplesV4[14] + samplesV4[22])) +
                (16.0 * (samplesV4[6] + samplesV4[8] + samplesV4[16] + samplesV4[18])) +
                (26.0 * (samplesV4[7] + samplesV4[11] + samplesV4[13] + samplesV4[17])) +
                (41.0 * samplesV4[12])) / 273.0;
            break;

        case RGB:
            vec3 samplesV3[25];

            for (int i = 0; i < 25; i++){
                samplesV3[i] = texture(textureS, clamp(texCoord + blurShifts[i], 0.0, 1.0), -100).rgb;
            }

            fragColorV3 = (
                (1.0 * (samplesV3[0] + samplesV3[4] + samplesV3[20] + samplesV3[24])) +
                (4.0 * (samplesV3[1] + samplesV3[3] + samplesV3[5] + samplesV3[9] + samplesV3[15] + samplesV3[19] + samplesV3[21] + samplesV3[23])) +
                (7.0 * (samplesV3[2] + samplesV3[10] + samplesV3[14] + samplesV3[22])) +
                (16.0 * (samplesV3[6] + samplesV3[8] + samplesV3[16] + samplesV3[18])) +
                (26.0 * (samplesV3[7] + samplesV3[11] + samplesV3[13] + samplesV3[17])) +
                (41.0 * samplesV3[12])) / 273.0;
            break;

        case RG:
            vec2 samplesV2[25];

            for (int i = 0; i < 25; i++){
                samplesV2[i] = texture(textureS, clamp(texCoord + blurShifts[i], 0.0, 1.0), -100).rg;
            }

            fragColorV2 = (
                (1.0 * (samplesV2[0] + samplesV2[4] + samplesV2[20] + samplesV2[24])) +
                (4.0 * (samplesV2[1] + samplesV2[3] + samplesV2[5] + samplesV2[9] + samplesV2[15] + samplesV2[19] + samplesV2[21] + samplesV2[23])) +
                (7.0 * (samplesV2[2] + samplesV2[10] + samplesV2[14] + samplesV2[22])) +
                (16.0 * (samplesV2[6] + samplesV2[8] + samplesV2[16] + samplesV2[18])) +
                (26.0 * (samplesV2[7] + samplesV2[11] + samplesV2[13] + samplesV2[17])) +
                (41.0 * samplesV2[12])) / 273.0;
            break;

        case DEPTH:
            float samplesFloat[25];

            for (int i = 0; i < 25; i++){
                samplesFloat[i] = texture(textureS, clamp(texCoord + blurShifts[i], 0.0, 1.0), -100).r;
            }

            fragColorFloat = (
                (1.0 * (samplesFloat[0] + samplesFloat[4] + samplesFloat[20] + samplesFloat[24])) +
                (4.0 * (samplesFloat[1] + samplesFloat[3] + samplesFloat[5] + samplesFloat[9] + samplesFloat[15] + samplesFloat[19] + samplesFloat[21] + samplesFloat[23])) +
                (7.0 * (samplesFloat[2] + samplesFloat[10] + samplesFloat[14] + samplesFloat[22])) +
                (16.0 * (samplesFloat[6] + samplesFloat[8] + samplesFloat[16] + samplesFloat[18])) +
                (26.0 * (samplesFloat[7] + samplesFloat[11] + samplesFloat[13] + samplesFloat[17])) +
                (41.0 * samplesFloat[12])) / 273.0;
            break;
    }
}
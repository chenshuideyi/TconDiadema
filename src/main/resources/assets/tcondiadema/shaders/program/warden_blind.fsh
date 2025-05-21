#version 440

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;

uniform mat4 ProjMat;
uniform mat4 InvProjMat;
uniform mat4 InvViewMat;
uniform float CycleTime;

in vec2 texCoord;

out vec4 fragColor;

#define FAR 1.5 // 这个是最远视距

#define COLOR_FAR vec3(0.07, 0.55, 0.71)
#define COLOR_CLS vec3(0.059, 0.859, 0.835)

#define PI 3.1415926535897932384626433832795
#define TAU 6.283185307179586476925286766559

vec3 hash33(vec3 p) {
    p = vec3(dot(p, vec3(127.1, 311.7, 74.7)),
    dot(p, vec3(269.5, 183.3, 246.1)),
    dot(p, vec3(113.5, 271.9, 124.6)));
    return fract(sin(p) * 43758.5453123);
}

// 3D Cellular (Voronoi/Worley) noise function with time-based animation
// p: input 3D coordinate
// time: current time
// period: duration of one full animation cycle
// animation_amplitude: how far the points move within their cells (0.0 to ~0.5 is reasonable)
//                      0.0 means static cellular noise.
float cell(vec3 p, float time, float animation_amplitude) {
    vec3 Pi = floor(p); // Integer part of p (cell ID)
    // vec3 Pf = fract(p); // Fractional part of p (position within cell) - not strictly needed for F1 calculation if p is used directly

    float min_dist = 1000.0; // Initialize with a large value

    // Iterate over a 3x3x3 grid of cells (current cell and its 26 neighbors)
    for (int k = -1; k <= 1; ++k) {
        for (int j = -1; j <= 1; ++j) {
            for (int i = -1; i <= 1; ++i) {
                vec3 cell_offset = vec3(float(i), float(j), float(k));
                vec3 current_cell_id = Pi + cell_offset;

                // Base random position for the feature point in this cell (static part)
                vec3 point_base_offset = hash33(current_cell_id);

                // Time-dependent offset for animation
                // We can use another hash to give each point a unique phase for its motion
                vec3 anim_phase_offsets = hash33(current_cell_id + vec3(7.31, 5.17, 9.83)); // Different seed for phases

                float ang_x = TAU * (time + anim_phase_offsets.x);
                float ang_y = TAU * (time * 2 + anim_phase_offsets.y);
                float ang_z = TAU * (time + anim_phase_offsets.z); // Slightly different speed for z for variety

                vec3 time_driven_offset = vec3(
                sin(ang_x),
                cos(ang_y),
                sin(ang_z)
                ) * animation_amplitude;

                // Final feature point position within its cell [0,1)^3, then add cell_id for world space
                vec3 feature_point_relative = fract(point_base_offset + time_driven_offset);
                vec3 feature_point_world = current_cell_id + feature_point_relative;

                float dist = distance(p, feature_point_world);
                min_dist = min(min_dist, dist);
            }
        }
    }
    return min_dist;
}

void main() {
    vec4 incolor = texture(DiffuseSampler, texCoord);
    float depth = texture(DepthSampler, texCoord).r;

    vec4 pos4 = InvProjMat * vec4(texCoord * 2 - vec2(1), depth * 2 - 1, 1.0);
    vec3 viewpos = pos4.xyz / pos4.w;

    float l = length(viewpos);
    float x = min(l / FAR, 1);
    float s = step(0.5, x);
    float y = -2 * x + 2;
    float f = (x < 0.5) ? (16 * x * x * x * x * x) : (1 - y * y * y * y * y / 2.0);
    float fd = f * 4 * FAR;
    // float fd = (s * (16 * x * x * x * x * x) + (1-s) * (1 - pow(-2 * x + 2, 5) / 2)) * 4 * FAR;
    viewpos = viewpos / l * fd;

    pos4 = InvViewMat * vec4(viewpos, 1.0);
    vec3 worldpos = pos4.xyz / pos4.w;

    float line = cell(worldpos * vec3(2), CycleTime, 0.3);
    line = line * line * line;
    vec3 color = mix(COLOR_CLS, COLOR_FAR, f) * line;
    color *= hash33(vec3(texCoord, CycleTime)) / 4 + 0.875;
    fragColor = vec4(color, 1.0);
}
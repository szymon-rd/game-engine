package pl.jacadev.engine.graphics.testing;


import pl.jacadev.engine.graphics.models.Model;
import pl.jacadev.engine.graphics.models.VAOModel;
import pl.jacadev.engine.graphics.shaders.StandardProgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jaca777
 *         Created 21.12.14 at 19:07
 */
public class ModelsFactory {

    private static class Vertex {

        private float[] xyz = new float[3];
        private float[] nrm = new float[3];
        private float[] txt = new float[2];

        public Vertex(float[] vtx, float[] normal, float[] txtcoords) {
            System.arraycopy(vtx, 0, xyz, 0, 3);
            if (normal != null)
                System.arraycopy(normal, 0, this.nrm, 0, 3);
            if (txtcoords != null)
                System.arraycopy(txtcoords, 0, this.txt, 0, 2);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Vertex other = (Vertex) obj;
            if (!Arrays.equals(this.xyz, other.xyz)) {
                return false;
            }
            if (!Arrays.equals(this.nrm, other.nrm)) {
                return false;
            }
            if (!Arrays.equals(this.txt, other.txt)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 83 * hash + Arrays.hashCode(this.xyz);
            hash = 83 * hash + Arrays.hashCode(this.nrm);
            hash = 83 * hash + Arrays.hashCode(this.txt);
            return hash;
        }
    }

    private List<Vertex> vertices = new ArrayList<Vertex>();
    private List<Short> indexes = new ArrayList<Short>();

    public void addTriangle(float[][] triangle, float[][] normals, float[][] textures) {
        for (int k = 0; k < 3; k++) {
            addVertex(triangle[k], normals[k], textures[k]);
        }
    }

    public void addVertex(float[] vertex, float[] normal, float[] texCoords) {
        Vertex v = new Vertex(vertex, normal, texCoords);
        short index = (short) vertices.indexOf(v);
        if (index < 0) {
            vertices.add(v);
            index = (short) (vertices.size() - 1);
        }
        indexes.add(index);
    }

    public Model buildModel() {
        float[] fv = new float[vertices.size() * 4];
        for (int i = 0; i < vertices.size(); i++) {
            fv[i << 2] = vertices.get(i).xyz[0];
            fv[(i << 2) + 1] = vertices.get(i).xyz[1];
            fv[(i << 2) + 2] = vertices.get(i).xyz[2];
            fv[(i << 2) + 3] = 1.0f;
        }

        float[] fn = new float[vertices.size() * 3];
        for (int i = 0; i < vertices.size(); i++) {
            fn[i * 3 + 0] = vertices.get(i).nrm[0];
            fn[i * 3 + 1] = vertices.get(i).nrm[1];
            fn[i * 3 + 2] = vertices.get(i).nrm[2];
        }

        float[] ft = new float[vertices.size() * 2];
        for (int i = 0; i < vertices.size(); i++) {
            ft[i * 2 + 0] = vertices.get(i).txt[0];
            ft[i * 2 + 1] = vertices.get(i).txt[1];
        }

        int[] idx = new int[indexes.size()];
        for (int i = 0; i < indexes.size(); i++) {
            idx[i] = indexes.get(i);
        }
        return new Model(fv, ft, fn, idx, StandardProgram.ATTRIBUTES);
    }

    public VAOModel buildVAOModel() {
        float[] fv = new float[vertices.size() * 4];
        for (int i = 0; i < vertices.size(); i++) {
            fv[i << 2] = vertices.get(i).xyz[0];
            fv[(i << 2) + 1] = vertices.get(i).xyz[1];
            fv[(i << 2) + 2] = vertices.get(i).xyz[2];
            fv[(i << 2) + 3] = 1.0f;
        }

        float[] fn = new float[vertices.size() * 3];
        for (int i = 0; i < vertices.size(); i++) {
            fn[i * 3 + 0] = vertices.get(i).nrm[0];
            fn[i * 3 + 1] = vertices.get(i).nrm[1];
            fn[i * 3 + 2] = vertices.get(i).nrm[2];
        }

        float[] ft = new float[vertices.size() * 2];
        for (int i = 0; i < vertices.size(); i++) {
            ft[i * 2 + 0] = vertices.get(i).txt[0];
            ft[i * 2 + 1] = vertices.get(i).txt[1];
        }

        int[] idx = new int[indexes.size()];
        for (int i = 0; i < indexes.size(); i++) {
            idx[i] = indexes.get(i);
        }
        return new VAOModel(fv, ft, fn, idx, StandardProgram.ATTRIBUTES);
    }


    public static final float[] QUAD_VERTICES = new float[]{
            -1.0f, -1.0f, 0.0f, 1.0f,
            1.0f, -1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            -1.0f, 1.0f, 0.0f, 1.0f
    };

    public static final float[] QUAD_TEX_COORDS = new float[]{
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f
    };

    public static final int[] QUAD_INDICES = new int[]{
            0, 1, 2, 0, 2, 3
    };

    public static final int QUAD_INDICES_AMOUNT = QUAD_INDICES.length;


    public static VAOModel makeRect(float w, float h) {
        float xmax = w / 2;
        float xmin = -xmax;
        float ymax = h / 2;
        float ymin = -ymax;
        return new VAOModel(new float[]
                {xmin, ymin, 0, 1.0f,
                        xmax, ymin, 0, 1.0f,
                        xmax, ymax, 0, 1.0f,
                        xmin, ymax, 0, 1.0f}, new float[]
                {0.0f, 0.0f,
                        1.0f, 0.0f,
                        1.0f, 1.0f,
                        0.0f, 1.0f
                }, new float[]
                {0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f,
                }, new int[]{0, 1, 2, 0, 2, 3}, StandardProgram.ATTRIBUTES);
    }


    public static VAOModel makeCube(float width, float height, float depth) {
        return makeCube(width, height, depth, StandardProgram.ATTRIBUTES);
    }

    public static VAOModel makeCube(float width, float height, float depth, int[] attributes) {
        float xmax = width / 2;
        float xmin = -xmax;
        float ymax = height / 2;
        float ymin = -ymax;
        float zmax = depth / 2;
        float zmin = -zmax;

        float[] vertices = new float[]{
                xmin, ymax, zmin, 1.0f,
                xmin, ymin, zmin, 1.0f,
                xmax, ymin, zmin, 1.0f,
                xmax, ymax, zmin, 1.0f,

                xmax, ymax, zmin, 1.0f,
                xmax, ymin, zmin, 1.0f,
                xmax, ymin, zmax, 1.0f,
                xmax, ymax, zmax, 1.0f,

                xmin, ymax, zmax, 1.0f,
                xmin, ymin, zmax, 1.0f,
                xmax, ymin, zmax, 1.0f,
                xmax, ymax, zmax, 1.0f,

                xmin, ymax, zmin, 1.0f,
                xmin, ymin, zmin, 1.0f,
                xmin, ymax, zmax, 1.0f,
                xmin, ymin, zmax, 1.0f,

                xmin, ymax, zmin, 1.0f,
                xmax, ymax, zmin, 1.0f,
                xmin, ymax, zmax, 1.0f,
                xmax, ymax, zmax, 1.0f,

                xmin, ymin, zmin, 1.0f,
                xmax, ymin, zmin, 1.0f,
                xmin, ymin, zmax, 1.0f,
                xmax, ymin, zmax, 1.0f
        };

        float[] texCoords = new float[]{
                1, 0,
                1, 1,
                0, 1,
                0, 0,

                1, 0,
                1, 1,
                0, 1,
                0, 0,

                1, 0,
                1, 1,
                0, 1,
                0, 0,

                0, 0,
                0, 1,
                1, 0,
                1, 1,

                1, 0,
                0, 0,
                1, 1,
                0, 1,

                0, 1,
                1, 1,
                0, 0,
                1, 0
        };
        float[] normals = new float[]{
                0, 0, -1,
                0, 0, -1,
                0, 0, -1,
                0, 0, -1,
                1, 0, 0,
                1, 0, 0,
                1, 0, 0,
                1, 0, 0,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                -1, 0, 0,
                -1, 0, 0,
                -1, 0, 0,
                -1, 0, 0,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                0, -1, 0,
                0, -1, 0,
                0, -1, 0,
                0, -1, 0
        };

        int[] indices = new int[]{
                2, 1, 0,
                3, 2, 0,

                6, 5, 4,
                7, 6, 4,

                9, 10, 11,
                8, 9, 11,

                13, 15, 14,
                12, 13, 14,

                17, 16, 18,
                19, 17, 18,

                21, 23, 22,
                20, 21, 22
        };

        return new VAOModel(vertices, texCoords, normals, indices, attributes);
    }

    public static ModelsFactory makeSphere(float fRadius, float iSlices, float iStacks) {
        ModelsFactory factory = new ModelsFactory();
        float drho = (float) (3.141592653589) / (float) iStacks;
        float dtheta = 2.0f * (float) (3.141592653589) / (float) iSlices;
        float ds = 1.0f / (float) iSlices;
        float dt = 1.0f / (float) iStacks;
        float t = 1.0f;
        float s = 0.0f;
        int i, j;
        for (i = 0; i < iStacks; i++) {
            float rho = (float) i * drho;
            float srho = (float) (Math.sin(rho));
            float crho = (float) (Math.cos(rho));
            float srhodrho = (float) (Math.sin(rho + drho));
            float crhodrho = (float) (Math.cos(rho + drho));

            s = 0.0f;
            float[][] vVertex = new float[4][3];
            float[][] vNormal = new float[4][3];
            float[][] vTexture = new float[4][2];

            for (j = 0; j < iSlices; j++) {
                float theta = (j == iSlices) ? 0.0f : j * dtheta;
                float stheta = (float) (-Math.sin(theta));
                float ctheta = (float) (Math.cos(theta));

                float x = stheta * srho;
                float y = ctheta * srho;
                float z = crho;

                vTexture[0][0] = s;
                vTexture[0][1] = t;
                vNormal[0][0] = x;
                vNormal[0][1] = y;
                vNormal[0][2] = z;
                vVertex[0][0] = x * fRadius;
                vVertex[0][1] = y * fRadius;
                vVertex[0][2] = z * fRadius;

                x = stheta * srhodrho;
                y = ctheta * srhodrho;
                z = crhodrho;

                vTexture[1][0] = s;
                vTexture[1][1] = t - dt;
                vNormal[1][0] = x;
                vNormal[1][1] = y;
                vNormal[1][2] = z;
                vVertex[1][0] = x * fRadius;
                vVertex[1][1] = y * fRadius;
                vVertex[1][2] = z * fRadius;


                theta = ((j + 1) == iSlices) ? 0.0f : (j + 1) * dtheta;
                stheta = (float) (-Math.sin(theta));
                ctheta = (float) (Math.cos(theta));

                x = stheta * srho;
                y = ctheta * srho;
                z = crho;

                s += ds;
                vTexture[2][0] = s;
                vTexture[2][1] = t;
                vNormal[2][0] = x;
                vNormal[2][1] = y;
                vNormal[2][2] = z;
                vVertex[2][0] = x * fRadius;
                vVertex[2][1] = y * fRadius;
                vVertex[2][2] = z * fRadius;

                x = stheta * srhodrho;
                y = ctheta * srhodrho;
                z = crhodrho;

                vTexture[3][0] = s;
                vTexture[3][1] = t - dt;
                vNormal[3][0] = x;
                vNormal[3][1] = y;
                vNormal[3][2] = z;
                vVertex[3][0] = x * fRadius;
                vVertex[3][1] = y * fRadius;
                vVertex[3][2] = z * fRadius;

                factory.addTriangle(vVertex, vNormal, vTexture);

                // Rearrange for next triangle

                System.arraycopy(vVertex[1], 0, vVertex[0], 0, 3);
                System.arraycopy(vNormal[1], 0, vNormal[0], 0, 3);
                System.arraycopy(vTexture[1], 0, vTexture[0], 0, 2);

                System.arraycopy(vVertex[3], 0, vVertex[1], 0, 3);
                System.arraycopy(vNormal[3], 0, vNormal[1], 0, 3);
                System.arraycopy(vTexture[3], 0, vTexture[1], 0, 2);

                factory.addTriangle(vVertex, vNormal, vTexture);
            }
            t -= dt;
        }
        return factory;
    }
}

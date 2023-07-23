package com.jm.airhockey.objects;

import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glDrawArrays;
import static com.jm.airhockey.Constants.BYTES_PER_FLOAT;

import com.jm.airhockey.data.VertexArray;
import com.jm.airhockey.programs.TextureShaderProgram;

public class Table {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private static final float[] VERTEX_DATA = {
         -0.5f,  0.5f, 1, 0,
         -0.5f, -0.5f, 0, 0,
          0.5f,  0.5f, 1, 1,
          0.5f, -0.5f, 0, 1
    };
    private final VertexArray vertexArray;

    public Table() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(TextureShaderProgram textureShaderProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                textureShaderProgram.getPositionLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);

        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                textureShaderProgram.getTextureUnitCoordinatesLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE
        );
    }

    public void draw() {
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    }
}

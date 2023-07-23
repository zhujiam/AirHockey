package com.jm.airhockey.objects;

import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glDrawArrays;
import static com.jm.airhockey.Constants.BYTES_PER_FLOAT;

import com.jm.airhockey.data.VertexArray;
import com.jm.airhockey.programs.ColorShaderProgram;
import com.jm.airhockey.programs.TextureShaderProgram;

public class Mallet {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private static final float[] VERTEX_DATA = {
        0, -0.4f, 0, 0, 1,
        0,  0.4f, 1, 0, 0
    };

    private final VertexArray vertexArray;

    public Mallet() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram colorShaderProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                colorShaderProgram.getPositionLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);

        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                colorShaderProgram.getTextureUnitCoordinatesLocation(),
                COLOR_COMPONENT_COUNT,
                STRIDE
        );
    }

    public void draw() {
        glDrawArrays(GL_POINTS, 0, 2);
    }
}

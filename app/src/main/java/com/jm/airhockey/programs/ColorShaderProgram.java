package com.jm.airhockey.programs;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.content.Context;

public class ColorShaderProgram extends ShaderProgram{

    private final int uMatrixLocation;

    private final int aPositionLocation;
    private final int aColorLocation;

    public ColorShaderProgram(Context context, int vertexShaderResId, int fragShaderResId) {
        super(context, vertexShaderResId, fragShaderResId);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);

        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aColorLocation = glGetAttribLocation(program, A_COLOR);
    }

    public void setUniforms(float[] matrix) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    public int getPositionLocation() {
        return aPositionLocation;
    }

    public int getTextureUnitCoordinatesLocation() {
        return aColorLocation;
    }
}

package com.jm.airhockey.programs;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.content.Context;

public class TextureShaderProgram extends ShaderProgram{

    private final int uMatrixLocation;
    private final int uTextureUnitLocation;

    private final int aPositionLocation;
    private final int aTextureUnitCoordinatesLocation;

    public TextureShaderProgram(Context context, int vertexShaderResId, int fragShaderResId) {
        super(context, vertexShaderResId, fragShaderResId);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);

        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aTextureUnitCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    public void setUniforms(float[] matrix, int textureId) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);
        // 告诉采样器到纹理单元0读取该纹理
        glUniform1i(uTextureUnitLocation, 0);
    }

    public int getPositionLocation() {
        return aPositionLocation;
    }

    public int getTextureUnitCoordinatesLocation() {
        return aTextureUnitCoordinatesLocation;
    }
}

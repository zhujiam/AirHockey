package com.jm.airhockey.programs;

import static android.opengl.GLES20.glUseProgram;

import android.content.Context;

import com.jm.airhockey.utils.ShaderHelper;
import com.jm.airhockey.utils.TextResourceReader;

public class ShaderProgram {

    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    protected final int program;

    protected ShaderProgram(Context context, int vertexShaderResId, int fragShaderResId) {
        program = ShaderHelper.buildProgram(TextResourceReader.readTextFileFromResource(context, vertexShaderResId),
                TextResourceReader.readTextFileFromResource(context, fragShaderResId));
    }

    public void useProgram() {
        glUseProgram(program);
    }
}

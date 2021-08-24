package com.jm.airhockey.utils;

import android.opengl.GLES20;
import android.util.Log;
import static android.opengl.GLES20.*;

/**
 * Time: 2021/8/24
 * Author: Archer
 * Description:
 */
public class ShaderHelper {

    private static final String TAG = "ShaderHelper";

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programObjId = glCreateProgram();
        if (programObjId == 0) {
            Log.e(TAG, "linkProgram: Could not create program.");
            return 0;
        }
        glAttachShader(programObjId, vertexShaderId);
        glAttachShader(programObjId, fragmentShaderId);
        glLinkProgram(programObjId);
        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjId, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            Log.e(TAG, "linkProgram: Linking of program failed.");
            return 0;
        }
        return programObjId;
    }

    public static boolean validateProgram(int programObjId) {
        glValidateProgram(programObjId);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjId, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.d(TAG, "validateProgram: Result of validating program: " + validateStatus[0]
                + "\nLog: " + glGetProgramInfoLog(programObjId));
        return validateStatus[0] != 0;
    }

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        final int shaderObjId = glCreateShader(type);
        if (shaderObjId == 0) {
            Log.e(TAG, "compileShader: Could not create new shader.");
            return 0;
        }
        glShaderSource(shaderObjId, shaderCode);
        glCompileShader(shaderObjId);
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjId, GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            glDeleteShader(shaderObjId);
            Log.e(TAG, "compileShader: Compilation of shader failed.");
            return 0;
        }
        return shaderObjId;
    }
}

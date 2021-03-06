package com.jm.airhockey;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.jm.airhockey.utils.ShaderHelper;
import com.jm.airhockey.utils.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

/**
 * Time: 2021/8/24
 * Author: Archer
 * Description:
 */
public class MyRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyRenderer";
    private static final boolean DBG = true;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private static final String A_COLOR = "a_Color";
    private static final String A_POSITION = "a_Position";
    private static final String U_MATRIX = "u_Matrix";
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];
    private int uMatrixLocation;
    private int aColorLocation;
    private int aPositionLocation;
    private FloatBuffer vertexData;
    private Context mContext;
    private int mProgram;

    public MyRenderer(Context context) {
        this.mContext = context;
        float[] tableVertices = {
                    0,     0,   1f,   1f,   1f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                 0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                 0.5f,  0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f,  0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,

                -0.5f, 0f, 1f, 0f, 0f,
                 0.5f, 0f, 0f, 0f, 1f,

                0f, -0.25f, 0f, 0f, 1f,
                0f,  0.25f, 1f, 0f, 0f,
        };
        vertexData = ByteBuffer.allocateDirect(tableVertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVertices);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0, 0, 0, 0);
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        mProgram = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        if (DBG) {
            ShaderHelper.validateProgram(mProgram);
        }
        glUseProgram(mProgram);
        aColorLocation = glGetAttribLocation(mProgram, A_COLOR);
        aPositionLocation = glGetAttribLocation(mProgram, A_POSITION);
        uMatrixLocation = glGetUniformLocation(mProgram, U_MATRIX);
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        glEnableVertexAttribArray(aPositionLocation); // ????????????
        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        glEnableVertexAttribArray(aColorLocation); // ????????????
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
       glViewport(0, 0, width, height);
       Matrix.perspectiveM(mProjectionMatrix, 0,45, (float)width / (float)height, 1f, 10f);
       Matrix.setIdentityM(mModelMatrix, 0);
       Matrix.translateM(mModelMatrix, 0, 0, 0, -2.5f);
       Matrix.rotateM(mModelMatrix, 0, -60f, 1f, 0, 0);
       final float[] temp = new float[16];
       Matrix.multiplyMM(temp, 0, mProjectionMatrix, 0, mModelMatrix, 0);
       System.arraycopy(temp, 0, mProjectionMatrix, 0, temp.length);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
        glUniformMatrix4fv(uMatrixLocation, 1, false, mProjectionMatrix, 0);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
        glDrawArrays(GL_LINES, 6, 2);
        glDrawArrays(GL_POINTS, 8, 1);
        glDrawArrays(GL_POINTS, 9, 1);
    }
}

package com.jm.airhockey;

import android.content.Context;
import android.opengl.GLSurfaceView;

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
    private static final int BYTES_PER_FLOAT = 4;
    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";
    private int uColorLocation;
    private int aPositionLocation;
    private FloatBuffer vertexData;
    private Context mContext;
    private int mProgram;

    public MyRenderer(Context context) {
        this.mContext = context;
        float[] tableVertices = {
                -0.5f, -0.5f,
                 0.5f,  0.5f,
                -0.5f,  0.5f,

                -0.5f, -0.5f,
                 0.5f, -0.5f,
                 0.5f,  0.5f,

                -0.5f, 0f,
                 0.5f, 0f,

                0f, -0.25f,
                0f,  0.25f
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
        uColorLocation = glGetUniformLocation(mProgram, U_COLOR);
        aPositionLocation = glGetAttribLocation(mProgram, A_POSITION);
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation); // 使能数据
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
       glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
        glUniform4f(uColorLocation, 1f, 1f, 1f, 1f);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glUniform4f(uColorLocation, 1f, 0, 0, 1f);
        glDrawArrays(GL_LINES, 6, 2);
        glUniform4f(uColorLocation, 0, 0, 0, 1f);
        glDrawArrays(GL_POINTS, 8, 1);
        glUniform4f(uColorLocation, 0, 0, 0, 1f);
        glDrawArrays(GL_POINTS, 9, 1);
    }
}

package com.jm.airhockey;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.jm.airhockey.objects.Mallet;
import com.jm.airhockey.objects.Table;
import com.jm.airhockey.programs.ColorShaderProgram;
import com.jm.airhockey.programs.TextureShaderProgram;
import com.jm.airhockey.utils.ShaderHelper;
import com.jm.airhockey.utils.TextResourceReader;
import com.jm.airhockey.utils.TextureHelper;

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

    private final float[] mProjectionMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];
    private Context mContext;

    private Table mTable;
    private Mallet mMallet;
    private TextureShaderProgram mTextureProgram;
    private ColorShaderProgram mColorProgram;
    private int mTexture;


    public MyRenderer(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0, 0, 0, 0);
        mTable = new Table();
        mMallet = new Mallet();
        mTextureProgram = new TextureShaderProgram(mContext, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
        mColorProgram = new ColorShaderProgram(mContext, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);
        mTexture = TextureHelper.loadTexture(mContext, R.drawable.bg);
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

        mTextureProgram.useProgram();
        mTextureProgram.setUniforms(mProjectionMatrix, mTexture);
        mTable.bindData(mTextureProgram);
        mTable.draw();

        mColorProgram.useProgram();
        mColorProgram.setUniforms(mProjectionMatrix);
        mMallet.bindData(mColorProgram);
        mMallet.draw();
    }
}

package com.pgeeKer.loading_page.view;

import com.example.loading_page.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {

	private AnimationSetupCallback animationSetupCallback;
	private Matrix shaderMatrix;   //着色器基体
	private float axleX, axleY;   //波浪坐标
	private boolean isSink;   //判断是否下沉，如果为true，着色器显示波浪
	private boolean isSetup;
	private Drawable waveDrawable;  //绘制波浪
	private BitmapShader shader;  //波浪着色器
	private float offsetY;

	public boolean isSetup() {
		return isSetup;
	}

	public CustomTextView(Context context) {
		super(context);
		//初始化
		init();
	}

	public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		shaderMatrix = new Matrix();
	}
	
	public float getAxleX() {
		return axleX;
	}

	public void setAxleX(float axleX) {
		this.axleX = axleX;
		invalidate();    //该函数的主要作用是：请求View树进行重绘
	}

	public float getAxleY() {
		return axleY;
	}

	public void setAxleY(float axleY) {
		this.axleY = axleY;
		invalidate();
	}
	
	public boolean isSink() {
		return isSink;
	}

	public void setSink(boolean isSink) {
		this.isSink = isSink;
	}

	public AnimationSetupCallback getAnimationSetupCallback() {
		return animationSetupCallback;
	}

	public void setAnimationSetupCallback(
			AnimationSetupCallback animationSetupCallback) {
		this.animationSetupCallback = animationSetupCallback;
	}	
	
	public interface AnimationSetupCallback {
		public void onSetupAnimation(CustomTextView customTextView);
	}
	

	@Override
	public void setTextColor(ColorStateList colors) {
		super.setTextColor(colors);
		CreateShader();
	}

	@Override
	public void setTextColor(int color) {
		super.setTextColor(color);
		CreateShader();
	}

	/**
	 * 画出波浪与当前背景的颜色
	 * 重复水平的位图，固定垂直方向的颜色
	 */
	private void CreateShader() {
		if (waveDrawable == null) {
			waveDrawable = this.getResources().getDrawable(R.drawable.wave);
		}
		int waveW = waveDrawable.getIntrinsicWidth();
		int waveH = waveDrawable.getIntrinsicHeight();
		
		//创建波浪位图
		Bitmap bitmap = Bitmap.createBitmap(waveW, waveH, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(getCurrentTextColor());
		waveDrawable.setBounds(0, 0, waveW, waveH);
		waveDrawable.draw(canvas);
		
		shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
		getPaint().setShader(shader);
		
		//Y轴偏移量,上下移动
		offsetY = (getHeight() - waveH) / 2;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		CreateShader();
		
		if (!isSetup) {
			isSetup = true;
			if (animationSetupCallback != null) {
				animationSetupCallback.onSetupAnimation(CustomTextView.this);
			}
		}
	}

	@Override
	public void draw(Canvas canvas) {
		//根据下沉状态修改文字的颜色
		if (isSink && shader != null) {
			if (getPaint().getShader() == null) {
				getPaint().setShader(shader);
			}
			
			shaderMatrix.setTranslate(axleX, axleY + offsetY);
			shader.setLocalMatrix(shaderMatrix);
		}else {
			getPaint().setShader(null);
		}
		super.draw(canvas);
	}
}

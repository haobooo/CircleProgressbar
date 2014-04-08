package com.pbstudio.circleprogress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CircleProgressBarInner extends View {
	private static final String TAG = "CircleProgressBar";
	
	private static final int INNER_WIDTH = 16;
	
	private int maxProgress = 100;
	private int progress = 30;
	private int progressStrokeWidth = 28;
	
	private Bitmap innerAnimBitmap;
	private final Matrix mCircleMatrix = new Matrix();
	
	
	//画圆所在的距形区域
	RectF oval;
	Paint paint;
	public CircleProgressBarInner(Context context) {
		super(context);
		
		oval = new RectF();
		paint = new Paint();
		
		setBackgroundColor(context.getResources().getColor(R.color.background));
		
		innerAnimBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.inner_circle_anim);
	}
 
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		super.onDraw(canvas);
		
		canvas.drawColor(Color.TRANSPARENT);
		
		int width = this.getWidth();
		int height = this.getHeight();
 
		if(width!=height)
		{
			int min=Math.min(width, height);
			width=min;
			height=min;
		}
 
		paint.setAntiAlias(true); // 设置画笔为抗锯齿
		paint.setColor(Color.rgb(0xd8,0xd8,0xd8 )); // 设置画笔颜色
		//canvas.drawColor(Color.TRANSPARENT); // 白色背景
		paint.setStrokeWidth(progressStrokeWidth); //线宽
		paint.setStyle(Style.STROKE);
 
		oval.left = progressStrokeWidth; // 左上角x
		oval.top = progressStrokeWidth; // 左上角y
		oval.right = width - progressStrokeWidth; // 左下角x
		oval.bottom = height - progressStrokeWidth; // 右下角y
		float radius = (width - progressStrokeWidth) / 2 - progressStrokeWidth;
 
		canvas.drawArc(oval, 135, 270, false, paint); // 绘制白色圆圈，即进度条背景
		paint.setColor(Color.WHITE);
		canvas.drawArc(oval, 135, ((float) progress / maxProgress) * 270, false, paint); // 绘制进度圆弧，这里是蓝色
 
		float centerX = oval.centerX();
		float centerY = oval.centerY();
		
		// Outer oval
		RectF outerOval = new RectF();
		outerOval.left = oval.left - progressStrokeWidth + 2;
		outerOval.top = oval.top - progressStrokeWidth + 2;
		outerOval.right = oval.right + progressStrokeWidth - 2;
		outerOval.bottom = oval.bottom + progressStrokeWidth - 2;
		paint.setAntiAlias(true);
		paint.setColor(Color.rgb(0xd8,0xd8,0xd8 ));
		paint.setStrokeWidth(4);
		canvas.drawArc(outerOval, 135, 270, false, paint);
		
		// Inner oval
		RectF innerOval = new RectF();
		innerOval.left = centerX - INNER_WIDTH;
		innerOval.top = centerY - INNER_WIDTH;
		innerOval.right = centerX + INNER_WIDTH;
		innerOval.bottom = centerY + INNER_WIDTH;
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(4);
		canvas.drawArc(innerOval, 135, 360, false, paint);
		
		
		float stopX = centerX, stopY = centerY;
		double ractor = Math.PI / 180;
		double degree = (float)(((float)progress * 270 / (float)maxProgress) );
		Log.d(TAG, "progress = " + progress + ", degree = " + degree);
		if (degree < 45.0) {
			double dd = 45.0 - degree;
			
			double deltaX = Math.cos(dd * ractor);
			double deltaY = Math.sin(dd * ractor);
			
			stopX = centerX - (float) (radius * deltaX);
			stopY = centerY + (float) (radius * deltaY);
			
			Log.d(TAG, "dd=" + dd);
			Log.d(TAG, "deltaX=" + deltaX);
			Log.d(TAG, "deltaY=" + deltaY);
			Log.d(TAG, "test1=" + Math.sin(0 * ractor));
			Log.d(TAG, "test2=" + Math.sin(90 * ractor));
		} else if (degree < 135.0) {
			double dd = degree - 45.0;
			
			double deltaX = Math.cos(dd * ractor);
			double deltaY = Math.sin(dd * ractor);
			
			stopX = centerX - (float) (radius * deltaX);
			stopY = centerY - (float) (radius * deltaY);
		} else if (degree < 225.0) {
			double dd = 225.0 - degree;
			
			double deltaX = Math.cos(dd * ractor);
			double deltaY = Math.sin(dd * ractor);
			
			stopX = centerX + (float) (radius * deltaX);
			stopY = centerY - (float) (radius * deltaY);
		} else if (degree <= 270.0) {
			double dd = degree - 225.0;
			
			double deltaX = Math.cos(dd * ractor);
			double deltaY = Math.sin(dd * ractor);
			
			stopX = centerX + (float) (radius * deltaX);
			stopY = centerY + (float) (radius * deltaY);
		}
		
		Log.d(TAG, "width = " + width + ", radius = " + radius);
		Log.d(TAG, "centerX = " + centerX + ", centerY = " + centerY);
		Log.d(TAG, "stopX = " + stopX + ", stopY = " + stopY);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(2);
		canvas.drawLine(centerY, centerY, stopX, stopY, paint);
		
//		paint.setStrokeWidth(1);
//		String text = progress + "%";
//		int textHeight = height / 4;
//		paint.setTextSize(textHeight);
//		int textWidth = (int) paint.measureText(text, 0, text.length());
//		paint.setStyle(Style.FILL);
//		canvas.drawText(text, width / 2 - textWidth / 2, height / 2 +textHeight/2, paint);
 
	}
 
 
 
	public int getMaxProgress() {
		return maxProgress;
	}
 
	public void setMaxProgress(int maxProgress) {
		this.maxProgress = maxProgress;
	}
 
	public void setProgress(int progress) {
		this.progress = progress;
		this.invalidate();
	}
 
	/**
	 * 非ＵＩ线程调用
	 */
	public void setProgressNotInUiThread(int progress) {
		this.progress = progress;
		this.postInvalidate();
	}
}
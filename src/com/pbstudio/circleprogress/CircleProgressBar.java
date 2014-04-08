package com.pbstudio.circleprogress;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CircleProgressBar extends RelativeLayout{
	private static final String TAG = "CircleProgressBar";
	
	private CircleProgressBarInner mProgressBar;
	private ImageView mAnimView;
	private Animation mRotateAnimation;
	
	public CircleProgressBar(Context context) {
		super(context);
		
		init(context);
	}

	public CircleProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		init(context);
	}

	public CircleProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		init(context);
	}

	private void init(Context context) {
		mProgressBar = new CircleProgressBarInner(context);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.addView(mProgressBar, lp);
		
		mAnimView = new ImageView(context);
		mAnimView.setVisibility(View.GONE);
		mAnimView.setImageResource(R.drawable.inner_circle_anim);
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp.width = 80;
		lp.height = 80;
		lp.addRule(CENTER_IN_PARENT);
		addView(mAnimView, lp);
		
		mRotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate);  
		LinearInterpolator lin = new LinearInterpolator();  
		mRotateAnimation.setInterpolator(lin);
	}
	
	public void setProgress(int progress) {
		mProgressBar.setProgress(progress);
	}
	
	public void setMaxProgress(int maxProgress) {
		mProgressBar.setMaxProgress(maxProgress);
	}
	
	public void setProgressNotInUiThread(int progress) {
		mProgressBar.setProgressNotInUiThread(progress);
	}
	
	public void startAnimation() {
		mAnimView.setVisibility(View.VISIBLE);
		mAnimView.startAnimation(mRotateAnimation);
	}
	
	public void stopAnimation() {
		mAnimView.setVisibility(View.GONE);
		mAnimView.clearAnimation();
	}
}

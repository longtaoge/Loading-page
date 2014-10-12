package com.pgeeKer.loading_page.view;

import com.pgeeKer.loading_page.view.CustomTextView.AnimationSetupCallback;

import android.animation.Animator.AnimatorListener;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.view.animation.LinearInterpolator;

public class CustomAnimation {
	private AnimatorSet animatorSet;
	private AnimatorListener animatorListener;
	
	public AnimatorListener getAnimatorListener() {
		return animatorListener;
	}
	public void setAnimatorListener(AnimatorListener animatorListener) {
		this.animatorListener = animatorListener;
	}
	
	public void start(final CustomTextView textView){
		@SuppressWarnings("unused")
		final Runnable animation = new Runnable() {
			
			@SuppressLint("NewApi")
			@Override
			public void run() {
				textView.setSink(true);
				//水平方向动画 200 = wave.png width
				ObjectAnimator axleXaAnimator = ObjectAnimator.ofFloat(textView, 
						"axleX", 0, 200);
				axleXaAnimator.setRepeatCount(ValueAnimator.INFINITE);  //无限次重复
				axleXaAnimator.setDuration(1000);  //动画持续时间
				axleXaAnimator.setStartDelay(0);  //延迟开始的时间		
				
				int hight = textView.getHeight();
				
				ObjectAnimator axleYaniAnimator = ObjectAnimator.ofFloat(textView, 
						"axleY", hight/2, -hight/2);
				axleYaniAnimator.setRepeatCount(ValueAnimator.INFINITE);
				axleYaniAnimator.setRepeatMode(ValueAnimator.REVERSE);
				axleYaniAnimator.setDuration(10000);
				axleYaniAnimator.setStartDelay(0);
				
				//让X,Y动画同时执行
				animatorSet = new AnimatorSet();
				animatorSet.playTogether(axleXaAnimator, axleYaniAnimator);
				animatorSet.setInterpolator(new LinearInterpolator());  //设置插补器为LinearInterpolator，则会匀速变化
				animatorSet.addListener(new AnimatorListener() {
					
					@Override
					public void onAnimationStart(Animator animator) {
						
					}
					
					@Override
					public void onAnimationRepeat(Animator animator) {
						
					}
					
					@Override
					public void onAnimationEnd(Animator animator) {
						textView.setSink(false);
						//SDK < 4.1
						if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
							textView.postInvalidate();
						}else {
							textView.postInvalidateOnAnimation();
						}
						animatorSet = null;
					}
					
					@Override
					public void onAnimationCancel(Animator animator) {
						
					}
				});
				if (animatorListener != null) {
					animatorSet.addListener(animatorListener);
				}
				animatorSet.start();
			}
		};
		if (!textView.isSetup()) {
			textView.setAnimationSetupCallback(new AnimationSetupCallback() {
				
				@Override
				public void onSetupAnimation(CustomTextView customTextView) {
					animation.run();
				}
			});
		}else {
			animation.run();
		}
	}
}

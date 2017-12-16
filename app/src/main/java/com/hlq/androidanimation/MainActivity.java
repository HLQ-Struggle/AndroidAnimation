package com.hlq.androidanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import static android.animation.ValueAnimator.INFINITE;

/**
 * author : HLQ
 * e-mail : 925954424@qq.com
 * time   : 2017/12/16
 * desc   : 属性动画
 * version: 1.0
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 补间动画
    public void getTranslate(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        view.startAnimation(animation);
    }

    private int position = 0;

    // 简单属性动画
    public void getTranslateX(View view) {
        position += 100;
        view.setTranslationX(position);
    }

    // ObjectAnimator ofFloat
    public void getAnimation(View view) {
        // 设置X轴平移
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 300f);
        // 设置动画时间
        animator.setDuration(500);
        animator.start();
    }

    // ObjectAnimator ofInt
    public void getObjectAnimatorInt(View view) {
        ObjectAnimator animator = ObjectAnimator.ofInt(view, "backgroundColor", Color.RED, Color.YELLOW, Color.BLUE);
        animator.setDuration(1000);
        animator.start();
    }

    // ObjectAnimator ofArgb
    public void getObjectAnimatorArgb(View view) {
        ObjectAnimator animator = ObjectAnimator.ofArgb(view, "backgroundColor", Color.RED, Color.YELLOW, Color.BLUE);
        animator.setDuration(1000);
        animator.setRepeatCount(INFINITE);
        animator.setRepeatMode(INFINITE);
        animator.start();
    }

    /**
     * 组合动画 实现方式一
     *
     * @param view
     */
    public void getMoreAnimation1(final View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "HLQ_Blog", 300f);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 动画执行中 会不断调用此方法
                float value = (float) animation.getAnimatedValue();
                view.setAlpha(value / 300);
                view.setScaleX(value / 300);
                view.setScaleY(value / 300);
            }
        });
        animator.start();
    }

    public void getMoreAnimation2(final View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 300f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 动画执行中 会不断调用此方法
                float value = (float) animation.getAnimatedValue();
                view.setAlpha(value / 300);
                view.setScaleX(value / 300);
                view.setScaleY(value / 300);
            }
        });
        animator.start();
    }

    public void getMoreAnimation3(View view) {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.7f, 1f);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.7f, 1f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, holder1, holder2, holder3);
        animator.setDuration(500);
        animator.start();
    }

    public void getMoreAnimation4(View view) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.7f, 1f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.7f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.7f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.play(animator1).after(animator2);
        // 同步执行
//        animatorSet.playTogether(animator1, animator2, animator3);
        // 依次执行
//        animatorSet.playSequentially(animator1, animator2, animator3);
        animatorSet.start();

    }

    public void getMoreAnimation5(final View view) {
        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(500);
        // 设置默认
        animator.setObjectValues(new PointF(0, 0));
        // 开启 估值器 设定规则
        animator.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                PointF pointF = new PointF();
                pointF.x = 100f * (fraction * 4);
                pointF.y = 0.5f * 100f * (fraction * 4) * (fraction * 4);
                return pointF;
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                view.setX(pointF.x);
                view.setY(pointF.y);
            }
        });
        animator.start();
    }

    /**
     * 插值器
     *
     * @param view
     */
    public void getInterpolator(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 0f, 500f);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateInterpolator(5));
        animator.start();
    }

}

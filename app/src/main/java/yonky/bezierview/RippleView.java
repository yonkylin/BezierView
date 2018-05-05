package yonky.bezierview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;


/**
 * Created by Administrator on 2018/5/4.
 */

public class RippleView extends View {
    private int mStep;
    private int waveCount;
    private int waveHeight,waveLength;

    private int height,width;
    private Paint mPaint;


    public RippleView(Context context) {
        super(context);
        initView();
    }

    public RippleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        waveCount = 4;
        waveHeight =40;


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        height = h;
        waveLength=w/2;
        run();
    }

    public void run(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,2*waveLength);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStep = (int)animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Path path1 = new Path();
//        path1.addRect(0,0,width,);
        Path path2 = new Path();
        path2.moveTo(-mStep,height/2);
        for(int i=1;i<=waveCount;i++){
            if(i%2 !=0){
                path2.quadTo(waveLength*i-(waveLength/2)-mStep,height/2-waveHeight,waveLength*i-mStep,height/2);
            }else{
                path2.quadTo(waveLength*i-(waveLength/2)-mStep,height/2+waveHeight,waveLength*i-mStep,height/2);
            }

        }
        path2.lineTo(waveLength*waveCount,height);
        path2.lineTo(0,height);
        path2.close();
        canvas.drawPath(path2,mPaint);

    }
}

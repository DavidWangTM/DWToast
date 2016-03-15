package tm.davidwang.toastactivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;

import tm.davidwang.dwtoast.R;

/**
 * Created by DavidWang on 16/3/14.
 */
public class ToastActivity extends Activity {

    private Spring Spring;

    private RelativeLayout showLayout;
    private TextView showText;
    private LinearLayout addViewLayout;
    private int viewHeight;
    // 屏幕宽度
    public float Width;
    // 屏幕高度
    public float Height;

    private LinearLayout.LayoutParams p;

    private int form_num;
    private int to_num;
    private float time;
    private int coverType;
    private int qcTension,qcFriction;

    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toast_main);
        getIntentValue();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Width = dm.widthPixels;
        Height = dm.heightPixels;
        findID();
        addView();
        Spring = SpringSystem.create().createSpring().addListener(new formSpringListener());
        Spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(qcTension, qcFriction));
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Spring.setEndValue(1);
            }
        }, 500);
    }

    private void getIntentValue() {
        form_num = getIntent().getIntExtra("form_num", 0);
        to_num = getIntent().getIntExtra("to_num", 0);
        time = getIntent().getFloatExtra("time",0.5f);
        coverType = getIntent().getIntExtra("coverType",0);
        qcTension = getIntent().getIntExtra("qcTension",100);
        qcFriction = getIntent().getIntExtra("qcFriction",6);
        description = getIntent().getStringExtra("description");
    }

    private void findID() {
        addViewLayout = (LinearLayout) findViewById(R.id.addViewLayout);
    }

    private void addView() {
        showLayout = (RelativeLayout) View.inflate(ToastActivity.this,
                R.layout.text_toast_view, null);
        showText = (TextView) showLayout.findViewById(R.id.showText);
        showText.setText(description);
        addViewLayout.addView(showLayout);

        switch (coverType){
            case Constants.status:
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
                viewHeight = dip2px(25);
                break;
            case Constants.nar:
                viewHeight = dip2px(56);
                break;
            case Constants.statusAndNar:
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
                viewHeight = dip2px(81);
                break;
        }
        p = new LinearLayout.LayoutParams((int) Width, viewHeight);
        showLayout.setLayoutParams(p);
        LinearLayout.LayoutParams pl = new LinearLayout.LayoutParams((int) Width, viewHeight);
        addViewLayout.setLayoutParams(pl);
        switch (form_num) {
            case Constants.fromTop:
                p.setMargins(0, -viewHeight, 0, 0);
                break;
            case Constants.fromBottom:
                p.setMargins(0, viewHeight, 0, 0);
                break;
            case Constants.fromLeft:
                p.setMargins(-(int) Width, 0, 0, 0);
                break;
            case Constants.fromRight:
                p.setMargins((int) Width, 0, 0, 0);
                break;
        }
    }


    private class formSpringListener implements SpringListener {

        @Override
        public void onSpringUpdate(Spring spring) {
            double CurrentValue = spring.getCurrentValue();
            if (Spring.getEndValue() == 0) {
                float mappedValue = 0;
                switch (form_num) {
                    case Constants.fromTop:
                        switch (to_num) {
                            case Constants.toTop:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, viewHeight);
                                showLayout.setTranslationY(mappedValue - 1);
                                break;
                            case Constants.toBottom:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange(2 - CurrentValue, 0, 1, 1, viewHeight );
                                showLayout.setTranslationY(mappedValue + 1);
                                break;
                            case Constants.toLeft:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange( 2 - CurrentValue, 0, 1, 1, -Width);
                                mappedValue +=  Width;
                                showLayout.setTranslationX(mappedValue - 1);
                                break;
                            case Constants.toRight:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange(2 - CurrentValue, 0, 1, 1, Width);
                                mappedValue -= Width;
                                showLayout.setTranslationX(mappedValue + 1);
                                break;
                        }
                        break;
                    case Constants.fromBottom:
                        switch (to_num) {
                            case Constants.toTop:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange(2 - CurrentValue, 0, 1, 1, -viewHeight);
                                showLayout.setTranslationY(mappedValue - 1);
                                break;
                            case Constants.toBottom:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, -viewHeight);
                                showLayout.setTranslationY(mappedValue + 1);
                                break;
                            case Constants.toLeft:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange( 2 - CurrentValue, 0, 1, 1, -Width);
                                mappedValue +=  Width;
                                showLayout.setTranslationX(mappedValue - 1);
                                break;
                            case Constants.toRight:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange(2 - CurrentValue, 0, 1, 1, Width);
                                mappedValue -= Width;
                                showLayout.setTranslationX(mappedValue + 1);
                                break;
                        }
                        break;
                    case Constants.fromLeft:
                        switch (to_num) {
                            case Constants.toTop:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange(2 - CurrentValue, 0, 1, 1, -viewHeight);
                                mappedValue += viewHeight;
                                showLayout.setTranslationY(mappedValue - 1);
                                break;
                            case Constants.toBottom:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange(2 - CurrentValue, 0, 1, 1, viewHeight);
                                mappedValue -= viewHeight;
                                showLayout.setTranslationY(mappedValue + 1);
                                break;
                            case Constants.toLeft:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, Width);
                                showLayout.setTranslationX(mappedValue - 1);
                                break;
                            case Constants.toRight:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange(2 - CurrentValue, 0, 1, 1, Width);
                                showLayout.setTranslationX(mappedValue + 1);
                                break;
                        }
                        break;
                    case Constants.fromRight:
                        switch (to_num) {
                            case Constants.toTop:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange(2 - CurrentValue, 0, 1, 1, -viewHeight);
                                mappedValue += viewHeight;
                                showLayout.setTranslationY(mappedValue - 1);
                                break;
                            case Constants.toBottom:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange(2 - CurrentValue, 0, 1, 1, viewHeight);
                                mappedValue -= viewHeight;
                                showLayout.setTranslationY(mappedValue + 1);
                                break;
                            case Constants.toLeft:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange(2 - CurrentValue, 0, 1, 1, -Width);
                                showLayout.setTranslationX(mappedValue - 1);
                                break;
                            case Constants.toRight:
                                mappedValue = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, -Width);
                                showLayout.setTranslationX(mappedValue + 1);
                                break;
                        }
                        break;
                }
            } else {
                float mappedValue = 0;
                switch (form_num) {
                    case Constants.fromTop:
                        mappedValue = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, viewHeight);
                        Log.e("1", mappedValue + "啦啦");
                        showLayout.setTranslationY(mappedValue);
                        break;
                    case Constants.fromBottom:
                        mappedValue = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, -viewHeight);
                        showLayout.setTranslationY(mappedValue);
                        break;
                    case Constants.fromLeft:
                        mappedValue = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, Width);
                        showLayout.setTranslationX(mappedValue);
                        break;
                    case Constants.fromRight:
                        mappedValue = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, -Width);
                        showLayout.setTranslationX(mappedValue);
                        break;
                }
            }
        }

        @Override
        public void onSpringAtRest(Spring spring) {
            if (Spring.getEndValue() == 1) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(1, 6));
                        Spring.setEndValue(0);
                    }
                }, (int)(time * 1000));
            } else {
                finish();
            }
        }

        @Override
        public void onSpringActivate(Spring spring) {
        }

        @Override
        public void onSpringEndStateChange(Spring spring) {
        }
    }


    protected int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



}

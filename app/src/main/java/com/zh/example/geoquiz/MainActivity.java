package com.zh.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
   // private ImageButton mPrevButton;
    private TextView mTextView;
    private  Question[] mQuestionBank = new Question[]{
        new Question(R.string.question_china, true),
        new Question(R.string.question_oceans, true),
        new Question(R.string.question_africa, false),
        new Question(R.string.question_americas, true),
        new Question(R.string.question_asia, true),
        new Question(R.string.question_mideast, false)
    };
    private  int mCurrentIndex = 0;
    private  float mCorrects = 0;
    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putInt(KEY_INDEX, mCurrentIndex);
//        Log.d(TAG, "saveInstanceState");
    }
    //Activity方法：onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d(TAG, "create called");
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null) {
          mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        /*显示问题内容*/
        mTextView =(TextView) findViewById(R.id.question_text_view);
        updateTextView();
        //textview点击事件监听器
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateTextView();
            }
        });
        //用户答案true
        mTrueButton =(Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /* 小练习 Toast在界面顶部显示
               Toast toast = Toast.makeText(MainActivity.this,R.string.correct_toast, Toast.LENGTH_SHORT);
               toast.setGravity(Gravity.TOP, 20, 20);
               toast.show();*/
              checkAnswer(true);
                mFalseButton.setEnabled(false);
            }
        });
        //用户答案false
        mFalseButton =(Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* 小练习 Toast在界面顶部显示
                Toast toast = Toast.makeText(MainActivity.this,R.string.incorrect_toast, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 20, 20);
                toast.show();*/
                checkAnswer(false);
                mTrueButton.setEnabled(false);
            }
        });
        //下一题
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                /*小练习 计算答题正确率*/
                if(mCurrentIndex == 0) {
                    float res = (mCorrects/mQuestionBank.length)* 100;
                    DecimalFormat decimalFormat = new DecimalFormat(".00");
                    String result = "Your scores:" + decimalFormat.format(res)  + "%";
                    Toast.makeText(MainActivity.this,
                            result, Toast.LENGTH_SHORT)
                            .show();
                    mCorrects = 0;
                    mCurrentIndex = 0;
                }
                updateTextView();
                mTrueButton.setEnabled(true);
                mFalseButton.setEnabled(true);


            }
        });
        /*上一题
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrentIndex - 1 < 0) {
                    Toast.makeText(MainActivity.this,R.string.first_toast,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                updateTextView();
            }
        });*/
    }
    //问题切换
    private void updateTextView() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mTextView.setText(question);
    }
    //问题答案判断
    private  void checkAnswer(boolean userPressedTrue) {
        boolean answer = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int toastMsg = 0;
        if(answer == userPressedTrue){
            toastMsg = R.string.correct_toast;
            mCorrects += 1;
        } else {
           toastMsg = R.string.incorrect_toast;
        }
        Toast.makeText(MainActivity.this,toastMsg,
                Toast.LENGTH_SHORT).show();

    }
    //Activity的onStart
    @Override
    public void onStart() {
        super.onStart();
//        Log.d(TAG,"start called");
    }
    //Activity的onResume
    @Override
    public void onResume() {
        super.onResume();
//        Log.d(TAG,"resume called");
    }
    //Activity的onPause
    @Override
    public void onPause() {
        super.onPause();
//        Log.d(TAG,"pause called");
    }
    //Activity的onStop
    @Override
    public void onStop() {
        super.onStop();
//        Log.d(TAG,"stop called");
    }
    //Activity的onDestroy
    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.d(TAG,"destroy called");
    }
}

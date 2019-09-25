package com.zh.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            }
        });
        //下一题
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateTextView();
            }
        });
        //上一题
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
        });
    }
    //问题切换
    private void updateTextView() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mTextView.setText(question);
    }
    //问题答案判断
    private  void checkAnswer(boolean userPressedTrue) {
        boolean answer = mQuestionBank[mCurrentIndex].isAnswerTrue();
        Toast.makeText(MainActivity.this,
                answer == userPressedTrue ? R.string.correct_toast : R.string.incorrect_toast,
                Toast.LENGTH_SHORT).show();

    }
}

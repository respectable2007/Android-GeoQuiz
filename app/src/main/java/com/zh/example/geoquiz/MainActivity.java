package com.zh.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
/*launcher activity*/
public class MainActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
   // private ImageButton mPrevButton;
    private Button mCheatButton;
    private TextView mTextView;
    private TextView mCheatsView;
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
    private  boolean mIsCheated = false;
    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEAT = "ischeated";
    private static final String KEY_CHEATS = "cheats";
    private  static int REQUEST_CODE_CHEAT = 0;
    //保存每个问题答案是否被查看
    private boolean[] mCheatBank = new boolean[]{false,false,false,false,false,false};
    private String mCheatsText;
    //记录偷看次数
    private int mCheats = 3;
    //stop前，即当前类被销毁前，暂存当前类所要保留的数据
    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        saveInstanceState.putBooleanArray(KEY_CHEAT, mCheatBank);
        saveInstanceState.putInt(KEY_CHEATS, mCheats);
//        Log.d(TAG, "saveInstanceState");
    }
    //Activity方法：onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d(TAG, "create called");
        setContentView(R.layout.activity_main);
        //获取暂存的数据
        if(savedInstanceState != null) {
          mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
          mIsCheated = savedInstanceState.getBooleanArray(KEY_CHEAT)[mCurrentIndex];
          mCheats = savedInstanceState.getInt(KEY_CHEATS);
          showCheats();
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
//        mTrueButton =(Button) findViewById(R.id.question_text_view);
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
//                mIsCheated = false;
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
        //查看答案
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取当前问题的答案
                boolean isAnswerTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                //构造要被启动activity
                Intent intent = CheatActivity.newIntent(MainActivity.this, isAnswerTrue);
                //启动子activity，并需要返回值
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
        //显示剩余偷看次数
        mCheatsView = (TextView) findViewById(R.id.cheats_view);
        showCheats();
    }
    //重写onActivityResult，获取子activity返回的结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK) {
            return;
        }
        if(requestCode == REQUEST_CODE_CHEAT) {
          if(data == null) {
              return;
          }
          mIsCheated = CheatActivity.wasAnswerShown(data);
          mCheatBank[mCurrentIndex] = mIsCheated;
          mCheats --;
          showCheats();
        }
    }
    //显示剩余偷看次数
    private void  showCheats(){
        mCheatsText = "Can cheat: " + Integer.toString(mCheats) + " times";
        mCheatsView.setText(mCheatsText);
        if(mCheats == 0) {
            mCheatButton.setEnabled(false);
        }
    }
    //问题切换
    private void updateTextView() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
//        Log.d(TAG, "update text:", new Exception());
        mTextView.setText(question);
    }
    //问题答案判断
    private  void checkAnswer(boolean userPressedTrue) {
        boolean answer = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int toastMsg = 0;
        if(mIsCheated) {
            toastMsg = R.string.judgment_toast;
        } else {
            if(answer == userPressedTrue){
                toastMsg = R.string.correct_toast;
                mCorrects += 1;
            } else {
                toastMsg = R.string.incorrect_toast;
            }
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

package com.zh.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_IS_ANSWER_TRUE = "com.zh.example.geoquiz.answer_is_true";
    private static final String SHOW_ANSWER_TRUE = "com.zh.example.geoquiz.show_answer_true";
    private boolean mAnswerTrue;
    private Button mCheatButton;
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        //通过getIntent方法，读取父activity传入的数据
        mAnswerTrue = getIntent().getBooleanExtra(EXTRA_IS_ANSWER_TRUE,false);
        mTextView = (TextView) findViewById(R.id.answer_text_view);
        mCheatButton = (Button) findViewById(R.id.show_answer_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText(mAnswerTrue?R.string.true_button:R.string.false_button);
                Intent intent = new Intent();
                intent.putExtra(SHOW_ANSWER_TRUE, true);
                setResult(RESULT_OK, intent);
            }
        });
    }
    //解析子activity返回的intent数据
    public static boolean wasAnswerShown(Intent intent){
        return  intent.getBooleanExtra(SHOW_ANSWER_TRUE, false);
    }
    //封装Intent构造方法
    public static Intent newIntent(Context packageContext, boolean isAnswerTrue){
      Intent intent = new Intent(packageContext, CheatActivity.class);
      intent.putExtra(EXTRA_IS_ANSWER_TRUE, isAnswerTrue);
      return intent;
    }
}

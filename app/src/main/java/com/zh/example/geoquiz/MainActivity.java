package com.zh.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTrueButton =(Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /* 小练习 Toast在界面顶部显示
               Toast toast = Toast.makeText(MainActivity.this,R.string.correct_toast, Toast.LENGTH_SHORT);
               toast.setGravity(Gravity.TOP, 20, 20);
               toast.show();*/
              Toast.makeText(MainActivity.this,R.string.correct_toast, Toast.LENGTH_SHORT)
                   .show();
            }
        });
        mFalseButton =(Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* 小练习 Toast在界面顶部显示
                Toast toast = Toast.makeText(MainActivity.this,R.string.incorrect_toast, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 20, 20);
                toast.show();*/
                Toast.makeText(MainActivity.this,R.string.incorrect_toast, Toast.LENGTH_SHORT)
                     .show();
            }
        });
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }
}

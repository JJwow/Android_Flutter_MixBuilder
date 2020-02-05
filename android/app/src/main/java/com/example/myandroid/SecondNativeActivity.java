package com.example.myandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondNativeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_native);
        Intent intent = getIntent();
        String content = intent.getStringExtra("message");
        TextView textView = findViewById(R.id.textView2);
        textView.setText(content);
        Button btnOpen = findViewById(R.id.button001);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("message","嗨，本文案来自第二个原生页面，将在Flutter页面看到我");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}

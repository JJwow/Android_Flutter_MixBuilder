package com.example.myandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstNativeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_native);
        Button btnOpen = findViewById(R.id.button001);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstNativeActivity.this,FirstFlutterActivity.class);
                intent.putExtra("message","嗨，本文案来此第一个原生页面,将在Flutter页面看到我");
                startActivityForResult(intent, Activity.RESULT_FIRST_USER);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            TextView textView = findViewById(R.id.textView001);
            textView.setText(data.getStringExtra("message"));
        }
    }


}

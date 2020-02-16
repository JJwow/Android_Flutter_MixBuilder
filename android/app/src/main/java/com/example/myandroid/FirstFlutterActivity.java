package com.example.myandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.flutter.embedding.android.FlutterView;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

public class FirstFlutterActivity extends AppCompatActivity {
    private static final String CHANNEL_NATIVE = "com.example.flutter/native";
    private static final String CHANNEL_FLUTTER = "com.example.flutter/flutter";
    FlutterEngine flutterEngine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_flutter);
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        FlutterView flutterView = new FlutterView(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        FrameLayout flContainer = findViewById(R.id.layout001);
        flContainer.addView(flutterView, lp);
        flutterEngine = new FlutterEngine(this);
        String str = "route1?{\"message\":\"" + message + "\"}";
        flutterEngine.getNavigationChannel().setInitialRoute(str);
        flutterEngine.getDartExecutor().executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        );
        // 关键代码，将Flutter页面显示到FlutterView中
        flutterView.attachToFlutterEngine(flutterEngine);

        //flutter调用Android，channel定义
        MethodChannel nativeChannel = new MethodChannel(flutterEngine.getDartExecutor(), CHANNEL_NATIVE);
        nativeChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(MethodCall call, MethodChannel.Result result) {
                switch (call.method){
                    case "openSecondNative":
                        // 跳转原生页面
                        Intent jumpToNativeIntent = new Intent(FirstFlutterActivity.this, SecondNativeActivity.class);
                        jumpToNativeIntent.putExtra("message", (String) call.argument("message"));
//                        startActivity(jumpToNativeIntent);
                        startActivityForResult(jumpToNativeIntent, Activity.RESULT_FIRST_USER);
                        result.success("成功打开第二个原生页面");
                        break;
                    case "backFirstNative":
                        Intent backIntent = new Intent();
                        backIntent.putExtra("message", (String) call.argument("message"));
                        setResult(Activity.RESULT_OK, backIntent);
                        finish();
                        result.success("成功返回第一个原生页面");
                        break;
                    case "backAction":
                        finish();
                        result.success("成功通过虚拟按键返回第一个原生页面");
                        break;
                    default :
                        result.notImplemented();
                        break;

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data != null) {
                    // NativePageActivity返回的数据
                    String message = data.getStringExtra("message");
                    Map<String, Object> result = new HashMap<>();
                    result.put("message", message);
                    // 创建MethodChannel，这里的flutterView即Flutter.createView所返回的View
                    MethodChannel flutterChannel = new MethodChannel(flutterEngine.getDartExecutor(), CHANNEL_FLUTTER);
                    // 调用Flutter端定义的方法
                    flutterChannel.invokeMethod("onActivityResult", result);
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        MethodChannel flutterChannel = new MethodChannel(flutterEngine.getDartExecutor(), CHANNEL_FLUTTER);
        flutterChannel.invokeMethod("backAction", null);
    }
}

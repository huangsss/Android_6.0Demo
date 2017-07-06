package com.study.callphonetest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private final int CAMERA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void onClick(View view){
        textView.setText("点击了");
        requestPermission();
    }

    @TargetApi(Build.VERSION_CODES.M) //使用TargetApi  使高版本API的代码在低版本SDK不报错
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            // 如果没有这个权限会进入这个方法；
            // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
            // 向用户解释为什么需要这个权限
            Log.d("print", "requestPermission:没有这个权限，看看这个Boolean的属性："+ ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA) );
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
                new AlertDialog.Builder(this)
                        .setMessage("申请相机权限")
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //申请相机权限
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
                    }
                })
                        .show();
            }else{
                //申请相机权限
                Log.d("print", "requestPermission: 进入了else 看看这个属性：" +ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA));
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
            }
        }else {
            textView.setTextColor(Color.BLUE);
            textView.setText("相机权限已申请");
        }
    }

    /**
     * 申请权限的结果的回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                textView.setTextColor(Color.GREEN);
                textView.setText("相机权限申请成功");
            }else {
                //用户勾选了不再询问  如果用户选择了不再提示，shouldShowRequestPermissionRationale为false
                //这种情况下就可以提示用户手动打开权限,跳转到应用设置手动打开权限；
                Log.d("print", "onRequestPermissionsResult: 11111111111111");
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
                    Toast.makeText(this, "相机权限已被禁止", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}

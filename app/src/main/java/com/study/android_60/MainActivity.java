package com.study.android_60;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("print", "onCreate: -1为拒绝DENIED ==" + ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE));
        //判断手机版本
        if (Build.VERSION.SDK_INT >= 23) {
            Log.d("Print", "你的SDK版本 SDK>23");
//            if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
//                Log.d("print", "拨打电话没有这个权限因此不能调用,要去请求权限");
////                requestPermission();
//            }else {
//                Log.d("print", "有权限所以进行拨打电话的操作，返回的数值==" + ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE));
//            }
        }

    }

    public void callPhone(View view) {
        Log.d("print", "callPhone:方法");
       /* //判断是否有权限，没有权限就去申请权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
        } else {
            Log.d("print", "callPhone: CALL-PHONE有授权");
            CALLphone();*/
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);

    }

    /**
     * 调起拨打电话界面 无需任何权限；
     */
    public void DIALphone() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "10086"));
        startActivity(intent);
    }

    /**
     * 直接拨打电话 需要拨打电话的权限
     */
    public void CALLphone() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri = Uri.parse("tel" + "18702515501");
        intent.setData(uri);
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            Log.d("print", "拨打电话没有这个权限因此不能调用");
            return;
        }else {
            Log.d("print", "有权限所以进行拨打电话的操作，返回的数值==" + ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE));
        }
        startActivity(intent);
    }

    /**
     * 请求权限的方法
     */
    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},MY_PERMISSIONS_REQUEST_CALL_PHONE);
        Log.d("print", "callPhone: 没有CALL-PHONE授权所以正在请求");
    }

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("print", "onRequestPermissionsResult: 回调，说明有了这个权限");
            } else {
                //permission Denied:
                Toast.makeText(this, "permission is Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}

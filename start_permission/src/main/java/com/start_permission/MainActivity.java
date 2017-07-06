package com.start_permission;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23){
            Log.d("print", "onCreate: SDK版本号大于23");
            isHavePromission();
        }
    }
    private  void isHavePromission(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("print", "isHavePromission: 没有这个权限要去申请");
            requestPromission();
        }else {
            Log.d("print", "isHavePromission: 有权限不用申请");
        }
    }
    //请求权限
    private void requestPromission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
        Log.d("print", "callPhone: 没有授权所以正在请求");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("print", "onRequestPermissionsResult: 回调 已经有这个权限啦");
                Toast.makeText(this, "你有这个权限可以为所欲为了", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "怕是没有权限哦", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    /**
     * 直接拨打电话 需要拨打电话的权限
     */
    public void CALLphone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + 10086);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("print", "CALLphone: 没有权限因此直接退出");
            Toast.makeText(this, "你没有打电话的权限", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(intent);
    }

    public void btn_onclick(View view){
        CALLphone();
    }
}

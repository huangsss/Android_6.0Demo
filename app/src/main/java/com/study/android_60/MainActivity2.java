package com.study.android_60;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by "huangsays"  on 2017/6/22.20:06"huangays@gmail.com"
 */

public class MainActivity2 extends AppCompatActivity{

    private Button btn_call;
    private int Sms_Permission = 1;
    private String phone = "10086";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actviity_main_2);
        init();

        if (Build.VERSION.SDK_INT >= 23) {
            Log.d("Print", "onCreate: SDK>23");
            requestPermission();
        }
    }
    public void requestPermission() {
        //如果没有这个权限，则去申请。
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //第一次被拒绝后，第二次访问时，向用户说明为什么需要此权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                Toast.makeText(this, "开启后使用拨号功能", Toast.LENGTH_SHORT).show();
            }
            //若权限没有开启，则请求权限
            Log.d("print", "requestPermission: 若权限没有开启，则请求权限");
            ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.CALL_PHONE}, Sms_Permission);
        } else {
            //权限已开启
            Log.d("print", "requestPermission: 权限已经得到");
            btn_call.setOnClickListener(btn_call_click);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Sms_Permission) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //请求权限成功,做相应的事情
                Log.d("print", "onRequestPermissionsResult: 权限开启成功");
                btn_call.setOnClickListener(btn_call_click);
            } else {
                //请求失败则提醒用户
                Toast.makeText(this, "请求权限失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void init() {
        btn_call = (Button) findViewById(R.id.btn_call);
    }


    public Button.OnClickListener btn_call_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phone);
            intent.setData(data);
            if (ActivityCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Log.d("print", "onClick: 这是拨打电话的页面");
                return;
            }
            startActivity(intent);
        }
    };
}

package com.studyseesms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button qrySMS;
    private TextView showSMS;
    private static final int REQUEST_SMS_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qrySMS = (Button) findViewById(R.id.button);
        showSMS = (TextView) findViewById(R.id.SMS_text);

        qrySMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否拥有读取短信的权限
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) ==
                            PackageManager.PERMISSION_GRANTED) {
                        Log.d("print", "onClick: 拥有权限直接读取短信");
                        getSMS();
                    } else {
                        //没有权限，去申请权限
                        Log.d("print", "onClick: 没有权限要去申请");
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission_group.SMS}, REQUEST_SMS_PERMISSION);
                    }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("print", "onRequestPermissionsResult: 通过申请 获得了权限");
                getSMS();
            }else {
                Log.d("print", "onRequestPermissionsResult: 拒绝权限");
                Toast.makeText(this, "权限已被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void getSMS() {
        String SMS_INBOX = "content://sms/inbox";
        Uri uri = Uri.parse(SMS_INBOX);
        String[] projection = new String[]{"address", "date"};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        showSMS.setText("总共有" + cursor.getCount() + "条短信");
    }
}

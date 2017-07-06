package com.start_permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by "huangsays"  on 2017/6/29.10:26"huangays@gmail.com"
 */

public class MoreActivity extends AppCompatActivity {
    //首先声明一个数组permissions,将需要的权限都放在里面。
    private String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
    private List<String> mPermissionList = new ArrayList<>();
    private AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("print", "onCreate: 加载多重的权限请求");

        judgePermission();
        RequestPermission();
    }

    /**
     * 判断权限申请
     */
    private void judgePermission() {
        mPermissionList.clear();
        /**
         * 判断哪些权限未授予
         */
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
                Log.d("print", "onCreate: 都未授权，因此需要加到请求的列表之中");
            }
        }
    }

    /**
     * 请求权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void RequestPermission() {
        /**
         * 判断permissionList是否为空，不为空的，调用ActivityCompat.requestPermissions()授予权限。
         * 如果permissionList为空，表示权限都授予了，执行对应的方法
         */
        if (mPermissionList.isEmpty()) {
            //表示都授权了。
            Log.d("print", "onCreate: 列表为空，没有要请求的权限，不需要重复请求了，可以做想做的事情了");
            Toast.makeText(this, "所有权限都有了", Toast.LENGTH_SHORT).show();
        } else {

            //不为空，表示需要去请求权限
            String[] RequestPermission = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, RequestPermission, 1);//请求权限

        }
    }

    boolean mShowRequestPermission = true;//用户是否禁止权限

    // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            builder = new AlertDialog.Builder(this);
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //获得权限
                    Log.d("print", "onRequestPermissionsResult: 回调，获得权限,尽情做你想做的事情吧！" + permissions[i].toString());
                } else {
                    //用户拒绝了权限就会执行这里。
                    //第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
                    Log.d("print", "onRequestPermissionsResult: 用户点了拒绝，权限被拒绝了");
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]) && builder != null) {
                         builder.setMessage("您拒绝了权限可能会导致应用闪退，请允许使用权限!")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        judgePermission();
                                        RequestPermission();
                                    }
                                }).show();
                        builder = null;
                    } else {
                        //用户勾选了不再询问  如果用户选择了不再提示，shouldShowRequestPermissionRationale为false
                        //这种情况下就可以提示用户手动打开权限,跳转到应用设置手动打开权限；
                        if (builder != null) {
                            builder
                                    .setMessage("没有权限可能导致应用闪退，请手动前往设置")
                                    .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            JumpPermissionManagement.GoToSetting(MoreActivity.this);
                                            Toast.makeText(MoreActivity.this, "前往系统界面进行权限设置....", Toast.LENGTH_SHORT).show();
                                        }
                                    }).show();
                            builder = null;
                        }
                    }
                }
            }
        }
    }

    /**
     * 拨打电话的权限，按官方API解释只要申请了一个危险权限，一组的都申请了但是此Demo在6.0模拟器并不是这样。
     */
    private void CALLphone() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri = Uri.parse("tel" + "10086");
        intent.setData(uri);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("print", "拨打电话没有这个权限因此不能调用");
            return;
        } else {
            Log.d("print", "有权限所以进行拨打电话的操作，返回的数值==" + ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE));
        }
        startActivity(intent);
    }

    public void btn_onclick(View view) {
        CALLphone();
    }

}

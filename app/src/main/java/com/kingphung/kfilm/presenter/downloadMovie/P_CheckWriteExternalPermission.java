package com.kingphung.kfilm.presenter.downloadMovie;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.kingphung.kfilm.view.downloadMovie.V_I_CheckWriteExternalPermission;

public class P_CheckWriteExternalPermission
            implements ActivityCompat.OnRequestPermissionsResultCallback {

    private boolean isPermissionGranted;
    private Context context;
    private V_I_CheckWriteExternalPermission v_i_checkWriteExternalPermission;
    public P_CheckWriteExternalPermission(Context context, V_I_CheckWriteExternalPermission v_i_checkWriteExternalPermission){
        this.context = context;
        this.v_i_checkWriteExternalPermission = v_i_checkWriteExternalPermission;
    }

    public void check(){
        if(checkForSDCard()){
            Toast.makeText(context,"there is a SD card", Toast.LENGTH_LONG).show();

            if(!checkForPermission()){
                ActivityCompat.requestPermissions(
                        (Activity)context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                        25);
            }else{
                v_i_checkWriteExternalPermission.onCompleteCheckExternalPermission(true);
            }
        }else{
            Toast.makeText(context,"No SD card", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length>0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED){
            v_i_checkWriteExternalPermission.onCompleteCheckExternalPermission(true);
        }else{
            v_i_checkWriteExternalPermission.onCompleteCheckExternalPermission(false);
        }
    }

    private boolean checkForSDCard(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }
    private boolean checkForPermission() {
        if(
                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED
        )return true;
        return false;
    }
}

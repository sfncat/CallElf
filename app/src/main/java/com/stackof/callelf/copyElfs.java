package com.stackof.callelf;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class copyElfs {
    String tag="StackOF:";
    Context ct;
    String appFileDirectory, executableFilePath;
    AssetManager assetManager;
    List resList;
    String cpuType;
    String[] assetsFiles ={
            "hello"
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    copyElfs(Context c){
        ct=c;

//        appFileDirectory = ct.getFilesDir().getPath();
        appFileDirectory = "/data/data/"+ct.getPackageName();

        executableFilePath = appFileDirectory + "/executable";
        Log.e(tag, "cpu type:"+ cpuType);
//        cputype = Build.SUPPORTED_ABIS[0];
        cpuType = Build.CPU_ABI;
        Log.e(tag, "cpu type:"+ cpuType);
//        Build.
        assetManager = ct.getAssets();
        try {
            resList = Arrays.asList(ct.getAssets().list(cpuType +"/"));
            Log.d(tag,"get assets list:"+ resList.toString());
        } catch (IOException e){
            Log.e(tag, "error list assets folder:", e);
        }
    }
    boolean resFileExist(String filename){
        File f=new File(executableFilePath +"/"+filename);
        if (f.exists())
            return true;
        return false;
    }
    void copyFile(InputStream in, OutputStream out){
        try {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (IOException e){
            Log.e(tag, "failed to read/write asset file: ", e);
        }
    };
    private void copyAssets(String filename) {
        InputStream in = null;
        OutputStream out = null;
        Log.d(tag, "attempting to copy this file: " + filename);

        try {
            in = assetManager.open(cpuType +"/"+filename);
            File outfile = new File(executableFilePath, filename);
            out = new FileOutputStream(outfile);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch(IOException e) {
            Log.e(tag, "failed to copy asset file: " + filename, e);
        }
        Log.d(tag, "copy success: " + filename);
    }
    void copyAll2Data(){
        int i;

        File folder=new File(executableFilePath);
        if (!folder.exists()){
            folder.mkdir();
        }

        for(i=0; i< assetsFiles.length; i++){
            if (!resFileExist(assetsFiles[i])){
                copyAssets(assetsFiles[i]);
                File execfile = new File(executableFilePath +"/"+ assetsFiles[i]);
                execfile.setExecutable(true);
            }
        }
    }

    String getExecutableFilePath(){
        return executableFilePath;
    }
}

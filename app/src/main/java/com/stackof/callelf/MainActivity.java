package com.stackof.callelf;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    String Tag = "StackOF";
    TextView textview1;
    int c=0;
    copyElfs ce;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview1=(TextView)findViewById(R.id.textView1);
        ce = new copyElfs(getBaseContext());
        ce.copyAll2Data();

    }
    public String callElf(String cmd){
        Process p;
        String tmptext;
        String execresult = "";

        try {
            p = Runtime.getRuntime().exec("/system/bin/linker64 "+ce.getExecutableFilePath() + "/"+cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((tmptext = br.readLine()) != null) {
                execresult += tmptext+"\n";
            }
        }catch (IOException e){
            Log.i(Tag,e.toString());
        }
        return execresult;
    }
    public void bt1_click(View view){
        c = c+1;
        textview1.setText("click:"+c+"\n"+callElf("hello"));
    }
}

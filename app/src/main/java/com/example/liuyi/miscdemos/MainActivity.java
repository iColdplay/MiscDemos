package com.example.liuyi.miscdemos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Xml;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        android.util.Log.i("ly20170808","should request permission ?");
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            android.util.Log.i("ly20170808","gonna request permission");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 8989);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        b1 = (Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = "";
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    fileName = Environment.getExternalStorageDirectory()+"ram_settings.xml";
                }else{
                    fileName += "ram_settings.xml";
                }
                File f = new File(fileName);
                try{
                    f.createNewFile();
                }catch (Exception e){
                    throw new AndroidRuntimeException(e);
                }
                try{
                    FileOutputStream fos = new FileOutputStream(f);
                    XmlSerializer serializer = Xml.newSerializer();

                    serializer.setOutput(fos, "utf-8");
                    serializer.startDocument(null, true);

                    serializer.startTag(null, "ram");
                    serializer.attribute(null, "mode", "1");

                    serializer.startTag(null, "rom");
                    serializer.text("256G");
                    serializer.endTag(null, "rom");
                    serializer.endDocument();
                    serializer.flush();
                    fos.close();
                }catch (Exception e){
                    throw new AndroidRuntimeException(e);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                android.util.Log.i("ly20170808","we know now");
            }
        }
    }
}

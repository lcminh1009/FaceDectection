package com.example.facedectection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adapter.StickerAdapter;
import com.example.model.Sticker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //ImageView imgHinh;
    ImageButton btnSave;
    ImageButton btnCamera;
    ImageButton btnGallery;
    Bitmap bitmap;
    private FaceOverlayView mFaceOverlayView;
    //OutputStream outputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addControls();
        addEvents();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Sticker> arrayList = new ArrayList<>();
        arrayList.add(new Sticker("cheek 3",R.drawable.cheek_3,1));
        arrayList.add(new Sticker("cheek lips",R.drawable.cheek_lips,2));
        arrayList.add(new Sticker("cheek love",R.drawable.cheek_love,3));
        arrayList.add(new Sticker("dog",R.drawable.dog,4));
        arrayList.add(new Sticker("glasses",R.drawable.glasses,5));
        arrayList.add(new Sticker("star",R.drawable.star,6));
        StickerAdapter stickerAdapter = new StickerAdapter(arrayList,getApplicationContext());
        stickerAdapter.setOnStickerSelect(new StickerAdapter.OnStickerSelect() {
            @Override
            public void onSelect(Sticker sticker) {
                mFaceOverlayView.setBitmap(bitmap,sticker.getRule());
            }
        });
        recyclerView.setAdapter(stickerAdapter);
    }

    private void addControls() {
        btnCamera = findViewById(R.id.btnCamera);
        btnGallery = findViewById(R.id.btnGallery);
        btnSave =  findViewById(R.id.btnSave);
        mFaceOverlayView =findViewById( R.id.face_overlay );

    }

    private void addEvents() {
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 69);
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 96);
            }
        });
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FileOutputStream fileOutputStream = null;
//                File file = getdisc();
//                if (!file.exists() && !file.mkdir()){
//                    Toast.makeText(getApplicationContext(),"sorry can not make dir",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmsshhmmss");
//                String date = simpleDateFormat.format(new Date());
//                String name = "img" + date +".jpeg";
//                String file_name = file.getAbsolutePath()+ "/" + name;
//                File new_file = new File(file_name);
//                try {
//                    fileOutputStream = new FileOutputStream(new_file);
//                    Bitmap bitmap = viewToBitmap(imgHinh, imgHinh.getWidth(),imgHinh.getHeight());
//                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
//                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
//                    fileOutputStream.flush();
//                    fileOutputStream.close();
//                } catch (FileNotFoundException e){
//
//                } catch (IOException e){
//
//                } refreshGallary(file);
//
//            }
//
//            private void refreshGallary(File file) {
//                Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                i.setData(Uri.fromFile(file));
//                sendBroadcast(i);
//            }
//
//
//            private File getdisc() {
//                ContextWrapper cw = new ContextWrapper(getApplicationContext());
//                File file = cw.getDir("imageDir", Context.MODE_PRIVATE);
//                return  new File(file,"My Image");
//            }
//        });

    }

    private static Bitmap viewToBitmap(View view, int witdth, int height) {
        Bitmap bitmap = Bitmap.createBitmap(witdth, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 69 && resultCode == RESULT_OK){
            bitmap = (Bitmap) data.getExtras().get("data");
            mFaceOverlayView.setBitmap(bitmap,0);
        }
        if(requestCode == 96 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                mFaceOverlayView.setBitmap(bitmap,0);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

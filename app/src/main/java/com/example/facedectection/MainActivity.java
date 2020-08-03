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
import android.util.Log;
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
    Bitmap facebitmap;
    private FaceOverlayView mFaceOverlayView;
    OutputStream outputStream;
    String TAG = "MainActivity";

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
        arrayList.add(new Sticker("Rainbow glasses",R.drawable.glasses0,1));
        arrayList.add(new Sticker("Thug life",R.drawable.glasses1,2));
        arrayList.add(new Sticker("dollar glasses",R.drawable.glasses3,3));
        arrayList.add(new Sticker("Saclo",R.drawable.glasses4,4));
        arrayList.add(new Sticker("lipstick",R.drawable.lips,5));
        arrayList.add(new Sticker("love",R.drawable.love,6));
        arrayList.add(new Sticker("fire eyes",R.drawable.fire,7));
        arrayList.add(new Sticker("beard",R.drawable.beard2,8));

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
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Here");
                //facebitmap = BitmapFactory.decodeResource(getResources(),R.drawable.glasses0);
                facebitmap = mFaceOverlayView.getmBitmap();

                if (facebitmap == null) {
                    Log.d(TAG, "facebitmap null");
                    return;
                }
                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsolutePath()+"/Camera");
                if (! dir.exists()){
                    if (! dir.mkdirs()){
                        Log.d(TAG, "Make dir failed!");
                        return;
                    }
                }
                File file = new File(dir.getPath() + File.separator + System.currentTimeMillis()+".jpg");
                try {
                    outputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }

                try {
                    facebitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d(TAG, "Error accessing file: " + e.getMessage());
                }
                Toast.makeText(getApplicationContext(),"Image save to Gallery " + file.getPath(), Toast.LENGTH_SHORT).show();
            }
        });
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

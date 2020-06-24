//package com.example.facedectection;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//
//import java.io.InputStream;
//
//public class MainActivity extends AppCompatActivity {
//
//    private FaceOverlayView mFaceOverlayView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mFaceOverlayView =(FaceOverlayView)findViewById( R.id.face_overlay );
//        InputStream stream = getResources().openRawResource( R.raw.amee );
//        Bitmap bitmap = BitmapFactory.decodeStream(stream);
//        mFaceOverlayView.setBitmap(bitmap);
//    }
//}

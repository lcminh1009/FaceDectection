package com.example.facedectection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;


import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

public class FaceOverlayView extends View {
    private Bitmap mBitmap;
    private Bitmap resolvedMBitmap;
    private SparseArray<Face> mFaces;
    public Canvas canvas;
    private int mRule;
    Bitmap mSticker, mSticker1;

    public FaceOverlayView(Context context) {
        super(context, null);
    }

    public FaceOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FaceOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public Bitmap getResolvedMBitmap() {
        if (canvas != null) {
            resolvedMBitmap = Bitmap.createBitmap( (int)canvas.getWidth(), (int)canvas.getHeight(), Bitmap.Config.RGB_565);
            canvas.setBitmap(resolvedMBitmap);
        }
        return null;
    }

    public void setBitmap(Bitmap bitmap, int rule) {
        mBitmap = bitmap;
        mRule = rule;
        FaceDetector detector = new FaceDetector.Builder(getContext())
                .setTrackingEnabled(true)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.ACCURATE_MODE)
                .build();
        if (!detector.isOperational()) {
            //Handle contingency
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            mFaces = detector.detect(frame);
            detector.release();
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ((mBitmap != null) && (mFaces != null)) {
            double scale = drawBitmap(canvas);
            //case :
            //drawFaceBox(canvas, scale);
            drawFaceLandmarks(canvas, scale);
            this.canvas = canvas;
        }
    }

    private double drawBitmap(Canvas canvas) {
        double viewWidth = canvas.getWidth();
        double viewHeight = canvas.getHeight();
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();
        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);

        Rect destBounds = new Rect(0, 0, (int) (imageWidth * scale), (int) (imageHeight * scale));
        canvas.drawBitmap(mBitmap, null, destBounds, null);
        return scale;
    }

//    private void drawFaceBox(Canvas canvas, double scale) {
//        Paint paint = new Paint();
//        paint.setColor(Color.GREEN);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(5);
//
//        float left = 0;
//        float top = 0;
//        float right = 0;
//        float bottom = 0;
//
//        for( int i = 0; i < mFaces.size(); i++ ) {
//            Face face = mFaces.valueAt(i);
//
//            left = (float) ( face.getPosition().x * scale );
//            top = (float) ( face.getPosition().y * scale );
//            right = (float) scale * ( face.getPosition().x + face.getWidth() );
//            bottom = (float) scale * ( face.getPosition().y + face.getHeight() );
//
//            canvas.drawRect( left, top, right, bottom, paint );
//        }
//    }

    private void drawFaceLandmarks(Canvas canvas, double scale) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        switch (mRule){
            case 0:
                for (int i = 0; i < mFaces.size(); i++) {
                    //lấy hàm
                    Face face = mFaces.valueAt(i);

                    for (Landmark landmark : face.getLandmarks()) {
                        int cx = (int) (landmark.getPosition().x * scale);
                        int cy = (int) (landmark.getPosition().y * scale);
                    }
                }
                break;
            //kinh 7 mau
            case 1:
                mSticker = BitmapFactory.decodeResource(getResources(),R.drawable.glasses0);
                for (int i = 0; i < mFaces.size(); i++) {
                    //lấy hàm
                    Face face = mFaces.valueAt(i);
                    float angle = (float)(face.getEulerZ());
                    Landmark landmark0 = face.getLandmarks().get(0);
                    int xlefteye = (int) (landmark0.getPosition().x * scale);
                    int ylefteye = (int) (landmark0.getPosition().y * scale);

                    Landmark landmark1 = face.getLandmarks().get(1);
                    int xrighteye = (int) (landmark1.getPosition().x * scale);
                    int yrighteye = (int) (landmark1.getPosition().y * scale);

                    int eyeDistance = xrighteye - xlefteye;
                    int delta = (int) (eyeDistance/2);
                    int top = ylefteye - delta;
                    int left = xlefteye - delta;
                    int bottom = yrighteye + delta;
                    int right = xrighteye + delta;
                    Rect hcn = new Rect(left, top, right, bottom);
                    canvas.save();
                    canvas.rotate(-angle, left, top);
                    canvas.drawBitmap(mSticker,null, hcn, null);

                }
                break;
            //kinh thuglife
            case 2:
                mSticker = BitmapFactory.decodeResource(getResources(),R.drawable.glasses2);
                mSticker1 = BitmapFactory.decodeResource(getResources(),R.drawable.tabaco);
                for (int i = 0; i < mFaces.size(); i++) {
                    Face face = mFaces.valueAt(i);
                    float angle = (float)(face.getEulerZ());
                    Landmark landmark0 = face.getLandmarks().get(0);
                    int xlefteye = (int) (landmark0.getPosition().x * scale);
                    int ylefteye = (int) (landmark0.getPosition().y * scale);

                    Landmark landmark1 = face.getLandmarks().get(1);
                    int xrighteye = (int) (landmark1.getPosition().x * scale);
                    int yrighteye = (int) (landmark1.getPosition().y * scale);

                    int eyeDistance = xrighteye - xlefteye;
                    int delta = (int) (eyeDistance/2);
                    int top1 = (int) (ylefteye - 0.2*eyeDistance);
                    int left1 = (int) (xlefteye - 1.1*delta);
                    int bottom1 = (int) (yrighteye + 0.2*eyeDistance);
                    int right1 = (int)(xrighteye + 1.1*delta);
                    Rect hcn1 = new Rect(left1, top1, right1, bottom1);
                    canvas.save();
                    canvas.rotate(-angle, left1, top1);
                    canvas.drawBitmap(mSticker, null, hcn1, null);
                    canvas.restore();

                    Landmark landmark = face.getLandmarks().get(5);
                    int cx = (int) (landmark.getPosition().x * scale);
                    int cy = (int) (landmark.getPosition().y * scale);
                    int top2 = cy;
                    int left2 = cx;
                    int bottom2 = (int)(cy + eyeDistance*0.75);
                    int right2 = (int)(cx + eyeDistance*1.2);
                    Rect hcn2 = new Rect(left2, top2, right2, bottom2);
                    canvas.save();
                    canvas.rotate(-angle, left1, top1);
                    canvas.drawBitmap(mSticker1, null, hcn2, null);
                    canvas.restore();
                }
                break;
            // dong tien
            case 3:
                mSticker = BitmapFactory.decodeResource(getResources(),R.drawable.glasses3);
                for (int i = 0; i < mFaces.size(); i++) {
                    //lấy hàm
                    Face face = mFaces.valueAt(i);
                    float angle = (float)(face.getEulerZ());
                    Landmark landmark0 = face.getLandmarks().get(0);
                    int xlefteye = (int) (landmark0.getPosition().x * scale);
                    int ylefteye = (int) (landmark0.getPosition().y * scale);

                    Landmark landmark1 = face.getLandmarks().get(1);
                    int xrighteye = (int) (landmark1.getPosition().x * scale);
                    int yrighteye = (int) (landmark1.getPosition().y * scale);

                    int eyeDistance = xrighteye - xlefteye;
                    int delta = (int) (eyeDistance / 2);
                    int top1 = ylefteye - delta;
                    int left1 = (int) (xlefteye - 0.3*eyeDistance);
                    int bottom1 = ylefteye + delta;
                    int right1 = (int) (xlefteye + 0.3*eyeDistance);

                    int top2 = yrighteye - delta;
                    int left2 = (int) (xrighteye - 0.3*eyeDistance);
                    int bottom2 = yrighteye + delta;
                    int right2 = (int) (xrighteye + 0.3*eyeDistance);
                    Rect hcn1 = new Rect(left1, top1, right1, bottom1);
                    Rect hcn2 = new Rect(left2, top2, right2, bottom2);
                    canvas.save();
                    canvas.rotate(-angle, left1, top1);
                    canvas.drawBitmap(mSticker,null, hcn1, null);
                    canvas.drawBitmap(mSticker,null, hcn2, null);
                }
                break;
            // kinh + mui
            case 4:
                mSticker = BitmapFactory.decodeResource(getResources(),R.drawable.glasses4);
                for (int i = 0; i < mFaces.size(); i++) {
                    //lấy hàm
                    Face face = mFaces.valueAt(i);
                    float angle = (float)(face.getEulerZ());
                    Landmark landmark0 = face.getLandmarks().get(0);
                    int xlefteye = (int) (landmark0.getPosition().x * scale);
                    int ylefteye = (int) (landmark0.getPosition().y * scale);

                    Landmark landmark1 = face.getLandmarks().get(1);
                    int xrighteye = (int) (landmark1.getPosition().x * scale);
                    int yrighteye = (int) (landmark1.getPosition().y * scale);

                    int eyeDistance = xrighteye - xlefteye;
                    int delta = (int) (eyeDistance/2);
                    int top = (int) (ylefteye - 1.8*delta);
                    int left = (int) (xlefteye - 1.2*delta);
                    int bottom = (int) (yrighteye + 2.4*delta);
                    int right = (int) (xrighteye + 1.2*delta);
                    Rect hcn = new Rect(left, top, right, bottom);
                    canvas.save();
                    canvas.rotate(-angle, left, top);
                    canvas.drawBitmap(mSticker,null, hcn, null);
                }
                break;
            // moi hon
            case 5:

                mSticker = BitmapFactory.decodeResource(getResources(),R.drawable.lips);
                for (int i = 0; i < mFaces.size(); i++) {
                    //lấy hàm
                    Face face = mFaces.valueAt(i);
                    float angle = (float)(face.getEulerZ());
                    Landmark landmark = face.getLandmarks().get(10);
                    int cx = (int) (landmark.getPosition().x * scale);
                    int cy = (int) (landmark.getPosition().y * scale);

                    Landmark landmark0 = face.getLandmarks().get(0);
                    int xlefteye = (int) (landmark0.getPosition().x * scale);

                    Landmark landmark1 = face.getLandmarks().get(1);
                    int xrighteye = (int) (landmark1.getPosition().x * scale);

                    int eyeDistance = xrighteye - xlefteye;
                    int top = (int) (cy - eyeDistance*0.4);
                    int left = (int) (cx - eyeDistance*0.3);
                    int bottom = (int) (cy + eyeDistance*0.1);
                    int right = (int) (cx + eyeDistance*0.3);
                    Rect hcn = new Rect(left, top, right, bottom);
                    canvas.save();
                    canvas.rotate(-angle, left, top);
                    canvas.drawBitmap(mSticker,null, hcn, null);
                }
                break;

            // trai tim
            case 6:
                mSticker = BitmapFactory.decodeResource(getResources(),R.drawable.love);
                for (int i = 0; i < mFaces.size(); i++) {
                    //lấy hàm
                    Face face = mFaces.valueAt(i);
                    float angle = (float)(face.getEulerZ());
                    Landmark landmark = face.getLandmarks().get(10);
                    int lx = (int) (landmark.getPosition().x * scale);
                    int ly = (int) (landmark.getPosition().y * scale);

                    Landmark landmark2 = face.getLandmarks().get(11);
                    int rx = (int) (landmark2.getPosition().x * scale);
                    int ry = (int) (landmark2.getPosition().y * scale);

                    Landmark landmark0 = face.getLandmarks().get(0);
                    int xlefteye = (int) (landmark0.getPosition().x * scale);

                    Landmark landmark1 = face.getLandmarks().get(1);
                    int xrighteye = (int) (landmark1.getPosition().x * scale);

                    int eyeDistance = xrighteye - xlefteye;
                    int top1 = (int) (ly - eyeDistance*0.28);
                    int left1 = (int) (lx - eyeDistance*0.14);
                    int bottom1 = ly;
                    int right1 = (int) (lx + eyeDistance*0.14);
                    Rect hcn1 = new Rect(left1, top1, right1, bottom1);
                    canvas.save();
                    canvas.rotate(-angle, left1, top1);
                    canvas.drawBitmap(mSticker,null, hcn1, null);
                    canvas.restore();

                    int top2 = (int) (ry - eyeDistance*0.28);
                    int left2 = (int) (rx - eyeDistance*0.14);
                    int bottom2 = ry;
                    int right2 = (int) (rx + eyeDistance*0.14);
                    Rect hcn2 = new Rect(left2, top2, right2, bottom2);
                    canvas.save();
                    canvas.rotate(-angle, left2, top2);
                    canvas.drawBitmap(mSticker,null, hcn2, null);
                    canvas.restore();
                }
                break;
            // fire eye
            case 7:
                mSticker = BitmapFactory.decodeResource(getResources(),R.drawable.fire);
                for (int i = 0; i < mFaces.size(); i++) {
                    //lấy hàm
                    Face face = mFaces.valueAt(i);
                    float angle = (float)(face.getEulerZ());
                    Landmark landmark0 = face.getLandmarks().get(0);
                    int xlefteye = (int) (landmark0.getPosition().x * scale);
                    int ylefteye = (int) (landmark0.getPosition().y * scale);

                    Landmark landmark1 = face.getLandmarks().get(1);
                    int xrighteye = (int) (landmark1.getPosition().x * scale);
                    int yrighteye = (int) (landmark1.getPosition().y * scale);

                    int eyeDistance = xrighteye - xlefteye;
                    int delta = (int) (eyeDistance / 2);
                    int top1 = (int) (ylefteye - 1.1*delta);
                    int left1 = (int) (xlefteye - 0.3*eyeDistance);
                    int bottom1 = (int) (ylefteye + 0.4*delta);
                    int right1 = (int) (xlefteye + 0.3*eyeDistance);

                    int top2 = (int) (yrighteye - 1.1*delta);
                    int left2 = (int) (xrighteye - 0.3*eyeDistance);
                    int bottom2 = (int) (yrighteye + 0.4*delta);
                    int right2 = (int) (xrighteye + 0.3*eyeDistance);
                    Rect hcn1 = new Rect(left1, top1, right1, bottom1);
                    Rect hcn2 = new Rect(left2, top2, right2, bottom2);
                    canvas.save();
                    canvas.rotate(-angle, left1, top1);
                    canvas.drawBitmap(mSticker,null, hcn1, null);
                    canvas.drawBitmap(mSticker,null, hcn2, null);
                }
                break;
            case 8:
                mSticker = BitmapFactory.decodeResource(getResources(),R.drawable.beard2);
                for (int i = 0; i < mFaces.size(); i++) {
                    //lấy hàm
                    Face face = mFaces.valueAt(i);
                    float angle = (float)(face.getEulerZ());
                    Landmark landmark = face.getLandmarks().get(6);
                    int lx = (int) (landmark.getPosition().x * scale);
                    int ly = (int) (landmark.getPosition().y * scale);

                    Landmark landmark2 = face.getLandmarks().get(7);
                    int rx = (int) (landmark2.getPosition().x * scale);
                    int ry = (int) (landmark2.getPosition().y * scale);

                    Landmark landmark0 = face.getLandmarks().get(0);
                    int xlefteye = (int) (landmark0.getPosition().x * scale);

                    Landmark landmark1 = face.getLandmarks().get(1);
                    int xrighteye = (int) (landmark1.getPosition().x * scale);

                    int eyeDistance = xrighteye - xlefteye;
                    int top = (int)(ly + eyeDistance*0.2);
                    int left = lx;
                    int bottom = (int) (ry + eyeDistance*2);
                    int right = rx;
                    Rect hcn = new Rect(left, top, right, bottom);
                    canvas.save();
                    canvas.rotate(-angle, left, top);
                    canvas.drawBitmap(mSticker,null, hcn, null);
                }
                break;
        }
    }
}

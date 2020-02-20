package com.aseyel.tgbl.tristangaryleyesa.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Romeo on 2018-01-27.
 */

public class LiquidCanvas extends View {
    private static final String TAG = LiquidCanvas.class.getSimpleName();
    static final float STROKE_WIDTH = 5f;
    static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
    Paint paint = new Paint();
    Path path = new Path();
    Context mContext;
    float lastTouchX;
    float lastTouchY;
    final RectF dirtyRect = new RectF();
    LinearLayout mContent;

    public LiquidCanvas(Context context, AttributeSet attrs,LinearLayout mLinearLayout) {
        super(context, attrs);
        this.mContext = context;
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(STROKE_WIDTH);
        mContent = mLinearLayout;
    }

    public void save(String FileName) {
        FileOutputStream fileOutputStream = null;
        File file = Liquid.getDiscSignature(Liquid.SelectedAccountNumber);
        if(!file.exists() && !file.mkdirs()){
            Liquid.ShowMessage(getContext(),"Can't create directory to save image");
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = simpleDateFormat.format(new Date());
        String name = FileName+".jpg";
        String file_name = file.getAbsolutePath()+"/"+name;
        Log.i(TAG,"Tristan "+file_name);
        File new_file = new File(file_name);

        Bitmap returnedBitmap = Bitmap.createBitmap(mContent.getWidth(),
                mContent.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = mContent.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        mContent.draw(canvas);
        try{
            fileOutputStream = new FileOutputStream(new_file);
            returnedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
            Liquid.ShowMessage(getContext(),"Save Image Success");
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch(Exception e){
            Log.e(TAG,"Error :",e);
        }

    }

    public void saveSubFolder(String FileName) {
        FileOutputStream fileOutputStream = null;
        File file = Liquid.getDiscByJOSignature(Liquid.SelectedId);
        if(!file.exists() && !file.mkdirs()){
            Liquid.ShowMessage(getContext(),"Can't create directory to save image");
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = simpleDateFormat.format(new Date());
        String name = FileName+".jpg";
        String file_name = file.getAbsolutePath()+"/"+name;

        File new_file = new File(file_name);

        Bitmap returnedBitmap = Bitmap.createBitmap(mContent.getWidth(),
                mContent.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = mContent.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        mContent.draw(canvas);
        try{
            fileOutputStream = new FileOutputStream(new_file);
            returnedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
            Liquid.ShowMessage(getContext(),"Save Image Success");
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch(Exception e){
            Log.e(TAG,"Error :",e);
        }

    }

    public void clear() {
        path.reset();
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                lastTouchX = eventX;
                lastTouchY = eventY;
                return true;

            case MotionEvent.ACTION_MOVE:

            case MotionEvent.ACTION_UP:

                resetDirtyRect(eventX, eventY);
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    path.lineTo(historicalX, historicalY);
                }
                path.lineTo(eventX, eventY);
                break;
        }

        invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

        lastTouchX = eventX;
        lastTouchY = eventY;

        return true;
    }

    private void resetDirtyRect(float eventX, float eventY) {
        dirtyRect.left = Math.min(lastTouchX, eventX);
        dirtyRect.right = Math.max(lastTouchX, eventX);
        dirtyRect.top = Math.min(lastTouchY, eventY);
        dirtyRect.bottom = Math.max(lastTouchY, eventY);
    }
}

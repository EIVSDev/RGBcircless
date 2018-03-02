package com.example.slava.rgbcircless;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class CanvasView extends View implements  ICanvasView {

    public  static  int wight;
    public  static  int height;
    private GameManager gameManager;
    private Paint paint;
    private Canvas canvas;
    private Toast toast;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWihthAndHeight(context);
        initPaint();
        gameManager = new GameManager(this,wight,height);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    private void initWihthAndHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        wight = point.x;
        height = point.y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
     //   gameManager.onDraw(canvas);
        this.canvas = canvas;
        gameManager.onDraw();
    }

    @Override
    public void drawCircle(SimpleCircle circle) {

        paint.setColor(circle.getColor());
        canvas.drawCircle(circle.getX(),circle.getY(),circle.getRadius(),paint);
    }

    @Override
    public void redraw() {
        invalidate();
    }

    @Override
    public void showMessage(String text) {
    if(toast!=null){
        toast.cancel();
    }
    toast=Toast.makeText(getContext(),text,Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.CENTER,0,0);
    toast.show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            gameManager.onTouchEvent(x,y);
        }

        invalidate();
        return true;
    }
}

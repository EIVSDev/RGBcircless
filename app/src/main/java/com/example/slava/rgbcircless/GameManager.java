package com.example.slava.rgbcircless;

import java.util.ArrayList;

public class GameManager {

    public static final int MAX_CIRCLES = 10;
    private MainCircle mainCircle;
    private ArrayList<EnemyCircle>circles;
    private CanvasView canvasView;
    private static int widht;
    private static int height;

    public GameManager(CanvasView canvasView, int w, int h) {
        this.canvasView = canvasView;
        widht = w;
        height = h;
       initMainCircle();
       initEnemyCircles();

    }

    private void initEnemyCircles() {
        SimpleCircle mainCircleArea = mainCircle.getCircleArea();
        circles = new ArrayList<>();
        for (int i = 0; i < MAX_CIRCLES; i++) {
            EnemyCircle circle;

            do{
            circle = EnemyCircle.getRandomCircle();}
            while (circle.isIntersec(mainCircleArea));
            circles.add(circle);
        }
        calculateAndSetCircleColor();
    }

    private void calculateAndSetCircleColor() {
  for(EnemyCircle circle : circles){
      circle.setEnemyForFoodColorDependsOn(mainCircle);
  }

    }

    public static int getWidth() {
        return widht;
    }

    public static int getHeight() {
        return height;
    }

    private void initMainCircle() {
        mainCircle=new MainCircle(widht/2,height/2);
    }

    public void onDraw() {
        canvasView.drawCircle(mainCircle);

         for(EnemyCircle circle : circles){
             canvasView.drawCircle(circle);
         }

    }

    public void onTouchEvent(int x, int y) {
        mainCircle.moveMainCircleWhenTouchAt(x,y);
        checkCollision();
        moveCircles();
    }

    private void checkCollision() {
        SimpleCircle circleForDel=null;
        for (EnemyCircle circle : circles) {
            if(mainCircle.isIntersec(circle)){
               if(circle.isSmallerThan(mainCircle)){
                 mainCircle.growRadius(circle);
                 circleForDel = circle;
                 calculateAndSetCircleColor();
                 break;
               }else {
                   gameEnd("YOU LOSE!");
                   return;
               }
            }
        }
        if (circleForDel != null){
            circles.remove(circleForDel);
        }
        if(circles.isEmpty()){
            gameEnd("YOU WIN!");
        }
        
    }

    private void gameEnd(String text) {
        canvasView.showMessage(text);
        mainCircle.initRadius();
        initEnemyCircles();
        canvasView.redraw();

    }

    private void moveCircles() {
        for (EnemyCircle circle : circles) {
            circle.moveOneStep();
        }
            
        }
    }


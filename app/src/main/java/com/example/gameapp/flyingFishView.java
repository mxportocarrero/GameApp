package com.example.gameapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class flyingFishView extends View
{
    private Bitmap fish[] = new Bitmap[2];
    private int fishX = 10;
    private int fishY;
    private int fishSpeed;

    private int canvasWidth,canvasHeight;

    private boolean touch = false;

    private int yelloX, yellowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private int num_redBalls = 4;
    private int redX[],redY[], redSpeed = 25;
    private Paint redPaint = new Paint();






    private Bitmap backgroundImage;

    private Paint scorePaint = new Paint();

    private Bitmap life[] = new Bitmap[2];


    private int score,lifeCounter;

    // Constructor
    public flyingFishView(Context context)
    {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.fish2);

        backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.background);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishY = 550;

        // Balls
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        score = 0;
        lifeCounter = 4;

        redX = new int[num_redBalls];
        redY = new int[num_redBalls];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();



        // Primero cargamos el background
        canvas.drawBitmap(backgroundImage,0,0,null);

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;

        fishY = fishY + fishSpeed;

        if(fishY < minFishY)
        {
            fishY = minFishY;
        }
        if (fishY > maxFishY)
        {
            fishY = maxFishY;
        }

        fishSpeed = fishSpeed +2;

        if (touch)
        {
            canvas.drawBitmap(fish[1],fishX,fishY,null);
            touch = false;
        }
        else
        {
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }

        //YellowBall
        yelloX = yelloX - yellowSpeed;

        if (hitBallChecker(yelloX,yellowY))
        {
            score = score + 10;
            yelloX = -100;
        }

        if (yelloX < 0)
        {
            yelloX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;

        }

        canvas.drawCircle(yelloX,yellowY,25,yellowPaint);

        // Green Ball
        greenX = greenX - greenSpeed;

        if (hitBallChecker(greenX,greenY))
        {
            score = score + 50;
            greenX = -100;
        }

        if (greenX < 0)
        {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }

        canvas.drawCircle(greenX,greenY,15,greenPaint);

        for (int i = 0; i < num_redBalls; i++)
        {
            redX[i] = redX[i] - redSpeed;
            if (hitBallChecker(redX[i],redY[i]))
            {
                redX[i] = -100;
                lifeCounter--;

                if (lifeCounter == 0)
                {
                    Toast.makeText(getContext(),"Game Over",Toast.LENGTH_SHORT).show();

                    Intent gameOverIntent = new Intent(getContext(),GameOverActivity.class);
                    gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    gameOverIntent.putExtra("score",score);

                    getContext().startActivity(gameOverIntent); // Por que estamos en una clase


                }
            }

            if (redX[i]< 0)
            {
                redX[i] = canvasWidth + 21 + (int)Math.floor(Math.random() * 200);
                redY[i] = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
            }

            canvas.drawCircle(redX[i],redY[i],30,redPaint);
        } // Fin de dibujar los obstaculos


        canvas.drawText("score : " + score,20,60, scorePaint);

        // Drawing the hearts
        for(int i = 0; i < 4; i++)
        {
            if (i  < lifeCounter)
                canvas.drawBitmap(life[0],580 + i * 100, 10, null);
            else
                canvas.drawBitmap(life[1],580 + i * 100, 10, null);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touch = true;

            fishSpeed = -22;
        }
        return true;
    }

    public boolean hitBallChecker(int x, int y)
    {
        if(fishX < x && x <(fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight()))
        {
            return true;
        }
        return false;
    }


}

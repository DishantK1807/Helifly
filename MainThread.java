package com.example.raghav_dell.my_first_game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread
{
    private int FPS=28;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder,GamePanel gamePanel)
    {
        super();
        this.surfaceHolder=surfaceHolder;
        this.gamePanel=gamePanel;

    }

        @Override
         public void run()
        {
            long starttime;
            long timeMillis;
            long waitTime;
            long totalTime=0;
            int frameCount=0;
            long targetTime=1000/FPS;


            while(running)
            {
                starttime = System.nanoTime();
                canvas=null;
               try
               {
                  canvas=this.surfaceHolder.lockCanvas();
                   synchronized (surfaceHolder)
                   {
                       this.gamePanel.update();
                       this.gamePanel.draw(canvas);

                   }
            }catch (Exception e){}
               finally {
                          if(canvas!=null)
                          {
                              try{
                                          surfaceHolder.unlockCanvasAndPost(canvas);

                                 }catch(Exception e)
                              {
                                  e.printStackTrace();
                              }
                          }
               }
                timeMillis=( System.nanoTime()-starttime)/1000000;
                waitTime=targetTime-timeMillis;

                try {
                      this.sleep(waitTime);
                    }catch (Exception e){}



                    totalTime += System.nanoTime()-starttime;
                    frameCount++;
                    if(frameCount==FPS)
                    {
                        averageFPS=1000/((totalTime)/frameCount/1000000);
                        frameCount=0;
                        totalTime=0;
                        System.out.println(averageFPS);
                    }
        }
}

       public void setRunning(boolean b)
       {
           running=b;
       }
}

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class BouncingBall extends Application
{
    static double BALLRADIUS = 20;
    static int SCREENW = 1000;
    static int SCREENH = 1000;
    static int PLAYERY = 900;

    Circle mBall = new Circle(BALLRADIUS, Color.RED);
    double mBVx = 10;
    double mBVy = 5;
    double mBPx = 100;
    double mBPy = 300;

    Rectangle mPlayer = new Rectangle(150, 25, Color.BLACK);
    boolean mAPressed = false;
    boolean mDPressed = false;
    double mSpeed = 15;
    double mRPx = 300;

    Rectangle mGameOver = new Rectangle(SCREENH, 10, Color.BLACK);

    Pane mCanvas = new Pane();
    Scene mScene = new Scene(mCanvas, SCREENW, SCREENH);

    @Override
    public void start(Stage window) 
    {
        mScene.setOnKeyPressed(e ->
        {
            if(e.getCode() == KeyCode.A)
            {
                mAPressed = true;
            }
            if(e.getCode() == KeyCode.D)
            {
                mDPressed = true;
            }
        });

        mScene.setOnKeyReleased(e -> 
        {
            if(e.getCode() == KeyCode.A)
            {
                mAPressed = false;
            }
            if(e.getCode() == KeyCode.D)
            {
                mDPressed = false;
            }
        });  

        mBall.relocate(mBPx,mBPy);
        mGameOver.relocate(0, SCREENH-10);
        mPlayer.relocate(mRPx,PLAYERY);
        mCanvas.getChildren().addAll(mBall, mPlayer, mGameOver);
        window.setTitle("Moving Ball");
        window.setScene(mScene);
        window.show();

        AnimationTimer timer = new AnimationTimer() 
        {
            @Override
            public void handle(long now) 
            {
                movePlayer();   
                playerCollision();
                if(checkGameOver() == true)
                {
                    window.close();
                }
                mBPx+=mBVx;
                mBPy+=mBVy;
                mBall.relocate(mBPx,mBPy);

                wallCollision();
            }
        };
        timer.start();               
    }

    public void movePlayer()
    {
        if(mAPressed == true)
        {
            mRPx -= mSpeed;
        }
        if(mDPressed == true)
        {
            mRPx += mSpeed;
        }
        mPlayer.relocate(mRPx, PLAYERY);
    }

    public void playerCollision()
    {
        if(mBall.getBoundsInParent().intersects(mPlayer.getBoundsInParent()))
        {
            mBVy*=-1;
        }
    }

    public boolean checkGameOver()
    {
        if(mBall.getBoundsInParent().intersects(mGameOver.getBoundsInParent()))
        {
            return true;
        }
        return false;
    }

    public void wallCollision()
    {
        if(mBPx+BALLRADIUS>=SCREENW || mBPx-BALLRADIUS<=0)
        {
            mBVx*=-1;
        }
        if(mBPy+BALLRADIUS>=SCREENH || mBPy-BALLRADIUS<=0)
        {
            mBVy*=-1;
        }
    }

    public static void main(String[] args) 
    {
        launch();
    }
}
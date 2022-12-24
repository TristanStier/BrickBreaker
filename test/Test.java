import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class Test extends Application
{
    Circle mBall = new Circle(40, Color.RED);
    Rectangle mRectangle = new Rectangle(50,100,Color.BLACK);
    boolean mWPressed = false;
    boolean mAPressed = false;
    boolean mSPressed = false;
    boolean mDPressed = false;
    int mPy = 250;
    int mPx = 250;
    int speed = 2;
    long mLastTime = 0;

    @Override
    public void start(Stage window) 
    {
    	Pane canvas = new Pane();
    	Scene scene = new Scene(canvas, 1000, 1000);

        scene.setOnKeyPressed(e ->
        {
            if(e.getCode() == KeyCode.W)
            {
                mWPressed = true;
            }
            if(e.getCode() == KeyCode.A)
            {
                mAPressed = true;
            }
            if(e.getCode() == KeyCode.S)
            {
                mSPressed = true;
            }
            if(e.getCode() == KeyCode.D)
            {
                mDPressed = true;
            }
        });

        scene.setOnKeyReleased(e -> 
        {
            if(e.getCode() == KeyCode.W)
            {
                mWPressed = false;
            }
            if(e.getCode() == KeyCode.A)
            {
                mAPressed = false;
            }
            if(e.getCode() == KeyCode.S)
            {
                mSPressed = false;
            }
            if(e.getCode() == KeyCode.D)
            {
                mDPressed = false;
            }
        });  
        mRectangle.relocate(500,500);
        canvas.getChildren().addAll(mBall, mRectangle);
        window.setTitle("Moving Ball");
        window.setScene(scene);
        window.show();

        AnimationTimer timer = new AnimationTimer() 
        {
            @Override
            public void handle(long now) 
            {
                double lDt = (now - mLastTime)/1000000000.0;
                Test.this.moveBall(lDt);
                checkBounds(mBall, mRectangle);
                mLastTime = now;
           }
        };
        timer.start();               
    }
    
    protected boolean checkBounds(Circle s1, Shape s2)
    {
        if(s1.getBoundsInParent().intersects(s2.getBoundsInParent()))
        {
            System.out.println("Colision detected");
            return true;
        }
        return false;
    }

    protected void moveBall(double iDt)
    {
        if(mWPressed == true)
        {
            mPy -= speed;
        }
        if(mAPressed == true)
        {
            mPx -= speed;
        }
        if(mSPressed == true)
        {
            mPy += speed;
        }
        if(mDPressed == true)
        {
            mPx += speed;
        }
        mBall.relocate(mPx, mPy);
    }
    public static void main(String[] args) {
        launch();
    }
}
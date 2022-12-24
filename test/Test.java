import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class Test extends Application
{
    Circle mBall = new Circle(10, Color.RED);
    double mVx = 0;
    double mVy = 0;
    double mPx = 0;
    double mPy = 0;


    long mLastTime = 0;

    @Override
    public void start(Stage stage) {
    	Pane canvas = new Pane();
    	Scene scene = new Scene(canvas, 300, 300);

        scene.setOnKeyPressed(e ->
        {
            if(e.getCode() == KeyCode.W)
            {
                System.out.println(mVx);
                mVx += 0.1;
            }
            if(e.getCode() == KeyCode.A)
            {
            }
            if(e.getCode() == KeyCode.S)
            {
            }
            if(e.getCode() == KeyCode.D)
            {
            }
        });

        scene.setOnKeyReleased(e -> 
        {
            if(e.getCode() == KeyCode.W)
            {
            }
            if(e.getCode() == KeyCode.A)
            {
            }
            if(e.getCode() == KeyCode.S)
            {
            }
            if(e.getCode() == KeyCode.D)
            {
            }
        });

        mBall.relocate(0, 10);
        
        canvas.getChildren().add(mBall);
        
        stage.setTitle("Moving Ball");
        stage.setScene(scene);
        stage.show();
         /* 
        Bounds bounds = canvas.getBoundsInLocal();
       
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), 
                new KeyValue(ball.layoutXProperty(), bounds.getMaxX()-ball.getRadius())));
        timeline.setCycleCount(2);
        timeline.play();
        */

        AnimationTimer timer = new AnimationTimer() 
        {
            @Override
            public void handle(long now) 
            {
                double lDt = (now - mLastTime)/1000000000.0;
                Test.this.moveBall(lDt);
                mLastTime = now;
           }
        };

        timer.start();       
         
    }
    
    protected void moveBall(double iDt)
    {
        mPx = mPx + mVx*iDt;
        mPy = mPy + mVy*iDt;
        mBall.relocate(mPx, mPy );
        //System.out.println(iDt);
    }
    public static void main(String[] args) {
        launch();
    }
}
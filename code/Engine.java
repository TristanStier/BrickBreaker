import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Engine extends Application
{
    private static int SCREENW = 1000;
    private static int SCREENH = 1000;

    private int mNumTilesW = 5;
    private int mNumTilesH = 3;
    private Tile[][] mTiles = new Tile[mNumTilesW+1][mNumTilesH+1];

    private Player mPlayer = new Player(900, 15);
    private Ball mBall = new Ball(10, 5, SCREENW/2, 750, 20, Color.RED);

    private Pane mCanvas = new Pane();
    private Scene mScene = new Scene(mCanvas, SCREENW, SCREENH);

    @Override
    public void start(Stage window) 
    {
        mPlayer.getRectangle().relocate(500,500);
        createTiles();
        for(int row = 1; row<=mNumTilesW; row++)
        {
            for(int column = 1; column<=mNumTilesH; column++)
            {
                mCanvas.getChildren().add(mTiles[row][column].getRectangle());
            }
        }
        mCanvas.getChildren().addAll(mBall.getCircle(), mPlayer.getRectangle());
        window.setTitle("Moving Ball");
        window.setScene(mScene);
        window.show();

        mScene.setOnKeyPressed(e -> mPlayer.playerKeyPresssed(e));
        mScene.setOnKeyReleased(e -> mPlayer.playerKeyReleased(e));

        AnimationTimer timer = new AnimationTimer() 
        {
            @Override
            public void handle(long now) 
            {
                playerCollision();
                tileCollision();
                wallCollision();
                mPlayer.movePlayer();
                mBall.updateBall();
            }
        };
        timer.start();  
    }

    public void playerCollision()
    {
        if(mBall.getCircle().getBoundsInParent().intersects(mPlayer.getRectangle().getBoundsInParent()))
        {
            mBall.setVelY(mBall.getVelY()*-1);
        }
    }

    public void wallCollision()
    {
        if(mBall.getPositionX()+mBall.getRadius()>=SCREENW || mBall.getPositionX()-mBall.getRadius()<=0)
        {
            mBall.setVelX(mBall.getVelX()*-1);
        }
        if(mBall.getPositionY()+mBall.getRadius()>=SCREENH || mBall.getPositionY()-mBall.getRadius()<=0)
        {
            mBall.setVelY(mBall.getVelY()*-1);
        }
    }

    public void createTiles()
    {
        for(int row = 1; row<=mNumTilesW; row++)
        {
            for(int column = 1; column<=mNumTilesH; column++)
            {
                int lBuffer = 500;
                int lPosX = ((SCREENW-(SCREENW/mNumTilesW))/mNumTilesW)*row;
                int lPosY = ((SCREENH-lBuffer)/mNumTilesH)*column;
                mTiles[row][column] = new Tile(lPosX, lPosY, 100, 25, Color.BLACK);
            }
        }
    }

    public void tileCollision()
    {
        for(int row = 1; row<=mNumTilesW; row++)
        {
            for(int column = 1; column<=mNumTilesH; column++)
            {
                if(mTiles[row][column].getAlive() == true && mBall.getCircle().getBoundsInParent().intersects(mTiles[row][column].getRectangle().getBoundsInParent()))
                {
                    mBall.setVelY(mBall.getVelY()*-1);
                    mCanvas.getChildren().remove(mTiles[row][column].getRectangle());
                    mTiles[row][column].setAlive(false);
                }
            }
        }
    }

    public static void main(String[] args) 
    {
        launch();
    }
}

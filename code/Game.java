import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Game 
{
    private static int SCREENW = 1000;
    private static int SCREENH = 1000;
    
    private int mLives = 3;
    private Label mLivesText;

    private int mPoints = 0;
    private Label mPointsText;

    private Button mMenuButton;
    private Button mPauseButton;
    boolean mPaused = false;

    private double mVelMultiplier = 1.2;
    private double mBallVelX = 10;
    private double mBallVelY = 6;

    private int mNumTilesW = 5;
    private int mNumTilesH = 3;
    private Tile[][] mTiles = new Tile[mNumTilesW+1][mNumTilesH+1];

    private Player mPlayer = new Player(900, 25);
    private Ball mBall = new Ball(mBallVelX*mVelMultiplier, mBallVelY*mVelMultiplier, SCREENW/2, 750, 20, Color.RED);
    private Rectangle mEndGame= new Rectangle(0, SCREENH, SCREENW, 10);

    private Pane mCanvas = new Pane();
    private Scene mScene = new Scene(mCanvas, SCREENW, SCREENH);

    private AnimationTimer mTimer;

    public Game(Engine iEngine)
    {
        createTiles();
        viewTiles();

        mLivesText = new Label("Lives: " + mLives);
        mLivesText.relocate(75, 50);
        mLivesText.setFont(new Font("Impact", 48));
        mLivesText.setVisible(true);
        
        mPointsText = new Label("Points: " + mPoints);
        mPointsText.relocate(250, 50);
        mPointsText.setFont(new Font("Impact", 48));
        mPointsText.setVisible(true);

        mMenuButton = new Button("Menu");
        mMenuButton.relocate(700, 50);
        mMenuButton.setMinWidth(120);
        mMenuButton.setMinHeight(50);
        mMenuButton.setOnAction(e -> iEngine.showMenu());

        mPauseButton = new Button("Pause");
        mPauseButton.relocate(825, 50);
        mPauseButton.setMinWidth(120);
        mPauseButton.setMinHeight(50);
        mPauseButton.setOnAction(e -> 
        {
            if(mPaused == false)
            {
                mTimer.stop();
                mPauseButton.setText("Unpause");
                mPaused = true;
            } 
            else
            {
                mTimer.start();
                mPauseButton.setText("Pause");
                mPaused = false;
            }
        });

        mCanvas.getChildren().addAll(mBall.getCircle(), mPlayer.getRectangle(), mEndGame, mLivesText, mPointsText, mMenuButton, mPauseButton);

        mScene.setOnKeyPressed(e -> mPlayer.playerKeyPresssed(e));
        mScene.setOnKeyReleased(e -> mPlayer.playerKeyReleased(e));

        mTimer = new AnimationTimer() 
        {
            @Override
            public void handle(long now) 
            {
                endGame(iEngine);
                playerCollision();
                tileCollision();
                wallCollision();
                mPlayer.movePlayer(SCREENW);
                mBall.updateBall();
            }
        };
    }

    public void start()
    {
        mTimer.start();
    }

    public void stop()
    {
        mTimer.stop();
    }

    public int getPoints()
    {
        return mPoints;
    }

    public void reset()
    {
        for(int row = 1; row<=mNumTilesW; row++)
        {
            for(int column = 1; column<=mNumTilesH; column++)
            {
                mCanvas.getChildren().remove(mTiles[row][column].getRectangle());
                mTiles[row][column] = null;
            }
        }
        createTiles();
        mPlayer.setPosition(SCREENW/2);
        mBall.setPosition(SCREENW/2, 750);
        mBall.setVelX(mBallVelX*mVelMultiplier);
        mBall.setVelY(-1*mBallVelY*mVelMultiplier);
        mLives = 3;
        mPauseButton.setText("Pause");
        mPaused = false;
        mVelMultiplier = 1.2;
        mLivesText.setText("Lives:" + mLives);
        mPoints = 0;
        mPointsText.setText("Points: " + mPoints);        
        viewTiles();
    }

    public void nextLevel(double iVelMultiplier)
    {
        mVelMultiplier*=iVelMultiplier;
        for(int row = 1; row<=mNumTilesW; row++)
        {
            for(int column = 1; column<=mNumTilesH; column++)
            {
                mCanvas.getChildren().remove(mTiles[row][column].getRectangle());
                mTiles[row][column] = null;
            }
        }
        createTiles();
        mPlayer.setPosition(SCREENW/2);
        mBall.setPosition(SCREENW/2, 750);
        mBall.setVelX(mBallVelX*mVelMultiplier);
        mBall.setVelY(-1*mBallVelY*mVelMultiplier);        
        viewTiles();
    }

    public void viewTiles()
    {
        for(int row = 1; row<=mNumTilesW; row++)
        {
            for(int column = 1; column<=mNumTilesH; column++)
            {
                mCanvas.getChildren().add(mTiles[row][column].getRectangle());
            }
        }
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

    public void endGame(Engine iEngine)
    {
        if(mBall.getCircle().getBoundsInParent().intersects(mEndGame.getBoundsInParent()))
        {
            mLives-=1;
            if(mLives==0)
            {
                try (BufferedWriter lWriter = new BufferedWriter(new FileWriter("points.txt"))) 
                {
                    lWriter.write(mPoints+"");
                    lWriter.close();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
                iEngine.getHighscore().updateHighscore();
                iEngine.showMenu();
            }
            else
            {
                stop();
                mPlayer.setPosition(SCREENW/2);
                mBall.setPosition(SCREENW/2, 750);
                mBall.setVelX(mBallVelX*mVelMultiplier);
                mBall.setVelY(-1*mBallVelY*mVelMultiplier);
                mLivesText.setText("Lives: " + mLives);
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                start();
            }
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

    public void checkWin()
    {
        boolean lWin = true;
        for(int row = 1; row<=mNumTilesW; row++)
        {
            for(int column = 1; column<=mNumTilesH; column++)
            {
                if(mTiles[row][column].getAlive() == true)
                {
                    lWin = false;
                }
            }
        }
        if(lWin == true)
        {
            nextLevel(1.25);
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
                    mPoints+=5;
                    mPointsText.setText("Points: " + mPoints);   
                    checkWin();     
                }
            }
        }
    }

    Scene getScene()
    {
        return mScene;
    }
}

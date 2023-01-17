import java.io.BufferedWriter;
import javafx.scene.media.*;
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

    private double mVelMultiplier = 1.5;
    private double mBallVelX = 10;
    private double mBallVelY = 6;

    private int mNumTilesW = 5;
    private int mNumTilesH = 4;
    private Tile[][] mTiles = new Tile[mNumTilesW+1][mNumTilesH+1];
    private double mTileDifficulty = 1;
    
    private int mTileXM = 100;

    private Player mPlayer = new Player(900, 20);
    private double mCurrentVel = 0;
    private double mPastPos = mPlayer.getPosition();

    private Ball mBall = new Ball(mBallVelX*mVelMultiplier, mBallVelY*mVelMultiplier, SCREENW/2, 750, 20, Color.RED);
    private Rectangle mEndGame= new Rectangle(0, SCREENH, SCREENW, 10);

    private Pane mCanvas = new Pane();
    private Scene mScene = new Scene(mCanvas, SCREENW, SCREENH);

    private AnimationTimer mTimer;
    
    public Game(Engine iEngine)
    {
        createTiles(Math.random()+0.3, mTileXM, Math.random()*16+20, 70, 1);
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
        mMenuButton.setOnAction(e -> 
        {
            iEngine.showMenu();
            iEngine.getHighscore().updateHighscore();
        });

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
                nextLevel(1, 1.003);
            } 
            else
            {
                mTimer.start();
                mPauseButton.setText("Pause");
                mPaused = false;
            }
        });

        mCanvas.getChildren().addAll(mBall.getCircle(), mPlayer.getRectangle(), mPlayer.getFixerL(), mPlayer.getFixerR(), mEndGame, mLivesText, mPointsText, mMenuButton, mPauseButton);

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
                
                mCurrentVel = mPlayer.getPosition()-mPastPos;
                mPastPos = mPlayer.getPosition();
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
                mCanvas.getChildren().removeAll(mTiles[row][column].getRectangle(), mTiles[row][column].getFixerR(), mTiles[row][column].getFixerL(), mTiles[row][column].getHitsText());
                mTiles[row][column] = null;
            }
        }
        createTiles(Math.random()+0.3, mTileXM, Math.random()*16+20, 70, 1);
        mPlayer.setPosition(SCREENW/2);
        mBall.setPosition(SCREENW/2, 750);
        mBall.setVelX(mBallVelX*mVelMultiplier);
        mBall.setVelY(-1*mBallVelY*mVelMultiplier);
        mPlayer.resetKeyValues();
        mLives = 3;
        mTileDifficulty = 1;
        mPauseButton.setText("Pause");
        mPaused = false;
        mVelMultiplier = 1.2;
        mPlayer.setSpeed(25);
        mLivesText.setText("Lives:" + mLives);
        mPoints = 0;
        mPointsText.setText("Points: " + mPoints);        
        viewTiles();
    }

    public void nextLevel(double iVelMultiplier, double iDifficultyMultiplier)
    {
        mVelMultiplier*=iVelMultiplier;
        for(int row = 1; row<=mNumTilesW; row++)
        {
            for(int column = 1; column<=mNumTilesH; column++)
            {
                mCanvas.getChildren().removeAll(mTiles[row][column].getRectangle(), mTiles[row][column].getFixerR(), mTiles[row][column].getFixerL(), mTiles[row][column].getHitsText());
                mTiles[row][column] = null;
            }
        }
        createTiles(Math.random()+0.3, mTileXM, Math.random()*16+20, 70, iDifficultyMultiplier);
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
                mCanvas.getChildren().addAll(mTiles[row][column].getRectangle(), mTiles[row][column].getFixerR(), mTiles[row][column].getFixerL(), mTiles[row][column].getHitsText());
            }
        }
    }

    public void playerCollision()
    {
        if(mBall.getCircle().getBoundsInParent().intersects(mPlayer.getRectangle().getBoundsInParent()))
        {
            mBall.setVelY(mBall.getVelY()*-1);
            mBall.setVelX((mBall.getVelX()+mCurrentVel)/2);
        }
        if(mBall.getCircle().getBoundsInParent().intersects(mPlayer.getFixerL().getBoundsInParent()) && mBall.getVelX()<0)
        {
            mBall.setVelX(mBall.getVelX()*-1);
        }
        if(mBall.getCircle().getBoundsInParent().intersects(mPlayer.getFixerR().getBoundsInParent()) && mBall.getVelX()>0)
        {
            mBall.setVelX(mBall.getVelX()*-1);
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

    public void createTiles(double Percentage, double sizeXM, double sizeY, double yDM, double difficultyMultiplier)
    {
        int lBuffer = 500;
        for(int row = 1; row<=mNumTilesW; row++)
        {
            for(int column = 1; column<=mNumTilesH; column++)
            {
                double lYDeviation = Math.random()*yDM;
                double lSizeXM = Math.random()*sizeXM+40;
                int lPosX = ((SCREENW-(SCREENW/mNumTilesW))/mNumTilesW)*row;
                int lPosY = ((SCREENH-lBuffer)/mNumTilesH)*column;
                mTileDifficulty *= difficultyMultiplier;
                double randomValue = Math.random()*mTileDifficulty;
                if(randomValue>1.5)
                {
                    mTiles[row][column] = new Tile(lPosX, lPosY+(int)lYDeviation, (int)lSizeXM, (int)sizeY, Color.BLACK, 8);                    
                }
                else if(randomValue>0.95)
                {
                    mTiles[row][column] = new Tile(lPosX, lPosY+(int)lYDeviation, (int)lSizeXM, (int)sizeY, Color.BLUE, 3);
                }
                else if(randomValue>0.65)
                {
                    mTiles[row][column] = new Tile(lPosX, lPosY+(int)lYDeviation, (int)lSizeXM, (int)sizeY, Color.RED, 2);
                }
                else
                {
                    mTiles[row][column] = new Tile(lPosX, lPosY+(int)lYDeviation, (int)lSizeXM, (int)sizeY, Color.GREEN, 1);
                }
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
            nextLevel(1.1, 1.003);
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
                    if(mTiles[row][column].getHits()<=1)
                    {
                        mCanvas.getChildren().removeAll(mTiles[row][column].getRectangle(), mTiles[row][column].getFixerR(), mTiles[row][column].getFixerL(), mTiles[row][column].getHitsText());
                        mTiles[row][column].setAlive(false);
                        mPoints+=5;
                        mPointsText.setText("Points: " + mPoints);   
                        checkWin();  
                    }
                    else
                    {
                        mTiles[row][column].setHits(mTiles[row][column].getHits()-1);
                        mTiles[row][column].getHitsText().setText("" + mTiles[row][column].getHits());
                    }
                }
                if(mTiles[row][column].getAlive() == true && (mBall.getCircle().getBoundsInParent().intersects(mTiles[row][column].getFixerL().getBoundsInParent()) || mBall.getCircle().getBoundsInParent().intersects(mTiles[row][column].getFixerR().getBoundsInParent())))
                {
                    mBall.setVelX(mBall.getVelX()*-1);
                    if(mTiles[row][column].getHits()<=1)
                    {
                        mCanvas.getChildren().removeAll(mTiles[row][column].getRectangle(), mTiles[row][column].getFixerR(), mTiles[row][column].getFixerL(), mTiles[row][column].getHitsText());
                        mTiles[row][column].setAlive(false);
                        mPoints+=5;
                        mPointsText.setText("Points: " + mPoints);   
                        checkWin();  
                    }
                    else
                    {
                        mTiles[row][column].setHits(mTiles[row][column].getHits()-1);
                        mTiles[row][column].getHitsText().setText("" + mTiles[row][column].getHits());
                    }
                }
            }
        }
    }

    Scene getScene()
    {
        return mScene;
    }
}

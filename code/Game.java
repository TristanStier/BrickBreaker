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
    // Screen
    private int mScreenW = 1000;
    private int mScreenH = 1000;
    private Pane mCanvas = new Pane();
    private Scene mScene = new Scene(mCanvas, mScreenW, mScreenH);
    
    private int mLives = 3;
    private Label mLivesText;

    private int mPoints = 0;
    private Label mPointsText;

    private Button mMenuButton;
    private Button mPauseButton;
    boolean mPaused = false;

    private double mVelMultiplier = 1.5;
    private double mBallVelX = 6;
    private double mBallVelY = 6;

    private int mNumTilesW = 5;
    private int mNumTilesH = 4;
    private Tile[][] mTiles = new Tile[mNumTilesW+1][mNumTilesH+1];
    private double mTileDifficulty = 1;
    private int mTileXM = 100;

    // Player
    private Player mPlayer = new Player(900, 20);
    private double mCurrentVel = 0;
    private double mPastPos = mPlayer.getPosition();

    // Ball
    private Ball mBall = new Ball(mBallVelX*mVelMultiplier, mBallVelY*mVelMultiplier, mScreenW/2, 750, 20, Color.RED);
    
    private Rectangle mEndGame= new Rectangle(0, mScreenH, mScreenW, 10);
    
    private AnimationTimer mTimer;
    
    public Game(Engine iEngine)
    {
        createTiles(mTileXM, Math.random()*16+20, 70, 1);
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
                mPlayer.movePlayer(mScreenW);
                mBall.updateBall();
                
                mCurrentVel = mPlayer.getPosition()-mPastPos;
                mPastPos = mPlayer.getPosition();
            }
        };
    }

    /**
     * Start the animation timer
     */
    public void start()
    {
        mTimer.start();
    }

    /**
     * Stop the animation timer
     */
    public void stop()
    {
        mTimer.stop();
    }
    
    /**
     * Gets the current points
     * @return Return the points
     */
    public int getPoints()
    {
        return mPoints;
    }
    
    /**
     * Reset the entire game 
     */
    public void reset()
    {
        mTileDifficulty = 1;
        for(int row = 1; row<=mNumTilesW; row++)
        {
            for(int column = 1; column<=mNumTilesH; column++)
            {
                mCanvas.getChildren().removeAll(mTiles[row][column].getRectangle(), mTiles[row][column].getFixerR(), mTiles[row][column].getFixerL(), mTiles[row][column].getHitsText());
                mTiles[row][column] = null;
            }
        }
        createTiles(mTileXM, Math.random()*16+20, 70, 1);
        viewTiles();

        mVelMultiplier = 1.2;
        mBall.setPosition(mScreenW/2, 750);
        mBall.setVelX(mBallVelX*mVelMultiplier);
        mBall.setVelY(-1*mBallVelY*mVelMultiplier);
        
        mPlayer.resetKeyValues();
        mPlayer.setSpeed(25);
        mPlayer.setPosition(mScreenW/2);
        
        mLives = 3;
        mLivesText.setText("Lives:" + mLives);
        
        mPauseButton.setText("Pause");
        mPaused = false;
        
        mPoints = 0;
        mPointsText.setText("Points: " + mPoints);        
    }

    /**
     * Proceeds to next level of game
     * @param iVelMultiplier Multiplier of ball speed
     * @param iDifficultyMultiplier Multiplier chance of harder blocks to appear
     */
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
        createTiles(mTileXM, Math.random()*16+20, 70, iDifficultyMultiplier);
        viewTiles();

        mPlayer.setPosition(mScreenW/2);
        
        mBall.setPosition(mScreenW/2, 750);
        mBall.setVelX(mBallVelX*mVelMultiplier);
        mBall.setVelY(-1*mBallVelY*mVelMultiplier);   
        
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Check ball collision with player
     */
    public void playerCollision()
    {
        if(mBall.getCircle().getBoundsInParent().intersects(mPlayer.getRectangle().getBoundsInParent()))
        {
            mBall.setVelY(mBall.getVelY()*-1);
            if(mCurrentVel!=0)
            {
                mBall.setVelX((mBall.getVelX()+mCurrentVel)/2);
            }
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

    /**
     * Check ball collision with walls
     */
    public void wallCollision()
    {
        if(mBall.getPositionX()+mBall.getRadius()>=mScreenW || mBall.getPositionX()-mBall.getRadius()<=0)
        {
            mBall.setVelX(mBall.getVelX()*-1);
        }
        if(mBall.getPositionY()+mBall.getRadius()>=mScreenH || mBall.getPositionY()-mBall.getRadius()<=0)
        {
            mBall.setVelY(mBall.getVelY()*-1);
        }
    }

    /**
     * Check ball collision with tiles
     */
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
    
    /**
     * Check ball collision with end game rectangle
     * @param iEngine Engine to show the menu
     */
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
                mPlayer.setPosition(mScreenW/2);
                mBall.setPosition(mScreenW/2, 750);
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

    /**
     * Fills the tiles array
     * @param sizeXM X multiplier of tile random X size
     * @param sizeY Y multiplier of tile random Y size
     * @param yDM Y deviation multiplier
     * @param difficultyMultiplier Difficulty of tile multiplier
     */
    public void createTiles(double sizeXM, double sizeY, double yDM, double difficultyMultiplier)
    {
        int lBuffer = 500;
        for(int row = 1; row<=mNumTilesW; row++)
        {
            for(int column = 1; column<=mNumTilesH; column++)
            {
                double lYDeviation = Math.random()*yDM;
                double lSizeXM = Math.random()*sizeXM+40;
                int lPosX = ((mScreenW-(mScreenW/mNumTilesW))/mNumTilesW)*row;
                int lPosY = ((mScreenH-lBuffer)/mNumTilesH)*column;
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

    /**
     * Adds tiles to canvas
     */
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

    /**
     * Check if tiles are remaining
     */
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

    /**
     * Get game scene
     * @return Returns game scene
     */
    Scene getScene()
    {
        return mScene;
    }
}

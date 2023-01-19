import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Player
{
    //! MAKE SURE U MAKE THE CONSUTRUCTOR THING WHERE THEY DONT INPOUT ANYTHING OR WTVR
    private int PLAYERY;
    private Rectangle mPlayer = new Rectangle(150, 10, Color.BLACK);
    private Rectangle mFixerL, mFixerR;
    private boolean mAPressed = false;
    private boolean mDPressed = false;
    private double mSpeed;
    private double mX = 500;    

    public Player(int playerY, double speed)
    {
        PLAYERY = playerY;
        mSpeed = speed;
        mFixerL = new Rectangle(10, 11, Color.RED);
        mFixerR = new Rectangle(10, 11, Color.RED);
        mFixerL.setVisible(false);
        mFixerR.setVisible(false);
    }

    public Player()
    {
        this(900, 25);
    }

    /**
     * Get player rectangle
     * @return Return player rectangle
     */
    public Rectangle getRectangle()
    {
        return mPlayer;
    }

    /**
     * Get right fixer
     * @return Returns right fixer
     */
    public Rectangle getFixerR()
    {
        return mFixerR;
    }
    
    /**
     * Get left fixer
     * @return Returns left fixer
     */
    public Rectangle getFixerL()
    {
        return mFixerL;      
    }
    
    /**
     * Get position X
     * @return Returns position X
     */
    public double getPosition()
    {
        return mX;
    }

    /**
     * Set the x position of player
     * @param posX X position to set
     */
    public void setPosition(double iPosX)
    {
        mX = iPosX;
    }

    /**
     * Set the speed of player
     * @param speed Speed to set
     */
    public void setSpeed(double iSpeed)
    {
        mSpeed = iSpeed;
    }

    /**
     * Handles player movement when key pressed
     * @param e Key event to check
     */
    public void playerKeyPresssed(KeyEvent e)
    {
        if(e.getCode() == KeyCode.A)
        {
            mAPressed = true;
        }
        if(e.getCode() == KeyCode.D)
        {
            mDPressed = true;
        }
    }

    /**
     * Handles player movement when key released
     * @param e Key event to check
     */
    public void playerKeyReleased(KeyEvent e)
    {
        if(e.getCode() == KeyCode.A)
        {
            mAPressed = false;
        }
        if(e.getCode() == KeyCode.D)
        {
            mDPressed = false;
        }
    }
 
    /**
     * Moves the player
     * @param iScreenW Screen width
     */
    public void movePlayer(int iScreenW)
    {
        if(mAPressed == true && mX > 0)
        {
            mX -= mSpeed;
        }
        if(mDPressed == true && mX < iScreenW-150)
        {
            mX += mSpeed;
        }
        mPlayer.relocate(mX, PLAYERY);
        mFixerR.relocate(mX-10, PLAYERY+3);
        mFixerL.relocate(mX+150, PLAYERY+3);
    }

    /**
     * Resets player key values
     */
    public void resetKeyValues()
    {
        mAPressed = false;
        mDPressed = false;
    }
}

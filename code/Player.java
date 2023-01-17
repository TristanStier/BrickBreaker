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

    public Rectangle getRectangle()
    {
        return mPlayer;
    }

    public Rectangle getFixerR()
    {
        return mFixerR;
    }
    
    public Rectangle getFixerL()
    {
        return mFixerL;      
    }
    
    public double getPosition()
    {
        return mX;
    }

    public void setPosition(double posX)
    {
        mX = posX;
    }

    public void setSpeed(double speed)
    {
        mSpeed = speed;
    }

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
 
    public void movePlayer(int w)
    {
        if(mAPressed == true && mX > 0)
        {
            mX -= mSpeed;
        }
        if(mDPressed == true && mX < w-150)
        {
            mX += mSpeed;
        }
        mPlayer.relocate(mX, PLAYERY);
        mFixerR.relocate(mX-10, PLAYERY+3);
        mFixerL.relocate(mX+150, PLAYERY+3);
    }

    public void resetKeyValues()
    {
        mAPressed = false;
        mDPressed = false;
    }
}










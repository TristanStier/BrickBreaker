import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Player
{
    private int PLAYERY;
    private Rectangle mPlayer = new Rectangle(150, 25, Color.BLACK);
    private boolean mAPressed = false;
    private boolean mDPressed = false;
    private double mSpeed;
    private double mX = 300;    

    public Player(int playerY, double speed)
    {
        PLAYERY = playerY;
        mSpeed = speed;
    }

    public Rectangle getRectangle()
    {
        return mPlayer;
    }

    public double getPosition()
    {
        return mX;
    }

    public void setPosition(double posX)
    {
        mX = posX;
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
 
    public void movePlayer()
    {
        if(mAPressed == true)
        {
            mX -= mSpeed;
        }
        if(mDPressed == true)
        {
            mX += mSpeed;
        }
        mPlayer.relocate(mX, PLAYERY);
    }
}

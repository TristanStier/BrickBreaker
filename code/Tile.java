import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile 
{
    private int mPosX;
    private int mPosY;
    private boolean mAlive = true;
    private Rectangle mTile;

    public Tile(int posX, int posY, int sizeX, int sizeY, Color color)
    {
        mPosX = posX;
        mPosY = posY;
        mTile = new Rectangle(sizeX, sizeY, color);
        mTile.relocate(posX, posY);
    }

    public int getPosX()
    {
        return mPosX;
    }

    public void setPosX(int posX)
    {
        mPosX = posX;
    }

    public int getPosY()
    {
        return mPosY;
    }

    public void setPosY(int posY)
    {
        mPosY = posY;
    }

    public boolean getAlive()
    {
        return mAlive;
    }

    public void setAlive(boolean alive)
    {
        mAlive = alive;
    }
    
    public Rectangle getRectangle()
    {
        return mTile;
    }
}

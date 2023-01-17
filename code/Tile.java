import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile 
{
    private int mX;
    private int mY;
    private boolean mAlive = true;
    private Rectangle mTile;
    private Rectangle mFixerL, mFixerR;

    public Tile(int posX, int posY, int sizeX, int sizeY, Color color)
    {
        mX = posX;
        mY = posY;
        mTile = new Rectangle(sizeX, sizeY, color);
        mTile.relocate(posX, posY);
        mFixerL = new Rectangle(10, sizeY, Color.RED);
        mFixerL.relocate(posX-10, posY);
        mFixerL.setVisible(false);
        mFixerR = new Rectangle(10, sizeY, Color.RED);
        mFixerR.relocate(posX+sizeX, posY);
        mFixerR.setVisible(false);
    }

    public Tile(int posX, int posY)
    {
        this(posX, posY, 100, 25, Color.BLACK);
    }

    public int getPosX()
    {
        return mX;
    }

    public void setPosX(int posX)
    {
        mX = posX;
    }

    public int getPosY()
    {
        return mY;
    }

    public void setPosY(int posY)
    {
        mY = posY;
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
    
    public Rectangle getFixerR()
    {
        return mFixerR;
    }
    
    public Rectangle getFixerL()
    {
        return mFixerL;      
    }
}

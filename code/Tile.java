import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile 
{
    private int mX;
    private int mY;
    private boolean mAlive = true;
    private Rectangle mTile;
    private Rectangle mFixerL, mFixerR;
    private int mHits;
    private Text mHitsText;

    public Tile(int posX, int posY, int sizeX, int sizeY, Color color, int hits)
    {
        mX = posX;
        mY = posY;
        mHits = hits;
        mTile = new Rectangle(sizeX, sizeY, color);
        mTile.relocate(posX, posY);
        mFixerL = new Rectangle(10, sizeY-2, Color.RED);
        mFixerL.relocate(posX-10, posY+1);
        mFixerL.setVisible(false);
        mFixerR = new Rectangle(10, sizeY-2, Color.RED);
        mFixerR.relocate(posX+sizeX, posY+1);
        mFixerR.setVisible(false);

        mHitsText = new Text("" + hits);
        mHitsText.relocate(posX+(sizeX/2), posY+(sizeY/3)); // Sometimes off centre but this is the best I can do
        mHitsText.setFill(Color.WHITE);
        mHitsText.setFont(new Font("Impact", sizeY/2+3)); 
    }

    public Tile(int posX, int posY)
    {
        this(posX, posY, 100, 25, Color.BLACK, 1);
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

    public Text getHitsText()
    {
        return mHitsText;
    }

    public void setHits(int hits)
    {
        mHits = hits;
    }

    public int getHits()
    {
        return mHits;
    }
}

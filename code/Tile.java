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
    
    /**
     * Get alive boolean of the tile
     * @return Returns alive boolean
     */
    public boolean getAlive()
    {
        return mAlive;
    }

    /**
     * Sets the alive boolean of the tile
     * @param iAlive Boolean to set
     */
    public void setAlive(boolean iAlive)
    {
        mAlive = iAlive;
    }
    
    /**
     * Gets the rectangle shape of tile
     * @return Returns the rectangle shape
     */
    public Rectangle getRectangle()
    {
        return mTile;
    }
    
    /**
     * Gets the shape of right fixer
     * @return Returns shape of right fixer
     */
    public Rectangle getFixerR()
    {
        return mFixerR;
    }
    
    /**
     * Gets the shape of left fixer
     * @return Returns left fixer
     */
    public Rectangle getFixerL()
    {
        return mFixerL;      
    }

    /**
     * Get hits text
     * @return Returns hits text
     */
    public Text getHitsText()
    {
        return mHitsText;
    }

    /**
     * Sets the hits integer
     * @param iHits 
     */
    public void setHits(int iHits)
    {
        mHits = iHits;
    }

    /**
     * Gets the hits integer
     * @return Returns hits integer
     */
    public int getHits()
    {
        return mHits;
    }
}

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball 
{
    //! MAKE SURE U MAKE THE CONSUTRUCTOR THING WHERE THEY DONT INPOUT ANYTHING OR WTVR
    private Circle mBall;
    private double mRadius;
    private double mVx;
    private double mVy;
    private double mPx;
    private double mPy;

    public Ball(double velX, double velY, double positionX,  double positionY, double radius, Color color)
    {
        mBall = new Circle(radius, color);
        mVx  = velX;
        mVy = velY;
        mPx = positionX;
        mPy= positionY;
        mRadius = radius;
    }

    public Ball()
    {
        this(10.0, 5.0, 500.0, 750.0, 20.0, Color.BLACK);
    }

    /**
     * Get X velocity
     * @return X Velocity of Ball
     */
    public double getVelX()
    {
        return mVx;
    }

    /**
     * Get Y velocity
     * @return Y Velocity of Ball
     */
    public double getVelY()
    {
        return mVy;
    }

    /**
     * Set X velocity
     * @param velX The X velocity to set
     */
    public void setVelX(double velX)
    {
        mVx = velX;
    }

    /**
     * Set Y velocity
     * @param velY The Y velocity to set
     */
    public void setVelY(double velY)
    {
        mVy = velY;
    }

    /**
     * Get X position of Ball
     * @return Returns X value of ball
     */
    public double getPositionX()
    {
        return mPx;
    }

    /**
     * Get Y position of Ball
     * @return Returns Y value of ball
     */
    public double getPositionY()
    {
        return mPy;
    }

    /**
     * Set position of the ball
     * @param x The X position to set
     * @param y The Y position to set
     */
    public void setPosition(double x, double y)
    {
        mPx = x;
        mPy = y;
    }

    /**
     * Gets the radius of the ball
     * @return Returns the radius of ball
     */
    public double getRadius()
    {
        return mRadius;
    }

    /**
     * Get the circle shape of the ball
     * @return Returns the circle shape
     */
    public Circle getCircle()
    {
        return mBall;
    }

    /**
     * Updates the position of the ball
     */
    public void updateBall()
    {
        mPx+=mVx;
        mPy+=mVy;
        mBall.relocate(mPx,mPy);
    }
}

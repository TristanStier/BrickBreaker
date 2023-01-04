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

    public double getVelX()
    {
        return mVx;
    }

    public double getVelY()
    {
        return mVy;
    }

    public void setVelX(double velX)
    {
        mVx = velX;
    }

    public void setVelY(double velY)
    {
        mVy = velY;
    }

    public double getPositionX()
    {
        return mPx;
    }

    public double getPositionY()
    {
        return mPy;
    }

    public void setPosition(double x, double y)
    {
        mPx = x;
        mPy = y;
    }

    public double getRadius()
    {
        return mRadius;
    }

    public Circle getCircle()
    {
        return mBall;
    }

    public void updateBall()
    {
        mPx+=mVx;
        mPy+=mVy;
        mBall.relocate(mPx,mPy);
    }
}

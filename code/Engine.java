import java.io.*;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 
 * @author tstie1
 */
public class Engine extends Application
{
    private Stage mWindow;
    private Menu mMenu;
    private Game mGame;
    private Highscore mHighscore;

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        mWindow = primaryStage;
        mWindow.setX(400);
        mWindow.setY(100);
        mMenu = new Menu(this);
        mGame = new Game(this);
        mHighscore = new Highscore(this);

        mWindow.setScene(mMenu.getScene());
        mWindow.show();
    }

    /**
     * Sets the scene of the current window to the menu scene
     */
    public void showMenu()
    {
        mGame.stop();
        mGame.reset();
        mWindow.setX(400);
        mWindow.setY(100);
        mWindow.setScene(mMenu.getScene());
        mWindow.setTitle("Menu");
    }

    /**
     * Sets the scene of the current window to the game scene
     */
    public void showGame()
    {
        mGame.start();
        mWindow.setScene(mGame.getScene());
        mWindow.setX(100);
        mWindow.setY(0);
        mWindow.setTitle("Game");
    }

    /**
     * Sets the scene of the current window to the high score scene
     */
    public void showHighscore()
    {
        mHighscore.updateHighscore();
        mWindow.setX(400);
        mWindow.setY(100);
        mWindow.setScene(mHighscore.getScene());
        mWindow.setTitle("Highscore");
    }

    /**
     * Gets the high score class
     * @return Returns high score class
     */
    public Highscore getHighscore()
    {
        return mHighscore;
    }

    public static void main(String[] args) 
    {   
        // Check for text files
        File lFH = new File("highscore.txt");
        if(!lFH.exists()) 
        {
            try (BufferedWriter lWriter = new BufferedWriter(new FileWriter("highscore.txt"))) 
            {           
                lWriter.write("0");
                lWriter.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }

        File lFP = new File("points.txt");
        if(!lFP.exists()) 
        {
            try (BufferedWriter lWriter = new BufferedWriter(new FileWriter("points.txt"))) 
            {           
                lWriter.write("0");
                lWriter.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
        
        launch(args);
    }
}

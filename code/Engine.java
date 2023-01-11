import java.io.*;
import javafx.application.Application;
import javafx.stage.Stage;

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

    public void showMenu()
    {
        mGame.stop();
        mGame.reset();
        mWindow.setX(400);
        mWindow.setY(100);
        mWindow.setScene(mMenu.getScene());
        mWindow.setTitle("Menu");
    }

    public void showGame()
    {
        mGame.start();
        mWindow.setScene(mGame.getScene());
        mWindow.setX(100);
        mWindow.setY(0);
        mWindow.setTitle("Game");
    }

    public void showHighscore()
    {
        mHighscore.updateHighscore();
        mWindow.setX(400);
        mWindow.setY(100);
        mWindow.setScene(mHighscore.getScene());
        mWindow.setTitle("Highscore");
    }

    public Highscore getHighscore()
    {
        return mHighscore;
    }

    public static void main(String[] args) 
    {   
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

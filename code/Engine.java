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
        mWindow.setScene(mMenu.getScene());
        mWindow.setTitle("Menu");
    }

    public void showGame()
    {
        mGame.start();
        mWindow.setScene(mGame.getScene());
        mWindow.setTitle("Game");
    }

    public void showHighscore()
    {
        mHighscore.updateHighscore();
        mWindow.setScene(mHighscore.getScene());
        mWindow.setTitle("Highscore");
    }

    public Highscore getHighscore()
    {
        return mHighscore;
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}

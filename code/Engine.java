import javafx.application.Application;
import javafx.stage.Stage;

public class Engine extends Application
{
    private Stage mWindow;
    private Menu mMenu;
    private Game mGame;

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        mWindow = primaryStage;

        mMenu = new Menu(this);
        mGame = new Game(this);

        showMenu();
        mWindow.show();
    }

    void showMenu()
    {
        mWindow.setScene(mMenu.getScene());
        mWindow.setTitle("menu");
    }
    
    void showGame()
    {
        mWindow.setScene(mGame.getScene());
        mGame.start();
        mWindow.setTitle("crazydasd");
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Menu
{
    private Scene mScene;

    public Menu(Engine iEngine) 
    {
        //Scene 1
        Label lLabel1 = new Label("Brick Breaker");
        lLabel1.setFont(new Font("Arial", 24));

        Button lButtonP = new Button("Play");
        lButtonP.setMinWidth(200);
        lButtonP.setMinHeight(60);
        lButtonP.setOnAction(e -> iEngine.showGame());

        Button lButtonH = new Button("Highscore");
        lButtonH.setMinWidth(200);
        lButtonH.setMinHeight(60);
        lButtonH.setOnAction(e -> iEngine.showHighscore());

        VBox lLayout = new VBox(20);
        lLayout.getChildren().addAll(lLabel1, lButtonP, lButtonH);
        lLayout.setAlignment(Pos.CENTER);
        lLayout.setSpacing(60);

        mScene = new Scene(lLayout, 500, 500);
    }

    Scene getScene()
    {
        return mScene;
    }
}

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class Program1 extends Application 
{
    Stage window;
    Scene scene1, scene2;

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        window = primaryStage;
        
        //Scene 1
        Label label1 = new Label("Welcome to the first scene!");
        Button button1 = new Button("Go to scene 2");
        button1.setOnAction(e -> window.setScene(scene2));

        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1);
        layout1.setAlignment(Pos.CENTER);

        scene1 = new Scene(layout1, 200, 200);
        
        //Scene 2
        Button button2 = new Button("This scene sucks, go back to scene 1");
        button2.setOnAction(e -> window.setScene(scene1));

        Button button3 = new Button("Open alert box");
        button3.setOnAction(e -> AlertBox.display("Title thingy", "Wow nice alrert box"));
        
        VBox layout2 = new VBox(20);
        layout2.getChildren().addAll(button2, button3);
        layout2.setAlignment(Pos.CENTER);
        scene2 = new Scene(layout2, 600, 300);

        window.setScene(scene1);
        window.setTitle("crazy code");
        window.show();
    }
}

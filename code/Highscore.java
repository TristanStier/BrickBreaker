import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Highscore 
{
    private Scene mScene;
    private Label mLabel1 = new Label();
    
    public Highscore(Engine iEngine)
    {
        mLabel1.setFont(new Font("Arial", 36));
        
        Button lMenuButton = new Button("Menu");
        lMenuButton.setMinWidth(200);
        lMenuButton.setMinHeight(60);
        lMenuButton.setOnAction(e -> iEngine.showMenu());

        VBox lLayout = new VBox(20);
        lLayout.getChildren().addAll(mLabel1, lMenuButton);
        lLayout.setAlignment(Pos.CENTER);
        lLayout.setSpacing(60);

        mScene = new Scene(lLayout, 500, 500);
    }

    /**
     * Reads and write text files to update high score
     */
    public void updateHighscore()
    {
        String currentHighscore = "";
        String recentPoints = "";

        try (BufferedReader lReader = new BufferedReader(new FileReader("highscore.txt"))) 
        {
            currentHighscore = lReader.readLine();
            lReader.close();
        } 
        catch (IOException e1) 
        {
            e1.printStackTrace();
        }
        try (BufferedReader lReader = new BufferedReader(new FileReader("points.txt"))) 
        {
            recentPoints = lReader.readLine();
            lReader.close();
        } 
        catch (IOException e1) 
        {
            e1.printStackTrace();
        }

        if(Integer.parseInt(recentPoints)>=Integer.parseInt(currentHighscore))
        {
            try (BufferedWriter lWriter = new BufferedWriter(new FileWriter("highscore.txt"))) 
            {           
                lWriter.write(recentPoints);
                mLabel1.setText(recentPoints);
                lWriter.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
        else
        {
            mLabel1.setText("Highscore: " + currentHighscore);
        }
    }

    /**
     * Get high score scene
     * @return Returns high score scene
     */
    public Scene getScene()
    {
        return mScene;
    }
}

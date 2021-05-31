package fr.alternalis.orangefactory;

import fr.alternalis.orangefactory.elements.Parameter;
import fr.alternalis.orangefactory.elements.Processor;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

public class Main extends Application
{
    private final static Long longStart = new Date().getTime();
    private static Stage stage;

    // Chemin de l'icone des fenêtres
    public static String PATH_ICON = "icons/icon.png";

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        Parent root;
        try
        {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
            stage.setTitle("Orange Factory");
            stage.getIcons().add(new Image(PATH_ICON));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            engageFactory();
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void engageFactory(){
       Timer timer = new Timer("FactoryTimer");
       timer.schedule(new Processor(), Parameter.cycleTime.intValue());
    }
}

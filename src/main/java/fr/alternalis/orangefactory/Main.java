package fr.alternalis.orangefactory;

import fr.alternalis.orangefactory.elements.Clock;
import fr.alternalis.orangefactory.elements.Indicator;
import fr.alternalis.orangefactory.elements.Parameter;
import fr.alternalis.orangefactory.elements.Processor;
import fr.alternalis.orangefactory.logger.Logger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;

public class Main extends Application
{

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
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("main.fxml")));
            stage.setTitle("Orange Factory");
            stage.getIcons().add(new Image(PATH_ICON));
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            stage.show();

            engageFactory();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void engageFactory() {
        Logger.openFile();
        Indicator.startTime = System.currentTimeMillis();
        Timer clockTimer = new Timer("ClockTimer");
        clockTimer.schedule(new Clock(), 0,  1000);
        Logger.writeLog("State", "Application", "Start");
        Timer timer = new Timer("FactoryTimer");
        timer.schedule(new Processor(), 0, Parameter.cycleTime.intValue());
    }
}

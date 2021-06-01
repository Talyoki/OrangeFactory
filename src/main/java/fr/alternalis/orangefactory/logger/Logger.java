package fr.alternalis.orangefactory.logger;

import fr.alternalis.orangefactory.elements.Clock;
import fr.alternalis.orangefactory.elements.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private File logFile = null;
    private static FileWriter writer = null;

    public void openFile(){
        try {
            if(logFile != null){
                writer.close();
            }
            logFile = new File(Parameter.logFile);
            if (logFile.createNewFile()) {
                System.out.println("File created: " + logFile.getName());
            } else {
                System.out.println("File already exists.");
            }
            writer = new FileWriter(logFile, true);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeLog(String type, String element, String information){
        try {
            switch (type) {
                case "State":
                    writer.write(Clock.getCurrentExecTime() + " # State # " + "[ " + element + " ] " + information);
                    break;
                case "Info":
                    writer.write(Clock.getCurrentExecTime() + " ? Info ? " + "[ " + element + " ] " + information);
                    break;
                case "Action":
                    writer.write(Clock.getCurrentExecTime() + " > Action < " + "[ " + element + " ] " + information);
                    break;
                case "Event":
                    writer.write(Clock.getCurrentExecTime() + " ! Event ! " + "[ " + element + " ] " + information);
                    break;
                case "Error":
                    writer.write(Clock.getCurrentExecTime() + " < Error > " + "[ " + element + " ] " + information);
                    break;
                case "Result":
                    writer.write(Clock.getCurrentExecTime() + " $ Result $ " + "[ " + element + " ] " + information);
                    break;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

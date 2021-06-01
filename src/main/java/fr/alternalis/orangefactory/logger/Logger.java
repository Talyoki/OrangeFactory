package fr.alternalis.orangefactory.logger;

import fr.alternalis.orangefactory.elements.Clock;
import fr.alternalis.orangefactory.elements.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private static File logFile = null;
    private static FileWriter writer = null;

    public static void openFile(){
        try {
            if(logFile != null){
                writer.close();
            }
            logFile = new File(Parameter.logFile);
            if (logFile.createNewFile()) {
                System.out.println("File created: " + logFile.getName());
            } else {
                if(logFile.delete() && logFile.createNewFile()){
                    System.out.println("File already exists but was deleted.");
                }
                System.out.println("File already exists but was not deleted.");
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
                    writer.write(System.lineSeparator());
                    break;
                case "Info":
                    writer.write(Clock.getCurrentExecTime() + " ? Info ? " + "[ " + element + " ] " + information);
                    writer.write(System.lineSeparator());
                    break;
                case "Action":
                    writer.write(Clock.getCurrentExecTime() + " > Action < " + "[ " + element + " ] " + information);
                    writer.write(System.lineSeparator());
                    break;
                case "Event":
                    writer.write(Clock.getCurrentExecTime() + " ! Event ! " + "[ " + element + " ] " + information);
                    writer.write(System.lineSeparator());
                    break;
                case "Error":
                    writer.write(Clock.getCurrentExecTime() + " < Error > " + "[ " + element + " ] " + information);
                    writer.write(System.lineSeparator());
                    break;
                case "Result":
                    writer.write(Clock.getCurrentExecTime() + " $ Result $ " + "[ " + element + " ] " + information);
                    writer.write(System.lineSeparator());
                    break;
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

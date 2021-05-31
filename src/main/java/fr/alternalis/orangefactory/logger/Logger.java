package fr.alternalis.orangefactory.logger;

import fr.alternalis.orangefactory.elements.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private File logFile = null;
    private FileWriter writer = null;

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

    //TODO: Add execution time
    public void writeLog(String type, String element, String information){
        try {
            switch (type) {
                case "State":
                    writer.write("# State #" + "[ " + element + " ]" + information);
                    break;
                case "Info":
                    writer.write("? Info ?" + "[ " + element + " ]" + information);
                    break;
                case "Action":
                    writer.write("> Action <" + "[ " + element + " ]" + information);
                    break;
                case "Event":
                    writer.write("! Event !" + "[ " + element + " ]" + information);
                    break;
                case "Error":
                    writer.write("< Error >" + "[ " + element + " ]" + information);
                    break;
                case "Result":
                    writer.write("$ Result $" + "[ " + element + " ]" + information);
                    break;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

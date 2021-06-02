package fr.alternalis.orangefactory;

import fr.alternalis.orangefactory.elements.*;
import fr.alternalis.orangefactory.logger.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

public class Controller implements Initializable
{
    private Processor processor = new Processor(this);

    public ImageView stopButton;
    public ImageView stopBoiler;
    public ImageView stopTank;
    public Rectangle tankAlarm;
    public Rectangle boilerAlarm;
    public ImageView shiftButton;
    public ImageView needleBoiler;
    public TextField tankAlarmLabel;
    public TextField boilerAlarmLabel;
    public Slider valveDebitSlider;
    public Slider pumpDebitSlider;
    public Slider powerBoilerSlider;
    public ImageView needleTank;
    public TextField clockLabel;
    public TextField tankTempLabel;
    public TextField pasteurizedLabel;
    public TextField spoilLabel;
    public TextField boilerTemperatureLabel;
    public TextField recycleLabel;
    public TextField boilerPowerLabel;

    public ImageView yellowArrow3;
    public ImageView yellowArrow5;
    public ImageView yellowArrow2;
    public ImageView yellowArrow1;
    public ImageView yellowArrow4;
    public ImageView yellowArrow6;
    public ImageView redArrow1;
    public ImageView greenArrow1;
    public ImageView greenArrow2;
    public ImageView greenArrow3;
    public ImageView greenArrow4;
    public ImageView greenArrow5;
    public ImageView blueArrow1;
    public ImageView blueArrow2;
    public ImageView blueArrow3;

    public Rectangle tankJuice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        EventHandler<MouseEvent> eventHandlerBoiler = e -> {
            synchronized(processor.getBoiler().getPower()) {
                processor.getBoiler().powerChange(powerBoilerSlider.getValue());
            }
        };

        EventHandler<MouseEvent> eventHandlerPump = e -> {
            synchronized(processor.getBoiler().getPump().getDebit()) {
                processor.getBoiler().getPump().setDebit(pumpDebitSlider.getValue());
            }
        };

        EventHandler<MouseEvent> eventHandlerValve = e -> {
            synchronized(processor.getValve().getDebit()) {
                processor.getValve().setDebit(valveDebitSlider.getValue());
            }
        };

        EventHandler<MouseEvent> eventHandlerShift = e -> Logger.writeLog("Info","Shift","L'utilisateur laisse son poste a un autre");

        EventHandler<MouseEvent> eventHandlerStop = e -> {
            Platform.exit();
            System.exit(0);
        };

        EventHandler<MouseEvent> eventHandlerStopBoiler = e -> processor.getBoiler().setActive(!processor.getBoiler().getActive());

        EventHandler<MouseEvent> eventHandlerStopTank = e -> processor.getTank().setActive(!processor.getTank().getActive());

        //Stop tank button event
        stopTank.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerStopTank);

        //Stop boiler button event
        stopBoiler.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerStopBoiler);

        //Stop button event
        stopButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerStop);

        //Shift button event
        shiftButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerShift);

        //Adding event for pump slider
        pumpDebitSlider.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerPump);
        pumpDebitSlider.setMin(processor.getBoiler().getPump().getMinOut());
        pumpDebitSlider.setMax(processor.getBoiler().getPump().getMaxOut());

        //Adding event for valve slider
        valveDebitSlider.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerValve);
        valveDebitSlider.setMin(processor.getValve().getMinOut());
        valveDebitSlider.setMax(processor.getValve().getMaxOut());

        //Adding event for power slider
        powerBoilerSlider.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerBoiler);
        powerBoilerSlider.setMin(processor.getBoiler().getMinPower());
        powerBoilerSlider.setMax(processor.getBoiler().getMaxPower());

        clockLabel.setEditable(false);
        tankTempLabel.setEditable(false);
        pasteurizedLabel.setEditable(false);
        spoilLabel.setEditable(false);
        boilerTemperatureLabel.setEditable(false);
        recycleLabel.setEditable(false);
        boilerPowerLabel.setEditable(false);
        tankAlarmLabel.setEditable(false);
        boilerAlarmLabel.setEditable(false);

        engageFactory();

    }

    public void engageFactory() {
        Logger.openFile();
        Indicator.startTime = System.currentTimeMillis();
        Timer clockTimer = new Timer("ClockTimer");
        clockTimer.schedule(new Clock(this), 0,  1000);
        Logger.writeLog("State", "Application", "Start");
        Timer timerFactory = new Timer("FactoryTimer");
        timerFactory.schedule(processor, 0, Parameter.cycleTime.intValue());
    }

    public void setLevelTank(Double levelPercent)
    {
        double maxHTank = 223D;
        tankJuice.setHeight(maxHTank/100D*levelPercent);
        tankJuice.setLayoutY(100D+maxHTank-maxHTank/100D*levelPercent);
    }

    public void setClockLabel()
    {
        try{
            clockLabel.setText(Clock.getCurrentExecTime());
        } catch (Exception e){
            //Do nothing
        }
    }

    public void setTankTempLabel()
    {
        Tank tank = processor.getTank();
        tankTempLabel.setText(String.valueOf(tank.getTemp()));
        needleTank.setRotate((100D/150*tank.getTemp())/100*104+128);
    }

    public void setPasteurizedLabel()
    {
        pasteurizedLabel.setText(String.valueOf(Indicator.pasteurized));
    }

    public void setSpoilLabel()
    {
        spoilLabel.setText(String.valueOf(Indicator.spoil));
    }

    public void setRecycleLabel()
    {
        recycleLabel.setText(String.valueOf(Indicator.recycle));
    }

    public void setBoilerTemp()
    {
        Boiler boiler = processor.getBoiler();
        boilerTemperatureLabel.setText(String.valueOf(boiler.getTemp()));
        needleBoiler.setRotate((100D/150*boiler.getTemp())/100*104+128);
    }

    public void setBoilerPower(){
        Boiler boiler = processor.getBoiler();
        boilerPowerLabel.setText(String.valueOf(boiler.getPower()));
        powerBoilerSlider.setValue(boiler.getPower());
    }

    public void setArrowInvisible(){
        blueArrow1.setVisible(false);
        blueArrow2.setVisible(false);
        blueArrow3.setVisible(false);
        redArrow1.setVisible(false);
        yellowArrow1.setVisible(false);
        yellowArrow2.setVisible(false);
        yellowArrow3.setVisible(false);
        yellowArrow4.setVisible(false);
        yellowArrow5.setVisible(false);
        yellowArrow6.setVisible(false);
        greenArrow1.setVisible(false);
        greenArrow2.setVisible(false);
        greenArrow3.setVisible(false);
        greenArrow4.setVisible(false);
        greenArrow5.setVisible(false);
    }

    public void setBlueArrowVisible(){
        blueArrow1.setVisible(true);
        blueArrow2.setVisible(true);
        blueArrow3.setVisible(true);
    }

    public void setYellowArrowVisible(){
        yellowArrow1.setVisible(true);
        yellowArrow2.setVisible(true);
        yellowArrow3.setVisible(true);
        yellowArrow4.setVisible(true);
        yellowArrow5.setVisible(true);
        yellowArrow6.setVisible(true);
    }

    public void setGreenArrowVisible(){
        greenArrow1.setVisible(true);
        greenArrow2.setVisible(true);
        greenArrow3.setVisible(true);
        greenArrow4.setVisible(true);
        greenArrow5.setVisible(true);
    }

    public void setRedArrowVisible(){
        redArrow1.setVisible(true);
    }

    public void updateAlarmLabelAndColor(){
        if(processor.getBoiler().getOverloadAlarm()){
            boilerAlarm.setFill(Paint.valueOf(Color.web("#F04723").toString()));
            boilerAlarmLabel.setText("Chaudière en surchauffe");
        } else if (processor.getBoiler().getEcoAlarm()){
            boilerAlarm.setFill(Paint.valueOf(Color.web("#09B3CB").toString()));
            boilerAlarmLabel.setText("Chaudière en économie");
        } else {
            boilerAlarm.setFill(Paint.valueOf(Color.web("#09CB32").toString()));
            boilerAlarmLabel.setText("Chaudière ok");
        }

        if(processor.getTank().getFullAlarm()){
            tankAlarm.setFill(Paint.valueOf(Color.web("#F04723").toString()));
            tankAlarmLabel.setText("Cuve surchargé");
        } else if (processor.getTank().getAlmostEmptyAlarm()){
            tankAlarm.setFill(Paint.valueOf(Color.web("#F04723").toString()));
            tankAlarmLabel.setText("Cuve sous chargé");
        } else if (processor.getTank().getEmptyAlarm()) {
            tankAlarm.setFill(Paint.valueOf(Color.web("#F04723").toString()));
            tankAlarmLabel.setText("Cuve vide, arrêt.");
        } else {
            tankAlarm.setFill(Paint.valueOf(Color.web("#09CB32").toString()));
            tankAlarmLabel.setText("Cuve ok");
        }
    }
}

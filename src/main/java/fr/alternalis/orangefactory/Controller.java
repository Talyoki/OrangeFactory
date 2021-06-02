package fr.alternalis.orangefactory;

import fr.alternalis.orangefactory.elements.*;
import fr.alternalis.orangefactory.logger.Logger;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
        EventHandler<MouseEvent> eventHandler = e -> processor.getBoiler().powerChange(powerBoilerSlider.getValue());

        //Adding event Filter
        powerBoilerSlider.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        powerBoilerSlider.setMin(1D);
        powerBoilerSlider.setMax(100D);

        engageFactory();

        clockLabel.setEditable(false);
        tankTempLabel.setEditable(false);
        pasteurizedLabel.setEditable(false);
        spoilLabel.setEditable(false);
        boilerTemperatureLabel.setEditable(false);
        recycleLabel.setEditable(false);
        boilerPowerLabel.setEditable(false);
        tankAlarmLabel.setEditable(false);
        boilerAlarmLabel.setEditable(false);

        setArrowInvisible();
    }

    public void engageFactory() {
        Logger.openFile();
        Indicator.startTime = System.currentTimeMillis();
        Timer clockTimer = new Timer("ClockTimer");
        clockTimer.schedule(new Clock(this), 0,  1000);
        Logger.writeLog("State", "Application", "Start");
        Timer timer = new Timer("FactoryTimer");
        timer.schedule(processor, 0, Parameter.cycleTime.intValue());
    }

    public void setLevelTank(Double levelPercent)
    {
        double maxHTank = 223D;
        tankJuice.setHeight(maxHTank/100D*levelPercent);
        tankJuice.setLayoutY(100D+maxHTank-maxHTank/100D*levelPercent);
    }

    public void setClockLabel()
    {
        clockLabel.setText(Clock.getCurrentExecTime());
    }

    public void setTankTempLabel()
    {
        tankTempLabel.setText(String.valueOf(processor.getTank().getTemp()));
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

    public void setBoilerPower()
    {
        Boiler boiler = processor.getBoiler();
        boilerPowerLabel.setText(String.valueOf(boiler.getPower()));
        boilerTemperatureLabel.setText(String.valueOf(boiler.getTemp()));
        powerBoilerSlider.setValue(boiler.getPower());
        needleBoiler.setRotate((100D/150*boiler.getTemp())/100*104+128);
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
}

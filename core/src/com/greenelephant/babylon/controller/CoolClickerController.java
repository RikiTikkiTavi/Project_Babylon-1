package com.greenelephant.babylon.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.Timer;
import java.util.TimerTask;

public class CoolClickerController {
    private int clicksAmount;
    private double rateModifier = 1;
    private int suspendTime = 500;
    private ClickerTimer clickerTimer;
    private ClickerSuspendTimer clickerSuspendTimer;
    private double latestClickTime;

    public void handleClicks() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            addClick();
            latestClickTime = System.currentTimeMillis();
        }
    }



    private void addClick () {
        if (clicksAmount==0){
            clickerTimer = new ClickerTimer();
            clickerTimer.start();
            clickerSuspendTimer = new ClickerSuspendTimer();
            clickerSuspendTimer.start();
        }

        // handleClickDistribution();
        clicksAmount ++;
        if (clicksAmount > 4){
            calculateClicksRateModifier(calculateClicksProMillis());
        }
    }

    private void calculateClicksRateModifier(double clicksProMillis){
        rateModifier = (Math.exp(clicksProMillis)*1000)%1003;
    }

    private double calculateClicksProMillis() {
        double currentTime = System.currentTimeMillis();
        double timeDelta = currentTime - clickerTimer.startTime;
        return clicksAmount / timeDelta;
    }

    class ClickerTimer extends Thread {
        double startTime;
        public ClickerTimer(){
            this.startTime = System.currentTimeMillis();
        }
    }

    class ClickerSuspendTimer extends Thread{

        Timer t;
        SuspendTimerTask suspendTimerTask;

        public ClickerSuspendTimer () {
            t = new Timer();
            suspendTimerTask = new SuspendTimerTask();
        }

        class SuspendTimerTask extends TimerTask{
            @Override
            public void run() {
                suspendIfNeeded();
            }
        }

        private void suspendIfNeeded(){
            if(System.currentTimeMillis() - latestClickTime > suspendTime) {
                clicksAmount = 0;
                rateModifier = 1;
            }
        }

        @Override
        public void run(){
            this.t.schedule(suspendTimerTask, 0, suspendTime);
        }
    }

    public double getRateModifier() {
        return rateModifier;
    }

}

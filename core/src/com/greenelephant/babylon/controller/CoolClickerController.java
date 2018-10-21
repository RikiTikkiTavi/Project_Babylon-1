package com.greenelephant.babylon.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class CoolClickerController {
    private int clicksAmount;
    private float rateModifier = 1;
    private int suspendTime;
    ClickerTimer clickerTimer;

    public CoolClickerController() {
        /*
        ClickerListenerThread clickerListenerThread = new ClickerListenerThread();
        clickerListenerThread.start();*/
    }

    public void addClickerListener() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            addClick();
        }
    }

    private void addClick () {
        if (clicksAmount==0){
            clickerTimer = new ClickerTimer();
            clickerTimer.start();
        }
        clicksAmount ++;
        if (clicksAmount >= 2){
            calculateClicksRateModifier(calculateClicksProMillis());
        }
    }

    private void calculateClicksRateModifier(float clicksProMillis){
        rateModifier = (float) Math.exp(clicksProMillis);
    }

    private float calculateClicksProMillis() {
        float currentTime = System.currentTimeMillis();
        float timeDelta = currentTime - clickerTimer.startTime;
        return clicksAmount / timeDelta;
    }

    class ClickerTimer extends Thread {
        float startTime;
        public ClickerTimer(){
            this.startTime = System.currentTimeMillis();
        }
    }

    public float getRateModifier() {
        return rateModifier;
    }



}

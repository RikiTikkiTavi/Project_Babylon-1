package com.greenelephant.babylon.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class ClickerController {

    private int clickNr = 0;
    private long[] clickTime = new long[3];
    private long newTime = 0;
    private double rateModifier = 1;

    public double getRateModifier() {
        return rateModifier;
    }

    public ClickerController() {
        Testing t = new Testing(3000);
        t.start();
        this.handle();
    }


    public int getClickNr() {
        return clickNr;
    }

    public void setClickNr(int clickNr) {
        this.clickNr = clickNr;
    }

    public long getNewTime() {
        return newTime;
    }

    public void setNewTime(long newTime) {
        this.newTime = newTime;
    }

//    public static void main(String[] args) {
//        ClickerController clickerController = new ClickerController();
//
//        // Scanner scanner = new Scanner(System.in);
//
//        ad
//
//        while (true) {
//            // String u = scanner.next();
//            clickerController.addClick();
//            System.out.println(clickerController.clickNr + "");
//            System.out.println("Rate " + clickerController.calculateClicksRate());
//
//        }
//
//
//    }

    private void addClick() {

        setClickNr(getClickNr() + 1);
        if (getClickNr() == 3) {
            setClickNr(1);
        }
        addClickTime();
    }

    public void addClickTime() {

        if (this.clickNr > 0) {

            clickTime[getClickNr() - 1] = System.currentTimeMillis() - getNewTime();
        }
        setNewTime(System.currentTimeMillis());
        System.out.println(clickTime[getClickNr()]);
    }

    public double calculateClicksRate() {

        double rate = 0;
        double sum = 0;


        if (getClickNr() > 1) {
            for (double value : this.clickTime) {
                sum += value;
            }
            rate = (sum / 2) / 1000;
        }
        if (rate == 0 || rate > 5) {
            rate = 1;
        }
        return rate;
    }

    public void handle() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            addClick();

    }

    class Testing extends Thread {

        private long time;

        public Testing(long time) {
            this.time = time;
        }

        @Override
        public void run() {
            super.run();
            while (true) {
                if (calculateClicksRate() > 2) {
                    rateModifier = calculateClicksRate();
                }
                try {
                    sleep((time));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

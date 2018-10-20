package com.greenelephant.babylon.model;

public class TestTower extends Tower {
    public static final String firstModelPath = "testTower.jpg";


    public TestTower(float x, float y) {
        super(firstModelPath, x, y);
        for (int i = 0; i < 3; i++)
            upgradeArray[i] = new EmptyUpgrade();

    }
}

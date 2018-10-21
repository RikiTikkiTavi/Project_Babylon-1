package com.greenelephant.babylon.model;

class TestTower extends Tower {
    private static final String firstModelPath = "firstTower.png";
    public static final int price =  100;




    public TestTower(float x, float y) {
        super(firstModelPath, x, y);
        for (int i = 0; i < 3; i++)
            upgradeArray[i] = new EmptyUpgrade();
        power = 10;
        range = 100;
        shootingFrequency = 4000;
        towerController.setShootingFrequency(shootingFrequency);
        upgrades = new UpgradeScreen(new Upgrade("cannon-tower.png", 120, 100, 3000, 100));
    }
}

package com.greenelephant.babylon.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.greenelephant.babylon.utils.Constants;

import java.util.ArrayList;

public class UpgradeScreen {
    Upgrade[] upgrades;
    UpgradePicture[] upgradePictures;

    UpgradeScreen(){
        upgrades = new Upgrade[3];
        upgrades[0] = new EmptyUpgrade();
        upgrades[1] = new EmptyUpgrade();
        upgrades[2] = new EmptyUpgrade();
    }
    UpgradeScreen(Upgrade upgrade0){
        upgrades = new Upgrade[3];
        upgrades[0] = upgrade0;
        upgrades[1] = new EmptyUpgrade();
        upgrades[2] = new EmptyUpgrade();

    }
    UpgradeScreen(Upgrade upgrade0,Upgrade upgrade1){
        upgrades = new Upgrade[3];
        upgrades[0] = upgrade0;
        upgrades[1] = upgrade1;
        upgrades[2] = new EmptyUpgrade();
    }
    UpgradeScreen(Upgrade upgrade0,Upgrade upgrade1,Upgrade upgrade2){
        upgrades = new Upgrade[3];
        upgrades[0] = upgrade0;
        upgrades[1] = upgrade1;
        upgrades[2] = upgrade2;
    }

    public void setup(float x, float y){
        upgradePictures = new UpgradePicture[3];
        upgradePictures[0] = new UpgradePicture(upgrades[0],(int)(x - Constants.TOWER_WIDTH.value - 24),
                (int)(y - Constants.TOWER_HEIGHT.value- 24));
        upgradePictures[1] = new UpgradePicture(upgrades[1],(int)(x + Constants.TOWER_WIDTH.value- 24),
                (int)(y - Constants.TOWER_HEIGHT.value- 24));
        upgradePictures[2] = new UpgradePicture(upgrades[2],(int)(x- 24),
                (int)(y + Constants.TOWER_HEIGHT.value- 24));
    }

    public void draw(SpriteBatch batch){
        for(UpgradePicture upgradePicture:upgradePictures)
            upgradePicture.draw(batch);
    }

    public void dispose(){
        for(UpgradePicture upgradePicture:upgradePictures)
            upgradePicture.dispose();
    }

    public Upgrade isUpgradeHere(float x, float y){
        for(int i = 0 ; i < 3 ; i ++) {
            if (!upgrades[i].isEmpty())
                if ((new Vector3(upgradePictures[i].x, upgradePictures[i].y, 0)).dst(new Vector3(x, y, 0)) <= 64) {
                    return upgrades[i];
                }
        }
        return null;

    }

}

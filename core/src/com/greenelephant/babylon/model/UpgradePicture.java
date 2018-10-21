package com.greenelephant.babylon.model;

import com.greenelephant.babylon.utils.Constants;

public class UpgradePicture extends GameObject {
    int x,y;
    UpgradePicture(Upgrade upgrade,int x,int y){
        super(upgrade.getTexturePath(),x,y, Constants.TOWER_WIDTH.value,Constants.TOWER_HEIGHT.value);
        this.x = x;
        this.y = y;
    }

}

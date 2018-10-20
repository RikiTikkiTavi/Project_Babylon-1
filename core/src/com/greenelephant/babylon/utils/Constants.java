package com.greenelephant.babylon.utils;

public enum Constants {
    /* resolution 1920 */
    APP_WIDTH(1920),
    APP_HEIGHT(1080),
    TOWER_WIDTH(64),
    TOWER_HEIGHT(96),
    ENEMY_WIDTH(48),
    ENEMY_HEIGHT(48),
    RESOLUTION(1024);


    public final int value;

    Constants(int value) {
        this.value = value;
    }
}

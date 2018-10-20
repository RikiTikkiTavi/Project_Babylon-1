package com.greenelephant.babylon.utils;

public enum Constants {
    /* resolution 1920 */
    APP_WIDTH(1920),
    APP_HEIGHT(1080),
    FIELD_SIZE(50),
    RESOLUTION(1024);

    public final int value;
    Constants (int value){
        this.value = value;
    }
}

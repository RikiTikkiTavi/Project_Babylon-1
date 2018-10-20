package com.greenelephant.babylon.model;

public class EmptyUpgrade extends Upgrade {
    EmptyUpgrade() {
        super("green-elephant.jpg", 0, 0, 0, 0);
    }

    boolean isEmpty() {
        return true;
    }
}

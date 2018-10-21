package com.greenelephant.babylon.model;

class EmptyUpgrade extends Upgrade {
    EmptyUpgrade() {
        super("FFFFFF-0.png", 0, 0, 0, 0);
    }

    boolean isEmpty() {
        return true;
    }
}

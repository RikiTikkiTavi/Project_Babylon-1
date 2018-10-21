package com.greenelephant.babylon.model;

import com.badlogic.gdx.math.Vector3;
import com.greenelephant.babylon.utils.Pair;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

class MapConfig {
    private Vector3 spawnPoint = new Vector3(15, 310, 0);
    private ArrayList<Pair<Vector3,Boolean>> towerDots = new ArrayList<Pair<Vector3, Boolean>>();
    private ArrayList<Pair<Vector3, Integer>> turnPoints = new ArrayList<Pair<Vector3, Integer>>();

    public MapConfig() {
        towerDots.add(new Pair<Vector3, Boolean>(new Vector3(100, 248, 0),false));
        towerDots.add(new Pair<Vector3, Boolean>(new Vector3(196, 373, 0),false));
        towerDots.add(new Pair<Vector3, Boolean>(new Vector3(343, 200, 0),false));
        towerDots.add(new Pair<Vector3, Boolean>(new Vector3(484, 342, 0),false)); //Play button
        towerDots.add(new Pair<Vector3, Boolean>(new Vector3(483, 197, 0),false));
        towerDots.add(new Pair<Vector3, Boolean>(new Vector3(340, 85, 0),false));
        towerDots.add(new Pair<Vector3, Boolean>(new Vector3(548, 84, 0),false));
        towerDots.add(new Pair<Vector3, Boolean>(new Vector3(677, 149, 0),false));
        towerDots.add(new Pair<Vector3, Boolean>(new Vector3(805, 216, 0),false));
        towerDots.add(new Pair<Vector3, Boolean>(new Vector3(678, 298, 0),false));
        towerDots.add(new Pair<Vector3, Boolean>(new Vector3(773, 377, 0),false));

        turnPoints.add(new Pair<Vector3, Integer>(new Vector3(411, 310, 0), -1));
        turnPoints.add(new Pair<Vector3, Integer>(new Vector3(411, 16, 0), 1));
        turnPoints.add(new Pair<Vector3, Integer>(new Vector3(727, 16, 0), 1));
        turnPoints.add(new Pair<Vector3, Integer>(new Vector3(727, 310, 0), -1));
        turnPoints.add(new Pair<Vector3, Integer>(new Vector3(895, 310, 0), 0));
    }

    public Vector3 getSpawnPoint() {
        return spawnPoint;
    }

    public ArrayList<Pair<Vector3, Integer>> getTurnPoints() {
        return turnPoints;
    }

    public ArrayList<Pair<Vector3,Boolean>> getTowerDots() {
        return towerDots;
    }
}

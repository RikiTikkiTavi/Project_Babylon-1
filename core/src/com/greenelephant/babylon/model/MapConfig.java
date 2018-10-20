package com.greenelephant.babylon.model;

import com.badlogic.gdx.math.Vector3;
import com.greenelephant.babylon.utils.Pair;

import java.util.ArrayList;

public class MapConfig {
    private Vector3 spawnPoint = new Vector3(15, 310, 0);
    private ArrayList<Vector3> towerDots = new ArrayList<Vector3>();
    private ArrayList<Pair<Vector3, Integer>> turnPoints = new ArrayList<Pair<Vector3, Integer>>();;

    public MapConfig(){
        towerDots.add(new Vector3(100, 248, 0));
        towerDots.add(new Vector3(201, 373, 0));
        towerDots.add(new Vector3(348, 200, 0));
        towerDots.add(new Vector3(489, 342, 0)); //Play button
        towerDots.add(new Vector3(488, 197, 0));
        towerDots.add(new Vector3(345, 85, 0));
        towerDots.add(new Vector3(553, 94, 0));
        towerDots.add(new Vector3(682, 149, 0));
        towerDots.add(new Vector3(810, 216, 0));
        towerDots.add(new Vector3(683, 298, 0));
        towerDots.add(new Vector3(778, 377, 0));

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

    public ArrayList<Vector3> getTowerDots() {
        return towerDots;
    }
}

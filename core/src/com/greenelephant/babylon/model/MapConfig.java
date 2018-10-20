package com.greenelephant.babylon.model;

import com.badlogic.gdx.math.Vector3;
import com.greenelephant.babylon.utils.Pair;

import java.util.ArrayList;

public class MapConfig {
    private Vector3 spawnPoint = new Vector3(15, 310, 0);
    private ArrayList<Vector3> towerDots = new ArrayList<Vector3>();
    private ArrayList<Pair<Vector3, Integer>> turnPoints = new ArrayList<Pair<Vector3, Integer>>();;

    public MapConfig(){
        towerDots.add(new Vector3(215, 186, 0));
        towerDots.add(new Vector3(327, 45, 0));

        turnPoints.add(new Pair<Vector3, Integer>(new Vector3(411, 310, 0), -1));
        turnPoints.add(new Pair<Vector3, Integer>(new Vector3(411, 22, 0), 1));
        turnPoints.add(new Pair<Vector3, Integer>(new Vector3(737, 22, 0), 1));
        turnPoints.add(new Pair<Vector3, Integer>(new Vector3(737, 310, 0), -1));
        turnPoints.add(new Pair<Vector3, Integer>(new Vector3(781, 310, 0), 0));
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

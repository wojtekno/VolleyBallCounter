package com.example.android.volleyballcounter;

/**
 * Created by Wojtek on 3/13/2018.
 */

public class SavedStats {
    private int[] scoreStats = new int[10];
    private boolean isGameFinished;

    public SavedStats(int[] scoreStats, boolean isGameFinished) {
        this.scoreStats = scoreStats;
        this.isGameFinished = isGameFinished;
    }


    public int[] getScoreStats() {
        return scoreStats;
    }

    public void setScoreStats(int[] scoreStats) {
        this.scoreStats = scoreStats;
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        isGameFinished = gameFinished;
    }
}

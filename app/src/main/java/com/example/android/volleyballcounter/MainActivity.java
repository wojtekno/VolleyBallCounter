package com.example.android.volleyballcounter;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int scoreA;
    int scoreB;
    int[] currentStatsA = {0, 0, 0, 0, 0};
    int[] currentStatsB = {0, 0, 0, 0, 0};
    int[] undoStats = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] redoStats = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    Button undoButton;
    Button redoButton;
    Button resetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        undoButton = findViewById(R.id.undo_button);
        redoButton = findViewById(R.id.redo_button);
        resetButton = findViewById(R.id.reset_button);
        undoButton.setEnabled(Boolean.FALSE);
        redoButton.setEnabled(Boolean.FALSE);
        resetButton.setEnabled(Boolean.FALSE);
    }


    void displayStats() {

        TextView scoreTVA = findViewById(R.id.score_tv_a);
        scoreTVA.setText(String.valueOf(scoreA));
        TextView setTVA = findViewById(R.id.set_tv_a);
        setTVA.setText(String.valueOf(currentStatsA[0]));
        TextView attackTVA = findViewById(R.id.attack_tv_a);
        attackTVA.setText(String.valueOf(currentStatsA[1]));
        TextView blockTVA = findViewById(R.id.block_tv_a);
        blockTVA.setText(String.valueOf(currentStatsA[2]));
        TextView serviceTVA = findViewById(R.id.service_tv_a);
        serviceTVA.setText(String.valueOf(currentStatsA[3]));
        TextView oppErrorTVA = findViewById(R.id.opp_error_tv_a);
        oppErrorTVA.setText(String.valueOf(currentStatsA[4]));
        TextView scoreTVB = findViewById(R.id.score_tv_b);
        scoreTVB.setText(String.valueOf(scoreB));
        TextView setTVB = findViewById(R.id.set_tv_b);
        setTVB.setText(String.valueOf(currentStatsB[0]));
        TextView attackTVB = findViewById(R.id.attack_tv_b);
        attackTVB.setText(String.valueOf(currentStatsB[1]));
        TextView blockTVB = findViewById(R.id.block_tv_b);
        blockTVB.setText(String.valueOf(currentStatsB[2]));
        TextView serviceTVB = findViewById(R.id.service_tv_b);
        serviceTVB.setText(String.valueOf(currentStatsB[3]));
        TextView oppErrorTVB = findViewById(R.id.opp_error_tv_b);
        oppErrorTVB.setText(String.valueOf(currentStatsB[4]));
    }

    public void addAttackA(View view) {
        saveUndoStats();
        Log.v("Main", "after saved:" + undoStats[1] + "  blocks: " + undoStats[2]);

        currentStatsA[1] = changeStats(currentStatsA[1]);
        Log.v("Main", "3)after saved:" + undoStats[1] + "  blocks: " + undoStats[2]);
//        Log.v("Main", "curr[1]: " + currentStatsA[1] + " current [2]" + currentStatsA[2]);
        afterChangeStats();
    }

    public void addBlockA(View view) {
        saveUndoStats();
        currentStatsA[2] = changeStats(currentStatsA[2]);
        afterChangeStats();
    }

    public void addServiceA(View view) {
        saveUndoStats();
        currentStatsA[3] = changeStats(currentStatsA[3]);
        afterChangeStats();

    }

    public void addOppErrorsA(View view) {
        saveUndoStats();
        currentStatsA[4] = changeStats(currentStatsA[4]);
        afterChangeStats();

    }

    public void addAttackB(View view) {
        saveUndoStats();
        currentStatsB[1] = changeStats(currentStatsB[1]);
        afterChangeStats();

    }

    public void addBlockB(View view) {
        saveUndoStats();
        currentStatsB[2] = changeStats(currentStatsB[2]);
        afterChangeStats();

    }

    public void addServiceB(View view) {
        saveUndoStats();
        currentStatsB[3] = changeStats(currentStatsB[3]);
        afterChangeStats();

    }

    public void addOppErrorsB(View view) {
        saveUndoStats();
        currentStatsB[4] = changeStats(currentStatsB[4]);
        afterChangeStats();

    }

    void saveUndoStats() {
        for (int i = 0; i < currentStatsA.length; i++) {
            undoStats[i] = currentStatsA[i];
            undoStats[i + 5] = currentStatsB[i];
        }
    }

    void saveRedoStats() {
        for (int i = 0; i < currentStatsA.length; i++) {
            redoStats[i] = currentStatsA[i];
            redoStats[i + 5] = currentStatsB[i];
        }
    }

    //TODO delete if obsolete
    void refreshScore() {
        scoreA = currentStatsA[1] + currentStatsA[2] + currentStatsA[3] + currentStatsA[4];
        scoreB = currentStatsB[1] + currentStatsB[2] + currentStatsB[3] + currentStatsB[4];
    }


    public void reset(View view) {
        saveUndoStats();
        currentStatsA = new int[]{0, 0, 0, 0, 0};
        currentStatsB = new int[]{0, 0, 0, 0, 0};
        refreshScore();
        displayStats();
        undoButton.setEnabled(Boolean.TRUE);
        redoButton.setEnabled(Boolean.FALSE);
        resetButton.setEnabled(Boolean.TRUE);
    }

    int changeStats(int stat) {
        stat++;
        return stat;
    }

    void afterChangeStats() {
        Log.v("Main", "curent:" + currentStatsA[1] + "  blocks: " + currentStatsA[2] + " saved: " + undoStats[1]);
        refreshScore();
        displayStats();
        winSet();
        winMatch();
//        displayStats();
        undoButton.setEnabled(Boolean.TRUE);
        redoButton.setEnabled(Boolean.FALSE);
        resetButton.setEnabled(Boolean.TRUE);

    }

    void setFromSavedStats(int[] savedStats) {
        for (int i = 0; i < currentStatsA.length; i++) {
            currentStatsA[i] = savedStats[i];
            currentStatsB[i] = savedStats[i + 5];
        }
    }

    //TODO workout undo reset redo
    public void undo(View view) {
        saveRedoStats();
        setFromSavedStats(undoStats);
        refreshScore();
        displayStats();
        undoButton.setEnabled(Boolean.FALSE);
        redoButton.setEnabled(Boolean.TRUE);
        resetButton.setEnabled(Boolean.TRUE);
    }

    public void redo(View view) {
        saveUndoStats();
        setFromSavedStats(redoStats);
        refreshScore();
        displayStats();
        undoButton.setEnabled(Boolean.TRUE);
        redoButton.setEnabled(Boolean.FALSE);
        resetButton.setEnabled(Boolean.TRUE);
    }

    //TODO finish this method
    void winSet() {
        if ((scoreA >= 7) | (scoreB >= 7)) {
            if (scoreA >= scoreB + 2) {

                new CountDownTimer(2500, 500) {
                    TextView winsATV = findViewById(R.id.wins_tv_a);

                    public void onTick(long millisUntilFinished) {
                        winsATV.setText("");
                    }

                    public void onFinish() {
                    }
                }.start();

                /*
                displays goal! for a second, and disables button for that time
                */
                new CountDownTimer(1500, 500) {
                    TextView scoreATV = findViewById(R.id.score_tv_a);
                    TextView winsATV = findViewById(R.id.wins_tv_a);

                    public void onTick(long millisUntilFinished) {
                        scoreATV.setText("");
                        winsATV.setTextSize(24);
                        winsATV.setText("Wins the set");
                    }

                    public void onFinish() {
                        winsATV.setText("");
                        scoreATV.setTextSize(56);
                        scoreATV.setText(String.valueOf(scoreA));
                        currentStatsA[0]++;
                        for (int i = 1; i < currentStatsA.length; i++) {
                            currentStatsA[i] = 0;
                            refreshScore();
                            displayStats();
                        }

                    }
                }.start();


            } else if (scoreB >= scoreA + 2) {
                currentStatsB[0]++;
                for (int i = 1; i < currentStatsB.length; i++) {
                    currentStatsB[i] = 0;
                }
            }
        }
    }

    void winMatch() {
        if ((currentStatsA[0] == currentStatsB[0]) & currentStatsA[0] == 2) {
            playTieBreak();
        } else if (currentStatsA[0] == 3) {
            currentStatsA[0] = 333;
        } else if (currentStatsB[0] == 3) {
            currentStatsB[0] = 333;
        }
    }

    void playTieBreak() {
        if ((scoreA >= 5) | (scoreB >= 5)) {
            if (scoreA >= scoreB + 2) {
                currentStatsA[0]++;
                for (int i = 1; i < currentStatsA.length; i++) {
                    currentStatsA[i] = 0;
                }
            } else if (scoreB >= scoreA + 2) {
                currentStatsB[0]++;
                for (int i = 1; i < currentStatsB.length; i++) {
                    currentStatsB[i] = 0;
                }
            }
        }
    }
}

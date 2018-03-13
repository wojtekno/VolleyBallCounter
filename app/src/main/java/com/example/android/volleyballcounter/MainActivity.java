package com.example.android.volleyballcounter;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
   private int scoreA;
    private int scoreB;
    private int[] currentStatsA = {0, 0, 0, 0, 0,};
    private int[] currentStatsB = {0, 0, 0, 0, 0};
    private int[] undoStats = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[] redoStats = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    Button undoButton;
    Button redoButton;
    Button resetButton;
    Button newGameButton;
    private final int POINTS_TO_WIN_THE_SET = 7;
    private final int POINTS_TO_WIN_TIEBREAK = 5;
    private final int MILLISINFUTURE = 1100;
    private final int COUNTDOWNINTERVAL = 1000;
    private boolean areImageButtonsEnabled = true;
    private boolean isEndOfGame = false;

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
        newGameButton = findViewById(R.id.new_game_button);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntArray("currentStatsA", currentStatsA);
        outState.putIntArray("currentStatsB", currentStatsB);
        outState.putIntArray("undoStats", undoStats);
        outState.putIntArray("redoStats", redoStats);
        outState.putBoolean("isUndoButtEnabled", undoButton.isEnabled());
        outState.putBoolean("isRedoButtEnabled", redoButton.isEnabled());
        outState.putBoolean("isResetButtEnabled", resetButton.isEnabled());
        outState.putInt("isResetButtVisible", resetButton.getVisibility());
        outState.putInt("isNewGameButtVisible", newGameButton.getVisibility());
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        currentStatsA = savedInstanceState.getIntArray("currentStatsA");
        currentStatsB = savedInstanceState.getIntArray("currentStatsB");
        undoStats = savedInstanceState.getIntArray("undoStats");
        redoStats = savedInstanceState.getIntArray("redoStats");
        undoButton.setEnabled(savedInstanceState.getBoolean("isUndoButtEnabled"));
        redoButton.setEnabled(savedInstanceState.getBoolean("isRedoButtEnabled"));
        resetButton.setEnabled((savedInstanceState.getBoolean("isResetButtEnabled")));
        resetButton.setVisibility(savedInstanceState.getInt("isResetButtVisible"));
        newGameButton.setVisibility(savedInstanceState.getInt("isNewGameButtVisible"));
        refreshScore();
        displayStats();
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

    void enableImageButtons(boolean flag) {
        areImageButtonsEnabled = flag;
        ImageButton button1 = findViewById(R.id.attack_button_a);
        ImageButton button2 = findViewById(R.id.attack_button_b);
        ImageButton button3 = findViewById(R.id.block_button_a);
        ImageButton button4 = findViewById(R.id.block_button_b);
        ImageButton button5 = findViewById(R.id.service_button_a);
        ImageButton button6 = findViewById(R.id.service_button_b);
        ImageButton button7 = findViewById(R.id.opp_error_button_a);
        ImageButton button8 = findViewById(R.id.opp_error_button_b);
        button1.setEnabled(flag);
        button2.setEnabled(flag);
        button3.setEnabled(flag);
        button4.setEnabled(flag);
        button5.setEnabled(flag);
        button6.setEnabled(flag);
        button7.setEnabled(flag);
        button8.setEnabled(flag);
    }
    void enableFunctionButtons(boolean flag) {
        undoButton.setEnabled(flag);
        resetButton.setEnabled(flag);
        redoButton.setEnabled(flag);
    }

    public void addAttackA(View view) {
        saveStatsForUndo();
        Log.v("Main", "after saved:" + undoStats[1] + "  blocks: " + undoStats[2]);

        currentStatsA[1] = changeStats(currentStatsA[1]);
        Log.v("Main", "3)after saved:" + undoStats[1] + "  blocks: " + undoStats[2]);
//        Log.v("Main", "curr[1]: " + currentStatsA[1] + " current [2]" + currentStatsA[2]);
        afterChangeStats();
    }

    public void addBlockA(View view) {
        saveStatsForUndo();
        currentStatsA[2] = changeStats(currentStatsA[2]);
        afterChangeStats();
    }

    public void addServiceA(View view) {
        saveStatsForUndo();
        currentStatsA[3] = changeStats(currentStatsA[3]);
        afterChangeStats();

    }

    public void addOppErrorsA(View view) {
        saveStatsForUndo();
        currentStatsA[4] = changeStats(currentStatsA[4]);
        afterChangeStats();

    }

    public void addAttackB(View view) {
        saveStatsForUndo();
        currentStatsB[1] = changeStats(currentStatsB[1]);
        afterChangeStats();

    }

    public void addBlockB(View view) {
        saveStatsForUndo();
        currentStatsB[2] = changeStats(currentStatsB[2]);
        afterChangeStats();

    }

    public void addServiceB(View view) {
        saveStatsForUndo();
        currentStatsB[3] = changeStats(currentStatsB[3]);
        afterChangeStats();

    }

    public void addOppErrorsB(View view) {
        saveStatsForUndo();
        currentStatsB[4] = changeStats(currentStatsB[4]);
        afterChangeStats();

    }

    void saveStatsForUndo() {
        for (int i = 0; i < currentStatsA.length; i++) {
            undoStats[i] = currentStatsA[i];
            undoStats[i + 5] = currentStatsB[i];
        }
    }

    void saveStatsForRedo() {
        SavedStats statsForRedo = new SavedStats()
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
        saveStatsForUndo();
        currentStatsA = new int[]{0, 0, 0, 0, 0};
        currentStatsB = new int[]{0, 0, 0, 0, 0};
        refreshScore();
        displayStats();
        undoButton.setEnabled(Boolean.TRUE);
        redoButton.setEnabled(Boolean.FALSE);
        resetButton.setEnabled(Boolean.TRUE);
    }

    public void startNewGame(View view) {
        saveStatsForUndo();
        currentStatsA = new int[]{0, 0, 0, 0, 0};
        currentStatsB = new int[]{0, 0, 0, 0, 0};
        refreshScore();
        displayStats();
        enableImageButtons(true);
        undoButton.setEnabled(Boolean.TRUE);
//        redoButton.setEnabled(Boolean.FALSE);
//        resetButton.setEnabled(Boolean.FALSE);
        newGameButton.setVisibility(View.GONE);
        resetButton.setVisibility(View.VISIBLE);

        areImageButtonsEnabled = false;
    }

    int changeStats(int stat) {
        stat++;
        return stat;
    }

    void afterChangeStats() {
        refreshScore();
        displayStats();
        if (ifWinTheSet()) {
            winTheSet();
        }
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
        saveStatsForRedo();
        setFromSavedStats(undoStats);
        refreshScore();
        displayStats();
        undoButton.setEnabled(Boolean.FALSE);
        redoButton.setEnabled(Boolean.TRUE);
        resetButton.setEnabled(Boolean.TRUE);
        if(!areImageButtonsEnabled) {
            enableImageButtons(true);
        }
    }

    public void redo(View view) {
        saveStatsForUndo();
        setFromSavedStats(redoStats);
        refreshScore();
        displayStats();
        undoButton.setEnabled(Boolean.TRUE);
        redoButton.setEnabled(Boolean.FALSE);
        resetButton.setEnabled(Boolean.TRUE);
        if(!areImageButtonsEnabled) {
            enableImageButtons(true);
        }
    }

    boolean ifWinTheSet() {
        if (currentStatsA[0] != 2 | currentStatsB[0] != 2) {
            if ((scoreA >= POINTS_TO_WIN_THE_SET & scoreA >= scoreB + 2) | (scoreB >= POINTS_TO_WIN_THE_SET & scoreB >= scoreA + 2)) {
                return true;
            }
        } else {
            if ((scoreA >= POINTS_TO_WIN_TIEBREAK & scoreA >= scoreB + 2) | (scoreB >= POINTS_TO_WIN_TIEBREAK & scoreB >= scoreA + 2)) {
                return true;
            }
        }
        return false;
    }

    //TODO finish this method
    void winTheSet() {
        if (scoreA >= scoreB + 2) {
            currentStatsA[0]++;
            new CountDownTimer(MILLISINFUTURE, COUNTDOWNINTERVAL) {
                TextView winsTV = findViewById(R.id.wins_tv_a);
                TextView scoreTV = findViewById(R.id.score_tv_a);

                public void onTick(long millisUntilFinished) {
                    enableImageButtons(false);
                    enableFunctionButtons(false);
                }

                public void onFinish() {
                    new CountDownTimer(MILLISINFUTURE, COUNTDOWNINTERVAL) {
                        public void onTick(long millisUntilFinished) {
                            winsTV.setTextSize(24);
                            winsTV.setText(R.string.wins_the_set);
                            scoreTV.setText("");
                        }

                        public void onFinish() {
                            if (!ifWinTheMatch()) {
                                winsTV.setText("");
                                for (int i = 1; i < currentStatsA.length; i++) {
                                    currentStatsA[i] = 0;
                                    currentStatsB[i] = 0;
                                }
                                refreshScore();
                                displayStats();
                                enableImageButtons(true);
                                undoButton.setEnabled(true);
                                resetButton.setEnabled(true);
                            } else {
                                new CountDownTimer(MILLISINFUTURE, COUNTDOWNINTERVAL) {
                                    public void onTick(long millisUntilFinished) {
                                        displayStats();
                                        scoreTV.setText("");
                                        winsTV.setText(R.string.wins_the_match);
                                    }

                                    public void onFinish() {
                                        winsTV.setText("");
                                        refreshScore();
                                        displayStats();
                                        undoButton.setEnabled(true);
                                        resetButton.setVisibility(View.GONE);
                                        newGameButton.setVisibility(View.VISIBLE);

                                    }
                                }.start();
                            }
                        }
                    }.start();
                }
            }.start();
        } else if (scoreB >= scoreA + 2) {
            currentStatsB[0]++;
            new CountDownTimer(MILLISINFUTURE, COUNTDOWNINTERVAL) {
                TextView winsTV = findViewById(R.id.wins_tv_b);
                TextView scoreTV = findViewById(R.id.score_tv_b);

                public void onTick(long millisUntilFinished) {
                    enableImageButtons(false);
                    enableImageButtons(false);
                }

                public void onFinish() {
                    new CountDownTimer(MILLISINFUTURE, COUNTDOWNINTERVAL) {
                        public void onTick(long millisUntilFinished) {
                            winsTV.setTextSize(24);
                            winsTV.setText(R.string.wins_the_set);
                            scoreTV.setText("");
                        }

                        public void onFinish() {
                            if (!ifWinTheMatch()) {
                                winsTV.setText("");
                                for (int i = 1; i < currentStatsA.length; i++) {
                                    currentStatsA[i] = 0;
                                    currentStatsB[i] = 0;
                                }
                                refreshScore();
                                displayStats();
                                enableImageButtons(true);
                                undoButton.setEnabled(true);
                                resetButton.setEnabled(true);
                            } else {
                                new CountDownTimer(MILLISINFUTURE, COUNTDOWNINTERVAL) {
                                    public void onTick(long millisUntilFinished) {
                                        displayStats();
                                        scoreTV.setText("");
                                        winsTV.setText(R.string.wins_the_match);
                                    }

                                    public void onFinish() {
                                        winsTV.setText("");
                                        refreshScore();
                                        displayStats();
                                        undoButton.setEnabled(true);
                                        resetButton.setVisibility(View.GONE);
                                        newGameButton.setVisibility(View.VISIBLE);
                                    }
                                }.start();
                            }
                        }
                    }.start();
                }
            }.start();
        }
    }

    boolean ifWinTheMatch() {
        if (currentStatsA[0] == 3 | currentStatsB[0] == 3) {
            return true;
        }
        return false;
    }

    void winTheMatch() {
        if (currentStatsA[0] == 3) {
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

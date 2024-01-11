package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int SIZE = 3;
    private final Button[][] buttons = new Button[SIZE][SIZE];
    private boolean playerXTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j] = new Button(this);
                buttons[i][j].setTextSize(30);
                buttons[i][j].setOnClickListener(v -> onButtonClick((Button) v));
                gridLayout.addView(buttons[i][j]);
            }
        }
        Button newGameButton = new Button(this);
        newGameButton.setText(R.string.new_game);
        newGameButton.setOnClickListener(v -> resetGame());
        gridLayout.addView(newGameButton);
    }

    private void onButtonClick(Button button) {
        if (!button.getText().toString().equals("")) {
            return;
        }

        if (playerXTurn) {
            button.setText(R.string.player_x);
        } else {
            button.setText(R.string.player_o);
        }

        if (checkForWinner()) {
            String winner = playerXTurn ? getString(R.string.player_x) : getString(R.string.player_o);
            Toast.makeText(this, winner + " Wygrywa!", Toast.LENGTH_SHORT).show();
            resetGame();
        } else if (isBoardFull()) {
            Toast.makeText(this, "Remis, Spróbuj jeszcze raz!", Toast.LENGTH_SHORT).show();
            resetGame();
        } else {
            playerXTurn = !playerXTurn;
        }
    }

    private boolean checkForWinner() {
        String[][] field = new String[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < SIZE; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (buttons[i][j].getText().toString().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j].setText("");
            }
        }
        playerXTurn = true;
    }
}
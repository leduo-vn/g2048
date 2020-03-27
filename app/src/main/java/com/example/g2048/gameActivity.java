package com.example.g2048;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.g2048.entities.User;
import com.example.g2048.otherClass.game2048;
import java.util.HashMap;
import java.util.Map;

public class gameActivity extends AppCompatActivity {

    String username;
    game2048 game;
    Map<Integer, String> myMap = new HashMap<Integer, String>() {
        {
            put(1, "#CDC1B4");
            put(2, "#EEE4DA");
            put(4, "#EDE0C8");
            put(8, "#F2B179");
            put(16, "#F59563");
            put(32, "#F67C5F");
            put(64, "#F65E3B");
            put(128, "#EDCF72");
            put(256, "#EDCC61");
            put(512, "#EDC850");
            put(1024, "#EDC53F");
            put(2048, "#EAC02D");
        }
    };

    private void DisplayBoard(int[][] gameMatrix)
    {
        for (int row = 0; row < gameMatrix.length; row ++)
            for (int col = 0; col <gameMatrix.length; col++) {
                String idName = "textView" + Integer.toString(row * 4 + col);
                TextView item = (TextView) findViewById(getResources().getIdentifier(idName, "id", getPackageName()));
                item.setBackgroundColor(Color.parseColor(myMap.get(gameMatrix[row][col])));
                if (gameMatrix[row][col] == 1)
                    item.setText(null);
                else
                    item.setText(Integer.toString(gameMatrix[row][col]));
            }
    }

    private void DisplayScore(int score)
    {
        TextView scoreTextView = (TextView) findViewById(R.id.score);
        scoreTextView.setText(getResources().getString(R.string.score) + Integer.toString(score));
    }

    private void DisplayHighScore(int highScore)
    {
        TextView highScoreTextView = (TextView) findViewById(R.id.highScore);
        highScoreTextView.setText(getResources().getString(R.string.highScore)+Integer.toString(highScore));
    }

    public void DisplayGameMatrix(int[][] gameMatrix, int score, int highScore){

        DisplayBoard(gameMatrix);
        DisplayScore(score);
        DisplayHighScore(highScore);
    }

    public void StoreCurrentGame()
    {
        // Update current status for user
        User user = new User();
        user.setUsername(username);
        user.setScore(game.getHighScore());
        MainActivity.gDatabase.daoUser().updateUser(user);
    }

    public void DisplayEndGame()
    {
        StoreCurrentGame();

        // display play again button
        Button playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
        playAgainBtn.setVisibility(View.VISIBLE);

        TextView cover = (TextView) findViewById(R.id.cover);
        cover.setVisibility(View.VISIBLE);
    }

    public void StartNewGame()
    {
        // Get the current status of user
        User user = MainActivity.gDatabase.daoUser().getUserByName(username);
        Log.i("info",Integer.toString(user.getScore()));
        game = new game2048(user.getScore());
        DisplayGameMatrix(game.getGameMatrix(),game.getScore(),game.getHighScore());
    }

    public void PlayAgainBtnClicked(View view)
    {
        // Remove play again button
        Button playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
        playAgainBtn.setVisibility(View.INVISIBLE);

        TextView cover = (TextView) findViewById(R.id.cover);
        cover.setVisibility(View.INVISIBLE);

        StartNewGame();
    }

    public void UpClicked(View view)
    {
       game.Up();
       DisplayGameMatrix(game.getGameMatrix(),game.getScore(),game.getHighScore());
       if (!game.getGameActive())
           DisplayEndGame();

    }


    public void DownClicked(View view)
    {
        game.Down();
        DisplayGameMatrix(game.getGameMatrix(),game.getScore(),game.getHighScore());
        if (!game.getGameActive())
            DisplayEndGame();
    }

    public void LeftClicked(View view)
    {
        game.Left();
        DisplayGameMatrix(game.getGameMatrix(),game.getScore(),game.getHighScore());
        if (!game.getGameActive())
            DisplayEndGame();
    }

    public void RightClicked(View view)
    {
       game.Right();
        DisplayGameMatrix(game.getGameMatrix(),game.getScore(),game.getHighScore());
        if (!game.getGameActive())
            DisplayEndGame();
    }

    public void LogoutClick(View view)
    {
        StoreCurrentGame();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        username =intent.getStringExtra("USERNAME");
        Toast.makeText(this,"Welcome " + username +"!!!", Toast.LENGTH_SHORT).show();
        StartNewGame();
    }
}

package com.example.g2048.otherClass;

import android.util.Log;
import java.util.Random;

public class game2048 {

    Random rand = new Random();
    final int SIZE=4;

    // Game stage
    int[][] gameMatrix = {
            {1,1,1,1},
            {1,1,1,1},
            {1,1,1,1},
            {1,1,1,1}
    };

    boolean gameActive;
    int score;
    int HighScore;

    public game2048(int highScore)
    {
        score = 0;
        HighScore = highScore;
        gameActive = true;
        AddNewRandomItem();
    }

    public int[][] getGameMatrix() {
        return gameMatrix;
    }
    public int getScore(){return score;};
    public int getHighScore() {return HighScore;};
    public Boolean getGameActive() { return gameActive; };


    public int getRandomAvailablePosition()
    {
        Log.i("info","here");
        int randInt=0;
        int quotient=0;
        int remainder=0;
        do {
            Log.i("info","here1");
            randInt = rand.nextInt(16);
            quotient = randInt / 4;
            remainder = randInt% 4;
        } while (gameMatrix[quotient][remainder]!=1);

        return randInt;
    }

    private void IncreaseScore(int extra)
    {
        score+=extra;
        if (score > HighScore) HighScore = score;
    }

    public void AddNewRandomItem()
    {
        Boolean available = false;
        for (int row = 0; row<SIZE;row++)
            for (int col =0; col<SIZE; col++)
                if (gameMatrix[row][col] ==1)
                {
                    available = true;
                    break;
                }
        if (available) {
            int itemPos = getRandomAvailablePosition();
            int quotient = itemPos / 4;
            int remainder = itemPos % 4;
            gameMatrix[quotient][remainder] = 2;
        }
        else{
            CheckGameOver();
            gameActive = false;
        }

    }

    // If there is no further space or move then game is over
    private void CheckGameOver()
    {
        for (int row = 0; row< SIZE-2; row++)
            for (int col=0; col <SIZE-2; col++)
                if (gameMatrix[row][col]==gameMatrix[row+1][col] || gameMatrix[row][col] == gameMatrix[row][col+1] ) return;

         gameActive = false;
    }


    public void Up()
    {
        ShiftUp();;
        MergeUp();
        ShiftUp();
        AddNewRandomItem();
    }

    public void Down()
    {
        ShiftDown();
        MergeDown();
        ShiftDown();
        AddNewRandomItem();
    }

    public void Left()
    {
        ShiftLeft();
        MergeLeft();
        ShiftLeft();
        AddNewRandomItem();
    }

    public void Right()
    {
        ShiftRight();
        MergeRight();
        ShiftRight();
        AddNewRandomItem();
    }

    private void ShiftUp()
    {
        for (int col=0; col<SIZE;col++)
            for (int row = 0; row<SIZE;row++)
                if ((row>0) && (gameMatrix[row-1][col] == 1 ))
                {
                    int currentRow = row;
                    while ((currentRow>0) && (gameMatrix[currentRow-1][col] == 1 ))
                    {
                        gameMatrix[currentRow-1][col] = gameMatrix[currentRow][col];
                        gameMatrix[currentRow][col] = 1;
                        currentRow--;
                    }
                }
    }

    private void MergeUp()
    {
        for (int col=0; col<SIZE;col++)
            for (int row = 0; row<SIZE-1;row++)
                if ((gameMatrix[row][col] != 1) && (gameMatrix[row][col] == gameMatrix[row+1][col] ))
                {
                    gameMatrix[row][col] *= 2;
                    IncreaseScore(gameMatrix[row][col]);
                    gameMatrix[row+1][col] = 1;
                }
    }

    private void ShiftDown()
    {
        for (int col=0; col<SIZE;col++)
            for (int row = SIZE-1; row>=0;row--)
                if ((row<SIZE-1) && (gameMatrix[row+1][col] == 1 ))
                {
                    int currentRow = row;
                    while ((currentRow<SIZE-1) && (gameMatrix[currentRow+1][col] == 1 ))
                    {
                        gameMatrix[currentRow+1][col] = gameMatrix[currentRow][col];
                        gameMatrix[currentRow][col] = 1;
                        currentRow++;
                    }
                }
    }

    private void MergeDown()
    {
        for (int col=0; col<SIZE;col++)
            for (int row = SIZE-1; row>0;row--)
                if ((gameMatrix[row][col] != 1) && (gameMatrix[row][col] == gameMatrix[row-1][col] ))
                {
                    gameMatrix[row][col] *= 2;
                    IncreaseScore(gameMatrix[row][col]);
                    gameMatrix[row-1][col] = 1;
                }
    }

    private void ShiftLeft()
    {
        for (int row=0; row<SIZE;row++)
            for (int col = 0; col<SIZE;col++)
                if ((col>0) && (gameMatrix[row][col-1] == 1 ))
                {
                    int currentCol = col;
                    while ((currentCol>0) && (gameMatrix[row][currentCol-1] == 1 ))
                    {
                        gameMatrix[row][currentCol-1] = gameMatrix[row][currentCol];
                        gameMatrix[row][currentCol] = 1;
                        currentCol--;
                    }
                }
    }

    private void MergeLeft()
    {
        for (int row=0; row<SIZE;row++)
            for (int col = 0; col<SIZE-1;col++)
                if ((gameMatrix[row][col] != 1) && (gameMatrix[row][col] == gameMatrix[row][col+1] ))
                {
                    gameMatrix[row][col] *= 2;
                    IncreaseScore(gameMatrix[row][col]);
                    gameMatrix[row][col+1] = 1;
                }
    }

    private void ShiftRight(){
        for (int row=0; row<SIZE;row++)
            for (int col = SIZE-1; col>=0;col--)
                if ((col<SIZE-1) && (gameMatrix[row][col+1] == 1 ))
                {
                    int currentCol = col;
                    while ((currentCol<SIZE-1) && (gameMatrix[row][currentCol+1] == 1 ))
                    {
                        gameMatrix[row][currentCol+1] = gameMatrix[row][currentCol];
                        gameMatrix[row][currentCol] = 1;
                        currentCol++;
                    }
                }
    }

    private void MergeRight()
    {
        for (int row=0; row<SIZE;row++)
            for (int col = SIZE-1; col>0;col--)
                if ((gameMatrix[row][col] != 1) && (gameMatrix[row][col] == gameMatrix[row][col-1] ))
                {
                    gameMatrix[row][col] *= 2;
                    IncreaseScore(gameMatrix[row][col]);
                    gameMatrix[row][col-1] = 1;
                }
    }
}

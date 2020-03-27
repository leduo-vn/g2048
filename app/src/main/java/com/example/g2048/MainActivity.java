package com.example.g2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.g2048.database.database;
import com.example.g2048.entities.User;

public class MainActivity extends AppCompatActivity {

    public static database gDatabase;

    public void startClicked(View view)
    {
        EditText userNameEditText = (EditText) findViewById(R.id.username);
        String username = userNameEditText.getText().toString();
        if (username.matches("")) {
            Toast.makeText(this, "Please enter username to continue", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            // If user is not existed then create User
            User user = MainActivity.gDatabase.daoUser().getUserByName(username);
            if (user == null)
            {
                Log.i("info","new");
                User newUser = new User();
                newUser.setUsername(username);
                newUser.setScore(0);
                MainActivity.gDatabase.daoUser().addUser(newUser);
            }

            //Create and Start Game Activity
            Intent intent = new Intent(this, gameActivity.class);
            intent.putExtra("USERNAME",username);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gDatabase = Room.databaseBuilder(getApplicationContext(),database.class,"userDB").allowMainThreadQueries().build();

    }
}

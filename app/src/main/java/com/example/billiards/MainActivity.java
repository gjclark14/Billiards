package com.example.billiards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.billiards.RequestCodes.*;


enum RequestCodes { BACK, PLAYER, SCORE; }


public class MainActivity extends AppCompatActivity {
    Player player;
    List<Player> players = Arrays.asList(new Player[3]);

    static int currentPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton addPlayerFab = findViewById(R.id.addPlayer);
        addPlayerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPlayerActivity.class);
                MainActivity.this.startActivityForResult(intent, PLAYER.ordinal());
                currentPlayer++;
            }
        });

        FloatingActionButton addPlayerScoreFab = findViewById(R.id.addScore);
        addPlayerScoreFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityAddPlayerScore.class);
                MainActivity.this.startActivityForResult(intent, SCORE.ordinal());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Request codes specify what activity you are referring to
        // Result code specifies the state of the result

        // Add player
        if (requestCode == PLAYER.ordinal()) {
            if(resultCode == Activity.RESULT_OK) {
                Player p = data.getParcelableExtra("result");

                assert p != null;

                if (currentPlayer > 2) {
                    Toast.makeText(this, "Cannot have more than 3 players, " +
                            "not adding the last", Toast.LENGTH_LONG).show();
                    return;
                }

                players.set(currentPlayer, p);
                Toast.makeText(this, p.getName(),Toast.LENGTH_LONG).show();
            }
            if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "RESULT CANCELLED",Toast.LENGTH_LONG).show();
            }
        }

        // Add score
        if (requestCode == SCORE.ordinal()) {
            if(resultCode == Activity.RESULT_OK) {
                Player p = data.getParcelableExtra("result");
                assert p != null;
                players.add(p);
                Toast.makeText(this, p.getName(),Toast.LENGTH_LONG).show();
            }
            if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "RESULT CANCELLED",Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.dronc;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class GroupActivity extends AppCompatActivity {

    private PlayerContainer playerContainer = new PlayerContainer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        getSupportActionBar().hide();

        Button button_minus = findViewById(R.id.button_minus);
        Button button_plus = findViewById(R.id.button_plus);
        Button button_start_group = findViewById(R.id.button_start);
        final TextView playercount = findViewById(R.id.spieler_count);
        final ListView listView = findViewById(R.id.playerList);

        //Method for getting PlayerContainer if any
        if (getIntent().hasExtra("players")) {
            this.playerContainer = (PlayerContainer) getIntent().getExtras().getSerializable("players");
            playercount.setText(playerContainer.getPlayers().size() + " Spieler");
        } else {
            this.playerContainer = new PlayerContainer();
        }

        //Setting up List with adapter
        final PlayerListAdapter playerListAdapter = new PlayerListAdapter(GroupActivity.this, R.layout.listitem_layout, playerContainer.getPlayers(), GroupActivity.this);
        listView.setAdapter(playerListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
                Intent intent = new Intent(GroupActivity.this,NewPlayerActivity.class);
                intent.putExtra("players",playerContainer);
                intent.putExtra("player",playerListAdapter.getItem(position));
                intent.putExtra("pos",position);
                startActivity(intent);
            }
        });

        //Button plus new activity add player
        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupActivity.this, NewPlayerActivity.class);
                intent.putExtra("players", playerContainer);
                startActivity(intent);
            }
        });

        //Button minus onclick delete player
        button_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerContainer.deleteLast();
                playerListAdapter.notifyDataSetChanged();
                playercount.setText(playerContainer.getPlayers().size() + " Spieler");
            }
        });

        //Button Start to Activity Settings
        button_start_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerContainer.getSize()>=2) {
                    Intent mintent = new Intent(GroupActivity.this, SettingsActivity.class);
                    mintent.putExtra("players", playerContainer);
                    startActivity(mintent);
                } else {
                    Toast.makeText(GroupActivity.this, "Bitte mindestens 2 Spieler einfügen", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Todo: remove this (only for testing)
        playercount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GroupActivity.this, "starting game without players", Toast.LENGTH_SHORT).show();
                Intent mintent = new Intent(GroupActivity.this, SettingsActivity.class);
                mintent.putExtra("players", playerContainer);
                startActivity(mintent);
            }
        });
    }
}

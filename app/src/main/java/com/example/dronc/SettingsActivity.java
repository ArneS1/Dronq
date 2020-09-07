package com.example.dronc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private PlayerContainer playerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        playerContainer = (PlayerContainer) extras.getSerializable("players");

        final SeekBar seekBar_konsum = findViewById(R.id.seekBar_konsum);
        final SeekBar seekBar_spiel = findViewById(R.id.seekBar_spiel);
        final SeekBar seekBar_sex = findViewById(R.id.seekBar_sex);
        final SeekBar seekBar_activity = findViewById(R.id.seekBar_activity);
        Button button_start_settings=findViewById(R.id.button_start_settings);
        final Switch shotMachine_switch = findViewById(R.id.switch_shotmachine);

        button_start_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, GameActivity.class);
                intent.putExtra("players",playerContainer);
                intent.putExtra("konsum", seekBar_konsum.getProgress());
                intent.putExtra("spiel", seekBar_spiel.getProgress());
                intent.putExtra("activity",seekBar_activity.getProgress());
                intent.putExtra("sex",seekBar_sex.getProgress());
                intent.putExtra("hasShotMachine",shotMachine_switch.isChecked());
                startActivity(intent);
            }
        });

    }
}

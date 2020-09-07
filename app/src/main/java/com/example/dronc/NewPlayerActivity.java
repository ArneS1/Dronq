package com.example.dronc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class NewPlayerActivity extends AppCompatActivity {

    //vars
    boolean isMale;
    private PlayerContainer playerContainer;
    private Player currentPlayer;
    private boolean hasCurrent = false;
    private boolean genderIsSet = false;
    private boolean nameIsSet;
    private boolean drinkIsSet;
    private int arrayPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player);
        getSupportActionBar().hide();

        //User Data
        final EditText editText = findViewById(R.id.editText);
        final ImageButton female_button = findViewById(R.id.imageButton);
        final ImageButton male_button = findViewById(R.id.imageButton2);
        final Switch relationship_switch = findViewById(R.id.switch2);
        final Button drink_button = findViewById(R.id.buttonDrink);
        Button button_add = findViewById(R.id.buttonAdd);
        Button button_delete = findViewById(R.id.buttonDelete);

        //Method for getting PlayerContainer if any
        if (getIntent().hasExtra("players")) {
            this.playerContainer = (PlayerContainer) getIntent().getExtras().getSerializable("players");
            arrayPosition = getIntent().getExtras().getInt("pos");
        } else {
            playerContainer = new PlayerContainer();
        }
        //Getting Current Player Info
        if (getIntent().hasExtra("player")) {
            this.currentPlayer = (Player) getIntent().getExtras().getSerializable("player");
            this.hasCurrent = true;
        }
        //Updating View with new Player Info
        if (hasCurrent) {
            editText.setText(currentPlayer.getName());
            nameIsSet = true;
            button_add.setText("done");
            //Updating gender
            if (currentPlayer.isMale()) {
                isMale = true;
                male_button.setBackgroundColor(Color.parseColor("#C0C861"));
                female_button.setBackgroundColor(Color.parseColor("#FFFFFF"));
            } else {
                isMale = false;
                female_button.setBackgroundColor(Color.parseColor("#C0C861"));
                male_button.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            genderIsSet = true;
            //Updating Relationship status
            if (currentPlayer.isRelationship()) {
                relationship_switch.setChecked(true);
            }
            drink_button.setText(currentPlayer.getDrink());
            drinkIsSet = true;
        }

        //OnClick : set gender and set Color of both buttons
        female_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMale = false;
                female_button.setBackground(getDrawable(R.drawable.roundbutton_pressed));
                male_button.setBackground(getDrawable(R.drawable.roundbutton));
                genderIsSet = true;
            }
        });

        male_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMale = true;
                male_button.setBackground(getDrawable(R.drawable.roundbutton_pressed));
                female_button.setBackground(getDrawable(R.drawable.roundbutton));
                genderIsSet = true;
            }
        });

        //Drink button
        drink_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinkIsSet = true;
                if (drink_button.getText() == "Bier") {
                    drink_button.setText("Mische");
                } else {
                    if (drink_button.getText() == "Mische") {
                        drink_button.setText("Wein");
                    } else {
                        if (drink_button.getText() == "Wein") {
                            drink_button.setText("pur");
                        } else {
                            if (drink_button.getText() == "pur"){
                                drink_button.setText("Wasser");
                            } else {
                                drink_button.setText("Bier");
                            }
                        }
                    }
                }
            }});

            //adding finished Player to PlayerContainer
                button_add.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                if (hasCurrent) {
                    playerContainer.delete(arrayPosition);
                }
                if(editText.getText().toString().trim().length() > 0) {
                    if (genderIsSet) {
                        if(drinkIsSet){
                        Player player = new Player();
                        player.setName(editText.getText().toString());
                        player.setDrink(drink_button.getText().toString());
                        player.setMale(isMale);
                        player.setRelationship(relationship_switch.isChecked());
                        playerContainer.add(player);
                        Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
                        intent.putExtra("players", playerContainer);
                        startActivity(intent);
                        } else {
                            Toast.makeText(NewPlayerActivity.this, "Please select a Drink!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(NewPlayerActivity.this, "Please select a gender!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NewPlayerActivity.this, "Please enter a name!", Toast.LENGTH_SHORT).show();
                }
            }
            });
            //Deleting current player if any
        button_delete.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                if (hasCurrent) {
                    playerContainer.delete(arrayPosition);
                }else {
                    Toast.makeText(NewPlayerActivity.this, "No Player to delete", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
                intent.putExtra("players", playerContainer);
                startActivity(intent);
            }
            });
        }
    }

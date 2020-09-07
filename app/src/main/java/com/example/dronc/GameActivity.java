package com.example.dronc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.round;

public class GameActivity extends AppCompatActivity {

    private StatementFragment statementFragment;
    private Button button_next;

    private PlayerContainer playerContainer;
    private StatementContainer statementContainer;

    private int settings_konsum;
    private int settings_game;
    private int settings_sex;
    private int settings_activity;
    private boolean hasShotMachine;

    private int hasFollowUp;
    private int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        playerContainer = (PlayerContainer) extras.getSerializable("players");
        settings_konsum = extras.getInt("konsum");
        settings_game = extras.getInt("spiel");
        settings_activity = extras.getInt("activity");
        settings_sex = extras.getInt("sex");
        hasShotMachine = extras.getBoolean("hasShotMachine");

        statementContainer = new StatementContainer();

        //Statement Thread starting -> Loading statements into Container
        final StatementThread statementThread = new StatementThread();
        statementThread.start();

        //getting button next
        button_next = findViewById(R.id.button_next);

        statementFragment = new StatementFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_statementFragment, statementFragment)
                .commit();

        //Next Button Funktion
        //TODO: remove shotmachine functionality
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shotmachine_multiplier;
                int randomboundary;
                int randomCl;
                if (statementFragment.getText().matches("(.*)Shotmachine(.*)")) {
                    shotmachine_multiplier = 1;
                    if (statementFragment.getText().matches("(.*)Ex(.*)") || statementFragment.getText().matches("(.*)ex(.*)")) {
                        shotmachine_multiplier = 2;
                    }
                    randomboundary = settings_konsum * shotmachine_multiplier;
                    if (randomboundary == 0) {
                        randomboundary += 5;
                    }
                    Random r = new Random();
                    int low = round(settings_konsum/2);
                    int high = randomboundary;
                    randomCl = r.nextInt(high-low) + low;

                    statementFragment.updateWebView("http://192.168.178.21:5000/pump" + randomCl);

                    Statement statement = StatementContainer.getNextStatement(hasFollowUp);
                    statementFragment.updateStatement(statement.getStatementTextWithNames(playerContainer, hasShotMachine) + randomCl +" cl befüllt!");
                    playerContainer.setAllNotInUse();
                    hasFollowUp = 0;
                    if (statement.isHasFollowup()) {
                        hasFollowUp = statement.getId();
                    }
                } else {
                    if (hasFollowUp > 0) {
                        Statement statement = StatementContainer.getNextStatement(hasFollowUp);
                        statementFragment.updateStatement(statement.getStatementTextWithNames(playerContainer, hasShotMachine));
                        playerContainer.setAllNotInUse();
                        hasFollowUp = 0;
                        if (statement.isHasFollowup()) {
                            hasFollowUp = statement.getId();
                        }
                    } else {
                        Statement statement = statementContainer.getRandomStatement();
                        String statementText = statement.getStatementTextWithNames(playerContainer, hasShotMachine);
                        playerContainer.setAllNotInUse();
                        while (statement.isFollowup() || statementText.matches("(.*)error(.*)")) {
                            statement = statementContainer.getRandomStatement();
                            statementText = statement.getStatementTextWithNames(playerContainer, hasShotMachine);
                            playerContainer.setAllNotInUse();
                        }

                        if (statement.isHasFollowup()) {
                            hasFollowUp = statement.getId();
                        }
                        if (counter % 50 == 0 && counter > 1) {
                            statementFragment.updateStatement("Runde vorbei! Erneut spielen?");
                            playerContainer.setAllNotInUse();
                        } else {
                            statementFragment.updateStatement(statementText);
                            playerContainer.setAllNotInUse();
                        }
                    }
                }
            }
        });

    }

    //Thread zum füllen des StatementContainers
    class StatementThread extends Thread {
        @Override
        public void run() {

            Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    try {
                        Statement statement = (Statement) msg.obj;
                        if (statement.getSettings_konsum() <= settings_konsum) {
                            if (statement.getSettings_sctivity() <= settings_activity) {
                                if (statement.getSettings_sex() <= settings_sex) {
                                    if (statement.getSettings_spiel() <= settings_game) {
                                        statementContainer.addStatement(statement);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            };

            JSONArray Json_all_statements = null;
            JSONObject Json_statement = null;
            try {
                Json_all_statements = new JSONObject(loadJSONFromAsset(GameActivity.this)).getJSONArray("drink_statements");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < Json_all_statements.length(); i++) {
                try {
                    Json_statement = Json_all_statements.getJSONObject(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Statement statement = new Statement();
                try {
                    statement.setId(Json_statement.getInt("id"));
                    statement.setStatement_text(Json_statement.getString("statement"));
                    statement.setSettings_konsum(Json_statement.getInt("category_drink"));
                    statement.setSettings_sctivity(Json_statement.getInt("category_activity"));
                    statement.setSettings_sex(Json_statement.getInt("category_sex"));
                    statement.setSettings_spiel(Json_statement.getInt("category_game"));
                    statement.setHasFollowup(Json_statement.getBoolean("hasFollowup"));
                    statement.setFollowup(Json_statement.getBoolean("isFollowup"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message completeMessage = new Message();
                completeMessage.obj = statement;
                handler.sendMessage(completeMessage);

            }
        }

        public String loadJSONFromAsset(Context context) {
            String json = null;
            try {
                InputStream is = context.getAssets().open("statements.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");

            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;

        }
    }
}

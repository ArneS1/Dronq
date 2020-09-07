package com.example.dronc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class PlayerContainer implements Serializable {
    private ArrayList<Player> players = new ArrayList<Player>();
    private Random random = new Random();

    private boolean hasMale;
    private boolean hasFemale;
    private boolean hasSingle;
    private boolean hasMaleSingle;
    private boolean hasFemaleSingle;

    private boolean hasSober;
    private boolean hasSoberMale;
    private boolean hasSoberFemale;
    private boolean hasSoberSingle;
    private boolean hasSoberSingleMale;
    private boolean hasSoberSingleFemale;


    // Getter and Setter for Playertype Variables
    public boolean isHasMale() {
        return hasMale;
    }

    public void setHasMale(boolean hasMale) {
        this.hasMale = hasMale;
    }

    public boolean isHasFemale() {
        return hasFemale;
    }

    public void setHasFemale(boolean hasFemale) {
        this.hasFemale = hasFemale;
    }

    public boolean isHasSingle() {
        return hasSingle;
    }

    public void setHasSingle(boolean hasSingle) {
        this.hasSingle = hasSingle;
    }

    public boolean isHasMaleSingle() {
        return hasMaleSingle;
    }

    public void setHasMaleSingle(boolean hasMaleSingle) {
        this.hasMaleSingle = hasMaleSingle;
    }

    public boolean isHasFemaleSingle() {
        return hasFemaleSingle;
    }

    public void setHasFemaleSingle(boolean hasFemaleSingle) {
        this.hasFemaleSingle = hasFemaleSingle;
    }

    public boolean isHasSober() {
        return hasSober;
    }

    public void setHasSober(boolean hasSober) {
        this.hasSober = hasSober;
    }

    public boolean isHasSoberMale() {
        return hasSoberMale;
    }

    public void setHasSoberMale(boolean hasSoberMale) {
        this.hasSoberMale = hasSoberMale;
    }

    public boolean isHasSoberFemale() {
        return hasSoberFemale;
    }

    public void setHasSoberFemale(boolean hasSoberFemale) {
        this.hasSoberFemale = hasSoberFemale;
    }

    public boolean isHasSoberSingle() {
        return hasSoberSingle;
    }

    public void setHasSoberSingle(boolean hasSoberSingle) {
        this.hasSoberSingle = hasSoberSingle;
    }

    public boolean isHasSoberSingleMale() {
        return hasSoberSingleMale;
    }

    public void setHasSoberSingleMale(boolean hasSoberSingleMale) {
        this.hasSoberSingleMale = hasSoberSingleMale;
    }

    public boolean isHasSoberSingleFemale() {
        return hasSoberSingleFemale;
    }

    public void setHasSoberSingleFemale(boolean hasSoberSingleFemale) {
        this.hasSoberSingleFemale = hasSoberSingleFemale;
    }

    //Method to add Player
    public void add(Player newPlayer) {

        players.add(newPlayer);

        //Playertype basic male or Female

        if (newPlayer.isMale()) {
            this.hasMale = true;
        } else {
            this.hasFemale = true;
        }

        //Playertype Single Male/Female

        if (!newPlayer.isRelationship()) {
            this.hasSingle = true;
            if (newPlayer.isMale()) {
                this.hasMaleSingle = true;
            } else {
                this.hasFemaleSingle = true;
            }
        }

        //Playertype Sober
        if (newPlayer.getDrink().equals("Wasser")) {
            this.hasSober = true;
            if (newPlayer.isMale()) {
                this.hasSoberMale = true;
                if (!newPlayer.isRelationship()) {
                    this.hasSoberSingleMale = true;
                }
            } else {
                this.hasSoberFemale = true;
                if (!newPlayer.isRelationship()) {
                    this.hasSoberSingleFemale = true;
                }
            }
        }
    }

    //Method to add Array of Players
    public void addPlayersArray(ArrayList<Player> tempPlayers) {
        for (Player p : tempPlayers) {
            this.add(p);
        }
    }

    //Methods to get all Players
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public int getSize() {
        return players.size();
    }

    //Methods for Deleting specific Players
    public void deleteLast() {
        this.players.remove(players.size() - 1);
    }

    public void delete(int p) {
        players.remove(p);
    }

    public Player getPlayer(boolean mustBeMale, boolean mustBeFemale, boolean mustBeSingle, boolean mustBeSober, boolean mustBeDrunk) {
        ArrayList<Player> potential_players = new ArrayList<>();
        for (Player p : players) {
            if (!p.isInUse()) {
                potential_players.add(p);
            }
        }
        if (mustBeMale || mustBeFemale) {
            if (mustBeMale) {
                for (Player p : potential_players) {
                    if (!p.isMale()) {
                        p.setInUse(true);
                    }
                }
            } else {
                for (Player p : potential_players) {
                    if (p.isMale()) {
                        p.setInUse(true);
                    }
                }
            }
        }
        if (mustBeSingle) {
            for (Player p : potential_players) {
                if (p.isRelationship()) {
                    p.setInUse(true);
                }
            }
        }
        if (mustBeSober) {
            for (Player p : potential_players) {
                if (!p.getDrink().equals("Wasser")) {
                    p.setInUse(true);
                }
            }
        }
        if (mustBeDrunk) {
            for (Player p : potential_players) {
                if (p.getDrink().equals("Wasser")) {
                    p.setInUse(true);
                }
            }
        }
        int removed_player_count = 0;
        for (int i = potential_players.size()-1; i>-1; i--) {
            if(potential_players.get(i).isInUse()){
                potential_players.remove(i);
                removed_player_count +=1;
            }
        }

        if(potential_players.size() != 0){
            int randomID = random.nextInt(potential_players.size());
            Player returnPlayer = potential_players.get(randomID);
            returnPlayer.setInUse(true);
            return returnPlayer;
        } else {
            Player errorPlayer = new Player();
            errorPlayer.setName("error");
            return errorPlayer;
        }
    }

    //Method for Setting everyones in-Use-Status to false
    public void setAllNotInUse() {
        for (Player player : players) {
            player.setInUse(false);
        }
    }
}
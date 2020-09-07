package com.example.dronc;

public class Statement {

    private int id;

    private String statement_text;

    private boolean isFollowup;
    private boolean hasFollowup;

    private int settings_konsum;
    private int settings_spiel;
    private int settings_sex;
    private int settings_sctivity;

    private boolean hasAll;
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

    //Getter und Setter

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isHasAll() {
        return hasAll;
    }

    public void setHasAll(boolean hasAll) {
        this.hasAll = hasAll;
    }

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

    public String getStatement_text() {
        return statement_text;
    }

    public void setStatement_text(String statement_text) {
        this.statement_text = statement_text;
    }

    public boolean isFollowup() {
        return isFollowup;
    }

    public void setFollowup(boolean followup) {
        isFollowup = followup;
    }

    public boolean isHasFollowup() {
        return hasFollowup;
    }

    public void setHasFollowup(boolean hasFollowup) {
        this.hasFollowup = hasFollowup;
    }

    public int getSettings_konsum() {
        return settings_konsum;
    }

    public void setSettings_konsum(int settings_konsum) {
        this.settings_konsum = settings_konsum;
    }

    public int getSettings_spiel() {
        return settings_spiel;
    }

    public void setSettings_spiel(int settings_spiel) {
        this.settings_spiel = settings_spiel;
    }

    public int getSettings_sex() {
        return settings_sex;
    }

    public void setSettings_sex(int settings_sex) {
        this.settings_sex = settings_sex;
    }

    public int getSettings_sctivity() {
        return settings_sctivity;
    }

    public void setSettings_sctivity(int settings_sctivity) {
        this.settings_sctivity = settings_sctivity;
    }

    //Get the String of the Statement with Names replaced
    public String getStatementTextWithNames(PlayerContainer playerContainer, boolean hasShotMachine) {
        String returnText = "";
        String[] words = this.statement_text.split("#");
        Player currentPlayer;

        //general replacer
        for (String part_of_statement : words) {
            boolean mustBeMale = false;
            boolean mustBeFemale = false;
            boolean mustBeSingle = false;
            boolean mustBeSober = false;
            boolean mustBeDrunk = false;
            if (part_of_statement == "#") {
                part_of_statement = "";
            }
            if(part_of_statement.equals("Shotmachine")){
                if(!hasShotMachine){
                    part_of_statement = "error";
                }
            }
            if (part_of_statement.matches("(.*)player(.*)")) {
                if (part_of_statement.matches("(.*)Male(.*)") && !part_of_statement.matches("(.*)Female(.*)")) {
                    mustBeMale = true;
                }
                if (part_of_statement.matches("(.*)Female(.*)")) {
                    mustBeFemale = true;
                }
                if (part_of_statement.matches("(.*)Single(.*)")) {
                    mustBeSingle = true;
                }
                if (part_of_statement.matches("(.*)Sober(.*)")) {
                    mustBeSober = true;
                }
                if (part_of_statement.matches("(.*)Drunk(.*)")) {
                    mustBeDrunk = true;
                }
                part_of_statement = playerContainer.getPlayer(mustBeMale,mustBeFemale,mustBeSingle,mustBeSober,mustBeDrunk).getName();
            }
            returnText = returnText + part_of_statement;
        }
        return returnText;
    }
}

package com.example.dronc;

import java.util.ArrayList;
import java.util.Random;

public class StatementContainer {

    private static ArrayList<Statement> statements = new ArrayList<Statement>();
    private static ArrayList<Integer> usedStatement_IDs = new ArrayList<Integer>();

    private Random random = new Random();

    public void addStatement(Statement newStatement) {
        statements.add(newStatement);
    }

    public Statement getRandomStatement() {
        int randomID = random.nextInt(statements.size());
        while (usedStatement_IDs.contains(randomID) ) {
            if(usedStatement_IDs.size()==statements.size()){
                Statement statement = new Statement();
                statement.setStatement_text("Spiel vorbei! du hast genug gesoffen! ( joke, aber es gibt keine Fragen mehr :( )");
                return statement;
            }
            randomID = random.nextInt(statements.size());
        }
        Statement currentStatement = statements.get(randomID);
        usedStatement_IDs.add(randomID);
        return currentStatement;
    }

    public static Statement getNextStatement(int index){
        Statement currentStatement = new Statement();
        currentStatement.setStatement_text("fail");
        for (int i = 0; i < statements.size() ; i++) {
            if(statements.get(i).getId() == index){
                currentStatement = statements.get(i+1);
            }
        }
        return currentStatement;
    }
}

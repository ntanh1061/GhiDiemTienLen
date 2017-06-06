package com.nonamejx.ghidiemtienlen.model;

import com.nonamejx.ghidiemtienlen.common.Constants;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by noname
 * on 22/10/2016.
 */
public class Game implements Serializable {
    @Getter
    private String gameId;

    @Getter
    @Setter
    private int numberOfPlayers;

    @Getter
    @Setter
    private int numberOfTurns;

    @Getter
    @Setter
    private String[] playerNames;

    @Getter
    @Setter
    private int[][] result;

    public Game(int numberOfTurns) {
        this.gameId = UUID.randomUUID().toString();
        this.numberOfPlayers = Constants.NUMBER_OF_PLAYERS;
        this.numberOfTurns = numberOfTurns;
        this.result = new int[numberOfTurns][numberOfPlayers];
    }

    public Game(String[] playerNames, int numberOfTurns) {
        this.gameId = UUID.randomUUID().toString();
        this.numberOfPlayers = Constants.NUMBER_OF_PLAYERS;
        this.numberOfTurns = numberOfTurns;
        this.playerNames = playerNames;
        this.result = new int[numberOfTurns][numberOfPlayers];
    }

    public int[] calculateFinalResult() {
        int[] finalResult = new int[4];
        for (int i = 0; i < this.numberOfTurns; i++) {
            for (int j = 0; j < this.numberOfPlayers; j++) {
                finalResult[j] += this.result[i][j];
            }
        }
        return finalResult;
    }

    public GameRealmObject toGameRealmObject() {
        return new GameRealmObject(this);
    }
}

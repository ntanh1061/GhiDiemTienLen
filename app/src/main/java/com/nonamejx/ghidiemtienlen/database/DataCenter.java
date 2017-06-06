package com.nonamejx.ghidiemtienlen.database;

import com.nonamejx.ghidiemtienlen.model.Game;
import com.nonamejx.ghidiemtienlen.model.GameRealmObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noname
 * on 10/11/2016.
 */
public class DataCenter implements IDataCenter {
    private static DataCenter instance;
    private List<Game> games;

    private DataCenter() {
        this.games = convert(RealmHelper.getInstance().getAllGameRealmObjects());
    }

    public static DataCenter getInstance() {
        if (instance == null) {
            instance = new DataCenter();
        }
        return instance;
    }

    private List<Game> convert(List<GameRealmObject> gameRealmObjects) {
        List<Game> games = new ArrayList<>();
        for (GameRealmObject gameRealmObject : gameRealmObjects) {
            games.add(gameRealmObject.toGame());
        }
        return games;
    }

    @Override
    public List<Game> getAllGames() {
        return games;
    }

    @Override
    public Game getGame(String gameId) {
        for (Game game : this.games) {
            if (game.getGameId().equals(gameId)) {
                return game;
            }
        }
        return null;
    }

    @Override
    public void addGame(Game game) {
        this.games.add(game);
        RealmHelper.getInstance().addGameRealmObject(game.toGameRealmObject());
    }

    @Override
    public void deleteGame(Game game) {
        this.games.remove(game);
        RealmHelper.getInstance().deleteGameRealmObject(RealmHelper.getInstance().getGame(game.getGameId()));
    }

    @Override
    public void deleteAllGames() {
        this.games.clear();
        RealmHelper.getInstance().deleteAllGameRealmObjects();
    }
}
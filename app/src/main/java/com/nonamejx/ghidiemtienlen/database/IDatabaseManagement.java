package com.nonamejx.ghidiemtienlen.database;

import com.nonamejx.ghidiemtienlen.model.GameRealmObject;

import java.util.List;

/**
 * Created by noname
 * on 22/10/2016.
 */
public interface IDatabaseManagement {
    List<GameRealmObject> getAllGameRealmObjects();
    void addGameRealmObject(GameRealmObject gameRealmObject);
    void deleteAllGameRealmObjects();
    void deleteGameRealmObject(GameRealmObject gameRealmObject);
    GameRealmObject getGame(String gameId);
}

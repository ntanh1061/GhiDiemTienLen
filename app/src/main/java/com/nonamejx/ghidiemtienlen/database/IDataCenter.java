package com.nonamejx.ghidiemtienlen.database;

import com.nonamejx.ghidiemtienlen.model.Game;

import java.util.List;

/**
 * Created by noname
 * on 10/11/2016.
 */
public interface IDataCenter {
    List<Game> getAllGames();
    Game getGame(String gameId);
    void addGame(Game game);
    void deleteGame(Game game);
    void deleteAllGames();
}

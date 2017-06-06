package com.nonamejx.ghidiemtienlen.model;

import com.google.gson.Gson;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by noname
 * on 10/11/2016.
 */
public class GameRealmObject extends RealmObject {

    @Ignore
    Gson gson = new Gson();
    @PrimaryKey
    @Getter
    private String gameId;
    @Getter
    @Setter
    private String json;

    public GameRealmObject() {

    }

    public GameRealmObject(Game game) {
        this.gameId = game.getGameId();
        this.json = gson.toJson(game);
    }

    public Game toGame() {
        return gson.fromJson(this.json, Game.class);
    }
}

package com.nonamejx.ghidiemtienlen.database;

import com.nonamejx.ghidiemtienlen.model.GameRealmObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by noname
 * on 22/10/2016.
 */
public class RealmHelper implements IDatabaseManagement {
    private Realm mRealm;

    private static RealmHelper mInstance;

    private RealmHelper() {
        mRealm = Realm.getDefaultInstance();
    }

    public static RealmHelper getInstance() {
        if (mInstance == null) {
            mInstance = new RealmHelper();
        }
        return mInstance;
    }

    @Override
    public List<GameRealmObject> getAllGameRealmObjects() {
        return mRealm.where(GameRealmObject.class).findAll();
    }

    @Override
    public void addGameRealmObject(GameRealmObject gameRealmObject) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(gameRealmObject);
        mRealm.commitTransaction();
    }

    @Override
    public void deleteAllGameRealmObjects() {
        RealmResults<GameRealmObject> games = mRealm.where(GameRealmObject.class).findAll();
        mRealm.beginTransaction();
        games.clear();
        mRealm.commitTransaction();
    }

    @Override
    public void deleteGameRealmObject(GameRealmObject gameRealmObject) {
        mRealm.beginTransaction();
        gameRealmObject.removeFromRealm();
        mRealm.commitTransaction();
    }

    @Override
    public GameRealmObject getGame(String gameId) {
        return mRealm.where(GameRealmObject.class).equalTo("gameId", gameId).findFirst();
    }
}

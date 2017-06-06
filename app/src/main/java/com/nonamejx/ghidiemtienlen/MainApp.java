package com.nonamejx.ghidiemtienlen;

import android.app.Application;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by noname
 * on 22/10/2016.
 */
@EApplication
public class MainApp extends Application {

    @AfterInject
    public void init() {
        // Configure Realm for the application
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        // Realm.deleteRealm(realmConfiguration); // Clean the old realm
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}

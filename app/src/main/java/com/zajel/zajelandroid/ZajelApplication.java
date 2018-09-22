package com.zajel.zajelandroid;

import android.app.Application;

import com.cloudinary.android.MediaManager;
import com.zajel.zajelandroid.BuildConfig;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;




public class ZajelApplication extends Application {
    private final static String REALM_FILE_NAME = BuildConfig.REALM_FILE_NAME;
    private final static int REALM_FILE_VERSION = BuildConfig.REALM_FILE_VERSION;

    @Override
    public void onCreate() {
        super.onCreate();


        Map config1 = new HashMap();
        config1.put("cloud_name", "zajel");
        config1.put("api_key","484384578824121");
        config1.put("api_secret","IXoBI4dVMV8KX-IlBQeHaSeO0eU");
        MediaManager.init(this, config1);
        // Initialize Realm
        Realm.init(this);
        PreferenceManager.initializeInstance(this);
// Get a Realm instance for this thread
        // The RealmConfiguration is created using the builder pattern.
// The Realm file will be located in Context.getFilesDir() with name "myrealm.realm"
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(REALM_FILE_NAME)
                .schemaVersion(REALM_FILE_VERSION)
                .deleteRealmIfMigrationNeeded()
//                .migration(new MyMigration())
                .build();
// Use the config
        Realm.setDefaultConfiguration(config);
    }
}

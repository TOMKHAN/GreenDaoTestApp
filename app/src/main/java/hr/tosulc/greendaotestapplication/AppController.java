package hr.tosulc.greendaotestapplication;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

import hr.tosulc.greendaotestapplication.daoclasses.DaoMaster;
import hr.tosulc.greendaotestapplication.daoclasses.DaoSession;

/**
 * Created by tomkan on 7.5.17..
 */

public class AppController extends Application {

    DaoMaster.DevOpenHelper helper;
    DaoMaster daoMaster;
    public static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        // do this once, for example in your Application class
        helper = new DaoMaster.DevOpenHelper(this, "greendao_db", null);
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();

        JodaTimeAndroid.init(this);

    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

}

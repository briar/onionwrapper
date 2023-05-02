package org.briarproject.onionwrapper;

import android.app.Application;

public class AndroidLocationUtilsFactory {

    public static AndroidLocationUtils createAndroidLocationUtils(Application app) {
        return new AndroidLocationUtils(app);
    }

}

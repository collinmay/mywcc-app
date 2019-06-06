package edu.whatcom.mywcc;

import com.mapbox.mapboxsdk.Mapbox;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // need to do this before any layouts are inflated
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));
    }
}

package net.stupidiot.pacekeeper;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PacekeeperService extends Service
{
    private static final String TAG = PacekeeperService.class.toString();

    public PacekeeperService()
    {

    }


    @Override
    public void onCreate()
    {
        Log.d(TAG, "Service created");
    }

    @Override
    public void onDestroy()
    {
        Log.d(TAG, "Service destroyed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG, "Service started");
        return super.onStartCommand(intent, flags, startId);
    }

}

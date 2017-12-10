package mdpcw2.mytracker.non_activity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class TrackerService extends Service {

    //Global variables
    private static final int ID = 100; //Permission Request ID
    NotificationCompat.Builder notification; //Create a notification object

    public TrackerService() {
    }

    private void init(){

    }

    private void startNoti(){}

    private void startTracker(){}

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //onCreate lifecycle
    @Override
    public void onCreate() {
        super.onCreate();
        init();
        Log.d("MyTracker","@@MyTrackerService created");
    }

    /*
     //This method handle incoming intent //onStartCommand passed to here eventually
     @Override
     protected void onHandleIntent(Intent intent){

     }
     */


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyTracker","@@MyTrackerService onStartCommand");
        startNoti();
        Log.d("MyTracker","@@MyTrackerService onStartCommand: Notification started");
        startTracker();
        Log.d("MyTracker","@@MyTrackerService onStartCommand: Tracker started");
        return super.onStartCommand(intent, flags, startId); //try comment out? +return
        //return START_STICKY;
    }

    //onDestroy lifecycle
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyTracker","@@MyTrackerService destroyed");
    }
}

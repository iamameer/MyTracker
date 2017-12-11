package mdpcw2.mytracker.non_activity;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class TrackerService extends Service {

    //Global variables
    private static final int ID = 100; //Permission Request ID
    NotificationCompat.Builder notification; //Create a notification object

    private LocationListener listener;
    private LocationManager locationManager;

    public TrackerService() {
    }

    private void init(){

    }

    private void startNoti(){}

    //https://www.youtube.com/watch?v=lvcGh2ZgHeA
    @SuppressLint("MissingPermission")
    private void startTracker(){
        Log.d("MyTracker","@@MyTrackerService in startTracker");
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("MyTracker","@@MyTrackerService onLocationChanged");
                Intent intent = new Intent("location_update");
                intent.putExtra("coordinates",location.getLongitude()+"//"+location.getLatitude());
                //TODO: walk or run
                sendBroadcast(intent);
                Toast.makeText(getApplicationContext(),"coordinates: "+location.getLongitude()+"//"+location.getLatitude(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("MyTracker","@@MyTrackerService onStatusChanged");
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d("MyTracker","@@MyTrackerService onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d("MyTracker","@@MyTrackerService onProviderDisabled");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };
        Log.d("MyTracker","@@MyTrackerService getSystemService");
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Log.d("MyTracker","@@MyTrackerService requestLocationUpdates");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,0,listener);
    }

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

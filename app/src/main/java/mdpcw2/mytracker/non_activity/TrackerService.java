/*
 * TrackerService   : Service class to handle background tasks
 *
 * Methods          :  init()         : initialize variable
 *                     startNoti()    : starts a notification
 *                     startTracker() : starts GPS listener
 *                     distance()     : return distance from Latitude and Longitude
 *                     steps()        : calculate steps from distance
 *                     calory()       : calculate calory from distance
 */

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
import android.util.Log;

public class TrackerService extends Service {

    //Global variables
    private static final int ID = 100; //Permission Request ID

    private LocationListener listener;
    private LocationManager locationManager;

    private double initLong,initLat, newLong, newLat;
    private boolean isChanged;
    private boolean canStart;

    private float distance;
    private double steps, calory;

    //empty constructor
    public TrackerService() {
    }

    //initializes variable
    private void init(){
        isChanged = false;
        canStart = false;
    }

    //method to start a GPS Listener
    //https://www.youtube.com/watch?v=lvcGh2ZgHeA
    @SuppressLint("MissingPermission")
    private void startTracker(){
        Log.d("MyTracker","@@MyTrackerService in startTracker");
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("MyTracker","@@MyTrackerService onLocationChanged");

                //initialize if first time location change or next concurrent
                if(isChanged){
                    initLong = newLong;
                    initLat = newLat;
                }
                newLong = location.getLongitude();
                newLat = location.getLatitude();

                distance = distance + distance(initLong,initLat,newLong,newLat);
                Log.d("MyTracker","$$Distance: "+distance);

                steps = steps + steps(distance);
                Log.d("MyTracker","$$Steps: "+steps);

                calory = calory + calory(distance);
                Log.d("MyTracker","$$Calory: "+calory);

                Intent intent = new Intent("location_update");
                intent.putExtra("coordinates",location.getLongitude()+"//"+location.getLatitude());
                intent.putExtra("long",location.getLongitude());
                intent.putExtra("lat",location.getLatitude());
                intent.putExtra("distance",Math.round(distance));
                intent.putExtra("steps",Math.round(steps));
                intent.putExtra("calory",Math.round(calory));
                if(canStart){
                    sendBroadcast(intent);
                }
                isChanged = true;
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
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        initLong = location.getLongitude();
        initLat = location.getLatitude();
        Log.d("MyTracker","**initLong: "+initLong+" || **initLat: "+initLat);
    }

    //Method to calculate distance
    //https://stackoverflow.com/questions/8410787/android-how-to-get-estimated-drive-time-from-one-place-to-another/8410867#8410867
    private float distance(double long1,double lat1,double long2, double lat2){
        Location location1 = new Location("");
        location1.setLatitude(lat1);
        location1.setLongitude(long1);

        Location location2 = new Location("");
        location2.setLatitude(lat2);
        location2.setLongitude(long2);

        return (location1.distanceTo(location2));
    }

    //Method to calculate steps
    //http://www.kylesconverter.com/length/steps-to-meters
    private double steps(float distance){
        return distance * 1.31;
    }

    //Method to calculate calories
    //https://www.runnersworld.com/tools/calories-burned-calculator
    private double calory(float distance){
        return distance * 0.104;
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
        listener = null;
        Log.d("MyTracker","@@MyTrackerService created");
        canStart = true;
    }

    /*
     //This method handle incoming intent //onStartCommand passed to here eventually
     @Override
     protected void onHandleIntent(Intent intent){
     }
     */

    //method that execute once service running
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyTracker","@@MyTrackerService onStartCommand");
        startTracker();
        Log.d("MyTracker","@@MyTrackerService onStartCommand: Tracker started");
        return super.onStartCommand(intent, flags, startId);
    }

    //onDestroy lifecycle
    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
        locationManager = null;
        stopSelf();
        Log.d("MyTracker","@@MyTrackerService destroyed");
    }
}

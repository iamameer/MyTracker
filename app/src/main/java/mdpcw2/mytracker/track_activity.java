/*
 * track_activity : Activity that starts TrackerService and handle the UI for broadcast receiver
 *
 * Methods          :  init()             : initialize variable
 *                     setEvents()        : setting up listeners
 *                     check_permission() : checking device permission
 *                     startMyService()   : start TrackerService
 *                     stopMyService()    : stop TrackerService
 *                     update()           : handle UI via broadcast receiver
 *                     startTimer()       : starts chronometer
 *                     getTime()          : returns time from the chronometer
 *                     startNoti()        : launch a notification
 *
 *                     onRequestPermission() : Handles device permission
 *                     onUserLeaveHint()     : Handles notification
 */

package mdpcw2.mytracker;

import android.Manifest;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

import mdpcw2.mytracker.non_activity.ActivityContract;
import mdpcw2.mytracker.non_activity.TrackerService;

public class track_activity extends AppCompatActivity {

    //Global Variables
    private static final String CHANNEL_ID = "TrackNotification"; //notification channel ID
    private static final int NOTI_ID = 88; //notification ID
    private static final int PERM_ID = 99;
    private Button btnTrackStartStop;
    private ProgressBar progressBar;
    private TextView txtTrackDistance,txtTrackSteps, txtTrackCalory, txtTrackDate, txtTrackTimer;
    private Chronometer chronometer;

    private String longitude,latitude;
    private String distance,steps,calory;

    private BroadcastReceiver trackerReceiver; //handles intent broadcast
    NotificationCompat.Builder notification; //Create a notification object

    //Initialising variables
    private void init(){
        btnTrackStartStop = findViewById(R.id.btnTrackStartStop);
        btnTrackStartStop.setText(R.string.start);
        btnTrackStartStop.setEnabled(false);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        txtTrackDistance = findViewById(R.id.txtTrackDistance);
        txtTrackSteps = findViewById(R.id.txtTrackSteps);
        txtTrackCalory = findViewById(R.id.txtTrackCalory);
        txtTrackDate = findViewById(R.id.txtTrackDate);
        txtTrackTimer = findViewById(R.id.txtTrackTimer);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setVisibility(View.INVISIBLE);

        //Get date
        //https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        txtTrackDate.setText(df.format(c.getTime()));

        check_permission();
    }

    //Setting up events
    private void setEvents(){
        btnTrackStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnTrackStartStop.getText().toString().equals("START")){
                    progressBar.setVisibility(View.VISIBLE);
                    txtTrackCalory.setText(R.string.initCalory);
                    txtTrackTimer.setText(R.string.initTimer);
                    txtTrackDistance.setText(R.string.initDistance);
                    txtTrackSteps.setText(R.string.empty);
                    btnTrackStartStop.setText(R.string.stop);
                    startMyService();
                    startTimer();
                }else if(btnTrackStartStop.getText().toString().equals("STOP")){
                    progressBar.setVisibility(View.INVISIBLE);
                    btnTrackStartStop.setText(R.string.start);
                    stopMyService();
                    chronometer.stop();
                    if (!distance.equals("0 m")){
                        Intent intent = new Intent(getApplicationContext(),done_activity.class);
                        intent.putExtra("steps",steps);
                        intent.putExtra("distance",distance);
                        intent.putExtra("timer",txtTrackTimer.getText().toString());
                        intent.putExtra("calory",calory);
                        startActivity(intent);
                        finish();
                    }
                    chronometer.setBase(SystemClock.elapsedRealtime());
                }
            }
        });

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                txtTrackTimer.setText(getTime((SystemClock.elapsedRealtime() - chronometer.getBase())/1000));
            }
        });
    }

    //Method to check permission
    private void check_permission(){
        if (ContextCompat.checkSelfPermission(track_activity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(track_activity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(track_activity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) &&
                ActivityCompat.shouldShowRequestPermissionRationale(track_activity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)){
                ActivityCompat.requestPermissions(track_activity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERM_ID);
                ActivityCompat.requestPermissions(track_activity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERM_ID);
            }else{
                ActivityCompat.requestPermissions(track_activity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERM_ID);
                ActivityCompat.requestPermissions(track_activity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERM_ID);
            }
        }else{btnTrackStartStop.setEnabled(true);}
    }

    //Method to start service
    private void startMyService(){
        Intent intent = new Intent(getApplicationContext(), TrackerService.class);
        startService(intent);
    }

    //Method to stop service
    private void stopMyService(){
        Intent intent = new Intent(getApplicationContext(), TrackerService.class);
        stopService(intent);
    }

    //Method to update the stats
    private void update(){
        Log.d("MyTracker","***Longitude: "+longitude+"|| ***Latitude: "+latitude);
        String sDistance = distance + " m";
        String sCalory = calory + " kCal";
        txtTrackDistance.setText(sDistance);
        txtTrackSteps.setText(steps);
        txtTrackCalory.setText(sCalory);
    }

    //Method to handle the timer
    private void startTimer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    //This method convert milliseconds to minute and seconds (int progress)
    //and return in String value, to be displayed in TextView
    private String getTime(long time){
        long hour,min,sec;
        String hourS, minS,secS;

        if (time <60){   //under 1 minute
            hour = 0;
            min = 0;
            sec = time;
        }else{              //more than 1 minute
            hour = (time) / 3600;
            min = (time) / 60;
            sec = (time) % 60;
        }

        //adding 0 at the beginning to please the eyes
        if (hour<0){hourS = "0"+hour;}else{hourS = String.valueOf(hour);}
        if (min<0){minS = "0"+min;}else{minS = String.valueOf(min);}
        if (sec <10){secS = "0"+sec;}else{secS = String.valueOf(sec);}

        return hourS+"h:"+minS+"m:"+secS+"s";
    }

    //method to start a notification
    private void startNoti(){
        notification = new NotificationCompat.Builder(this,CHANNEL_ID);
        notification.setAutoCancel(true);

        //setting up notification
        notification.setColor(Color.rgb(144, 99, 160));
        notification.setSmallIcon(R.drawable.runner);
        notification.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.runner));
        notification.setTicker("MyTracker: "+String.valueOf(distance)+" m");
        notification.setContentTitle("MyTracker: "+String.valueOf(distance)+" m");
        notification.setContentText("Steps: "+String.valueOf(steps)+" | Calories: "+String.valueOf(calory));
        notification.setStyle(new NotificationCompat.BigTextStyle());

        //return to track_activity if clicked
        Intent intent = new Intent(getApplicationContext(),track_activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //launch the notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTI_ID,notification.build());
        Log.d("MyTracker","Notification via TrackerService");
    }

    //this method return an activity state
    //https://stackoverflow.com/questions/22511018/check-if-service-is-running-from-a-broadcast-receiver
    private boolean isServiceRunning(Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)){
            if (TrackerService.class.getName().equals(serviceInfo.service.getClassName())){
                return  true;
            }
        }
        return false;
    }

    //Activity Lifecycle onCreate()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_activity);

        init();
        setEvents();
        Log.d("MyTracker","=TrackActivity onCreate()");

        trackerReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try{
                    longitude = intent.getExtras().get("long").toString();
                    latitude = intent.getExtras().get("lat").toString();
                    distance = intent.getExtras().get("distance").toString();
                    steps = intent.getExtras().get("steps").toString();
                    calory = intent.getExtras().get("calory").toString();
                    update();
                }catch (Exception e){
                    Log.d("MyTracker","***Fail to parse INT from STRING");
                    Toast.makeText(getApplicationContext(),"Fail to parse INT from STRING",Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    //Activity Lifecycle onStart()
    @Override
    protected void onStart(){
        super.onStart();
        Log.d("MyTracker","=TrackActivity onStart()");
    }

    //Activity Lifecycle onRestart()
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("MyTracker","=TrackActivity onRestart()");
    }

    //Activity Lifecycle onResume()
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("MyTracker","=TrackActivity onResume() | isServiceRunning: "+isServiceRunning(getApplicationContext()));
        registerReceiver(trackerReceiver,new IntentFilter("location_update"));
        if (isServiceRunning(getApplicationContext())){
            progressBar.setVisibility(View.VISIBLE);
            btnTrackStartStop.setText(R.string.stop);
        }
    }

    //Activity Lifecycle onPause()
    @Override
    protected void onPause(){
        super.onPause();
        Log.d("MyTracker","=TrackActivity onPause()");
    }

    //Activity Lifecycle onStop()
    @Override
    protected void onStop(){
        super.onStop();
        Log.d("MyTracker","=TrackActivity onStop()");
    }

    //Activity Lifecycle onDestroy()
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("MyTracker","=TrackActivity onDestroy()");
        if (trackerReceiver != null){
            unregisterReceiver(trackerReceiver);
        }
    }

    //Method overriding - during listing and permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults){
        if(requestCode == PERM_ID){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED){
                btnTrackStartStop.setEnabled(true);
            }else{
                check_permission();
            }
        }
    }

    //starts noti when a user pressed Home key
    //also this method handle onBackPressed()
    @Override
    public void onUserLeaveHint(){

        //only starts noti if service running in background
        if (isServiceRunning(getApplicationContext())){
            try{
                startNoti();
                Log.d("MyTracker","@@onUserLeaveHint: Notification started");
            }catch (Exception e){
                Log.e("MyTracker",e.toString());
            }
        }
        Log.d("MyTracker","MainActivity onUserLeaveHint");
    }
}

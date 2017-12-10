package mdpcw2.mytracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import mdpcw2.mytracker.non_activity.TrackerService;

public class track_activity extends AppCompatActivity {

    //Global Variables
    private static final int PERM_ID = 99;
    private Button btnTrackStartStop;
    private ProgressBar progressBar;

    //Init
    private void init(){
        btnTrackStartStop = findViewById(R.id.btnTrackStartStop);
        btnTrackStartStop.setText(R.string.start);
        btnTrackStartStop.setEnabled(false);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        check_permission();
    }

    //Setting up events
    private void setEvents(){
        btnTrackStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnTrackStartStop.getText().toString().equals("START")){
                    progressBar.setVisibility(View.VISIBLE);
                    btnTrackStartStop.setText(R.string.stop);
                    startMyService();
                }else if(btnTrackStartStop.getText().toString().equals("STOP")){
                    progressBar.setVisibility(View.INVISIBLE);
                    btnTrackStartStop.setText(R.string.start);
                    stopMyService();
                    Intent intent = new Intent(getApplicationContext(),done_activity.class);
                    startActivity(intent);
                }
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
        //TODO: check if thread in here, or in service
        Intent intent = new Intent(getApplicationContext(), TrackerService.class);
        startService(intent);
    }

    //Method to stop service
    private void stopMyService(){
        Intent intent = new Intent(getApplicationContext(), TrackerService.class);
        stopService(intent);
    }

    //Activity Lifecycle onCreate()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_activity);

        init();
        setEvents();
        Log.d("MyTracker","=TrackActivity onCreate()");
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
        Log.d("MyTracker","=TrackActivity onResume()");
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
}

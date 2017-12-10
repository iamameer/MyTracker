package mdpcw2.mytracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class track_activity extends AppCompatActivity {

    //Global Variables
    private Button btnTrackStartStop;

    //Init
    private void init(){
        btnTrackStartStop = findViewById(R.id.btnTrackStartStop);
    }

    //Setting up events
    private void setEvents(){
        btnTrackStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO if start/stop bla2
                Intent intent = new Intent(getApplicationContext(),done_activity.class);
                startActivity(intent);
            }
        });
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
}

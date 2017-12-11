package mdpcw2.mytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class done_activity extends AppCompatActivity {

    //Global Variables
    private TextView txtDoneSteps, txtDoneCalory,txtDoneTimer,txtDoneDistance;
    private Button btnDoneMainMenu;

    private String steps,calory,timer,distance;

    //Init
    private void init(){
        txtDoneSteps = findViewById(R.id.txtDoneSteps);
        txtDoneCalory = findViewById(R.id.txtDoneCalory);
        txtDoneTimer = findViewById(R.id.txtDoneTimer);
        txtDoneDistance = findViewById(R.id.txtDoneDistance);

        steps = getIntent().getExtras().get("steps").toString()+" [90%] (Best: 9999)";
        calory = getIntent().getExtras().get("calory").toString()+" [90%] (Best: 9999)";
        distance = getIntent().getExtras().get("distance").toString()+" [90%] (Best: 9999)";

        txtDoneSteps.setText(steps);
        txtDoneCalory.setText(calory);
        //txtDoneTimer
        txtDoneDistance.setText(distance);

        btnDoneMainMenu = findViewById(R.id.btnDoneMainMenu);
    }

    //Setting up events
    private void setEvents(){
        btnDoneMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //Activity Lifecycle onCreate()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_activity);

        init();
        setEvents();
        Log.d("MyTracker","=DoneActivity onCreate()");
    }

    //Activity Lifecycle onStart()
    @Override
    protected void onStart(){
        super.onStart();
        Log.d("MyTracker","=DoneActivity onStart()");
    }

    //Activity Lifecycle onRestart()
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("MyTracker","=DoneActivity onRestart()");
    }

    //Activity Lifecycle onResume()
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("MyTracker","=DoneActivity onResume()");
    }

    //Activity Lifecycle onPause()
    @Override
    protected void onPause(){
        super.onPause();
        Log.d("MyTracker","=DoneActivity onPause()");
    }

    //Activity Lifecycle onStop()
    @Override
    protected void onStop(){
        super.onStop();
        Log.d("MyTracker","=DoneActivity onStop()");
    }

    //Activity Lifecycle onDestroy()
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("MyTracker","=DoneActivity onDestroy()");
    }
}

package mdpcw2.mytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import mdpcw2.mytracker.non_activity.Activities;
import mdpcw2.mytracker.non_activity.DBHelperActivity;

public class done_activity extends AppCompatActivity {

    //Global Variables
    private TextView txtDoneSteps, txtDoneCalory,txtDoneTimer,txtDoneDistance;
    private Button btnDoneMainMenu;

    private String steps,calory,timer,distance;
    private String sSteps,sCalory,sTimer,sDistance;
    private String bStep, bCalory,bDistance;

    //Init
    private void init(){
        txtDoneSteps = findViewById(R.id.txtDoneSteps);
        txtDoneCalory = findViewById(R.id.txtDoneCalory);
        txtDoneTimer = findViewById(R.id.txtDoneTimer);
        txtDoneDistance = findViewById(R.id.txtDoneDistance);

        steps = getIntent().getExtras().get("steps").toString();
        calory = getIntent().getExtras().get("calory").toString();
        timer = getIntent().getExtras().get("timer").toString();
        distance = getIntent().getExtras().get("distance").toString();
        add();

        getBest();
        sSteps = getIntent().getExtras().get("steps").toString()+" \n(Best: "+bStep+")";
        sCalory = getIntent().getExtras().get("calory").toString()+" \n(Best: "+bCalory+")";
        sTimer = getIntent().getExtras().get("timer").toString();
        sDistance = getIntent().getExtras().get("distance").toString()+" \n(Best: "+bDistance+")";

        txtDoneSteps.setText(sSteps);
        txtDoneCalory.setText(sCalory);
        txtDoneTimer.setText(sTimer);
        txtDoneDistance.setText(sDistance);

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

    //This method add the new record into the database
    private void add(){
        Log.d("MyTracker","=DoneActivity Adding new record into database");
        DBHelperActivity dbHelperActivity = new DBHelperActivity(this,null,null,1);

        //Get date
        //https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Activities activities = new Activities(df.format(c.getTime()),steps,distance,timer,calory);

        try{
           dbHelperActivity.addActivity(activities);
            Log.d("MyTracker","=DoneActivity: Successfully added a new record");
            Toast.makeText(getApplicationContext(),"New record successfully added",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.d("MyTracker",e.toString());
            Log.d("MyTracker","=DoneActivity: Error adding new record");
            Toast.makeText(getApplicationContext(),"Error adding new record",Toast.LENGTH_SHORT).show();
        }
    }

    //This method retrieve the best record
    private void getBest(){
        Log.d("MyTracker","=DoneActivity Retrieving the best record");
        DBHelperActivity dbHelperActivity = new DBHelperActivity(this,null,null,1);

        bStep = dbHelperActivity.findActivityBest("step");
        bCalory = dbHelperActivity.findActivityBest("calories");
        bDistance = dbHelperActivity.findActivityBest("distance");
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

/*
 * detail_activity  : Activity display selected record from history_activity
 *
 * Methods          :  init()         : initialize variable
 *                     setEvents()    : setting up listeners
 *                     delete()       : deleting a record from database
 */

package mdpcw2.mytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mdpcw2.mytracker.non_activity.DBHelperActivity;

public class detail_activity extends AppCompatActivity {

    //Global Variables
    private int id;
    private String steps, date, timer, distance, calory;

    private TextView txtDetailStep, txtDetailDate, txtDetailTimer, txtDetailDistance, txtDetailCalory;
    private Button btnDetailBack, btnDetailDelete;

    //Initialising variables and states
    private void init(){
        id = getIntent().getExtras().getInt("id");
        Log.d("MyTracker","ID: "+String.valueOf(id));
        date = getIntent().getExtras().get("date").toString();
        steps = getIntent().getExtras().get("steps").toString();
        distance = getIntent().getExtras().get("distance").toString();
        timer = getIntent().getExtras().get("timer").toString();
        calory = getIntent().getExtras().get("calory").toString();

        btnDetailBack = findViewById(R.id.btnDetailBack);
        btnDetailDelete = findViewById(R.id.btnDetailDelete);

        txtDetailDate = findViewById(R.id.txtDetailDate);
        txtDetailStep = findViewById(R.id.txtDetailStep);
        txtDetailTimer = findViewById(R.id.txtDetailTimer);
        txtDetailDistance = findViewById(R.id.txtDetailDistance);
        txtDetailCalory = findViewById(R.id.txtDetailCalory);

        String sCalory, sDistance;
        sCalory = calory + " kCal";
        sDistance = distance + " m";
        txtDetailCalory.setText(sCalory);
        txtDetailDistance.setText(sDistance);
        txtDetailDate.setText(date);
        txtDetailStep.setText(steps);
        txtDetailTimer.setText(timer);
    }

    //Setting up events
    private void setEvents(){
        btnDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnDetailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
    }

    //Method to delete the selected activity
    private void delete(){
        Log.d("MyTracker","=DetailActivity: Deleting a record from database");
        DBHelperActivity dbHelperActivity = new DBHelperActivity(this,null,null,1);

        boolean result = dbHelperActivity.deleteActivity(id);

        if(result){
            Log.d("MyTracker","=DetailActivity: Successfully deleted record");
            Toast.makeText(getApplicationContext(),"Record successfully deleted",Toast.LENGTH_SHORT).show();
        }else {
            Log.d("MyTracker","=DetailActivity: Error deleted record");
            Toast.makeText(getApplicationContext(),"Error deleting record",Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    //Activity Lifecycle onCreate()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);

        init();
        setEvents();
        Log.d("MyTracker","=DetailActivity onCreate()");
    }

    //Activity Lifecycle onStart()
    @Override
    protected void onStart(){
        super.onStart();
        Log.d("MyTracker","=DetailActivity onStart()");
    }

    //Activity Lifecycle onRestart()
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("MyTracker","=DetailActivity onRestart()");
    }

    //Activity Lifecycle onResume()
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("MyTracker","=DetailActivity onResume()");
    }

    //Activity Lifecycle onPause()
    @Override
    protected void onPause(){
        super.onPause();
        Log.d("MyTracker","=DetailActivity onPause()");
    }

    //Activity Lifecycle onStop()
    @Override
    protected void onStop(){
        super.onStop();
        Log.d("MyTracker","=DetailActivity onStop()");
    }

    //Activity Lifecycle onDestroy()
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("MyTracker","=DetailActivity onDestroy()");
    }
}

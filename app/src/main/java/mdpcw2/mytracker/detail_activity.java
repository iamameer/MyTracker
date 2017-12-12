package mdpcw2.mytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class detail_activity extends AppCompatActivity {

    //Global Variables
    private String steps, date, timer, distance, calory;

    private TextView txtDetailStep, txtDetailDate, txtDetailTimer, txtDetailDistance, txtDetailCalory;
    private Button btnDetailBack;

    //Init
    private void init(){
        date = getIntent().getExtras().get("date").toString();
        steps = getIntent().getExtras().get("steps").toString();
        distance = getIntent().getExtras().get("distance").toString();
        timer = getIntent().getExtras().get("timer").toString();
        calory = getIntent().getExtras().get("calory").toString();

        btnDetailBack = findViewById(R.id.btnDetailBack);

        txtDetailDate = findViewById(R.id.txtDetailDate);
        txtDetailStep = findViewById(R.id.txtDetailStep);
        txtDetailTimer = findViewById(R.id.txtDetailTimer);
        txtDetailDistance = findViewById(R.id.txtDetailDistance);
        txtDetailCalory = findViewById(R.id.txtDetailCalory);

        txtDetailDate.setText(date);
        txtDetailStep.setText(steps);
        txtDetailDistance.setText(distance);
        txtDetailTimer.setText(timer);
        txtDetailCalory.setText(calory);
    }

    //Setting up events
    private void setEvents(){
        btnDetailBack.setOnClickListener(new View.OnClickListener() {
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

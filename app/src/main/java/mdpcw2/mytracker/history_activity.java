/*
 * history_activity : Activity that display full records of database
 *
 * Methods          :  init()         : initialize variable
 *                     setEvents()    : setting up listeners
 *                     viewActivity() : select a specified activity and launch detail_activity
 *                     display()      : display all records from database
 */

package mdpcw2.mytracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mdpcw2.mytracker.non_activity.Activities;
import mdpcw2.mytracker.non_activity.DBHelperActivity;

public class history_activity extends AppCompatActivity {

    //Global Variables
    private Button btnHistoryBack;
    private ListView listView;

    ArrayList<Activities> activitiesArrayList;

    //Initialising variables
    private void init(){
        btnHistoryBack = findViewById(R.id.btnHistoryBack);
        listView = findViewById(R.id.listView);
    }

    //Setting up events
    private void setEvents(){
        btnHistoryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {viewActivity(i);}
        });
    }

    //Method to view Activity detail
    private void viewActivity(int position){
        Log.d("MyTracker","=HistoryActivity: Launching detail_activity--> View Record : "+listView.getItemAtPosition(position));
        Log.d("MyTracker","Item position: "+String.valueOf(position));
        //implementing search()
        DBHelperActivity dbHelperActivity = new DBHelperActivity(this,null,null,1);
        Activities activities = dbHelperActivity.findActivity(activitiesArrayList.get(position).get_id());

        if (activities!= null){
            Intent intent = new Intent(getApplicationContext(),detail_activity.class);
            intent.putExtra("id",activitiesArrayList.get(position).get_id());
            intent.putExtra("date",activities.getDate());
            intent.putExtra("steps",activities.getStep());
            intent.putExtra("distance",activities.getDistance());
            intent.putExtra("timer",activities.getDuration());
            intent.putExtra("calory",activities.getCalories());
            startActivity(intent);
        }else{
            Log.d("MyTracker","=HistoryActivity: Error viewing item");
            Toast.makeText(getApplicationContext(),"Error viewing item",Toast.LENGTH_SHORT).show();
        }
    }

    //Method to display database in the listView
    private void display(){
        Log.d("MyTracker","=HistoryActivity: Retrieving record from database");
        DBHelperActivity dbHelperActivity = new DBHelperActivity(this,null,null,1);
        activitiesArrayList = dbHelperActivity.display();

        //display if the list is not empty, else simply state empty
        if (activitiesArrayList!=null){
            List<String> list = new ArrayList<String>();
            for (int i = 0; i <activitiesArrayList.size(); i++){
                list.add((i+1)+". "+activitiesArrayList.get(i).getDate()+" | Distance: "+activitiesArrayList.get(i).getDistance()+" m");
            }
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
            listView.setAdapter(adapter);
        }else{
            Toast.makeText(getApplicationContext()," No record in Database", Toast.LENGTH_SHORT).show();
            Log.d("MyTracker","=HistoryActivity: Empty database");
        }
    }

    //Activity Lifecycle onCreate()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_activity);

        init();
        setEvents();
        Log.d("MyTracker","=HistoryActivity onCreate()");
    }

    //Activity Lifecycle onStart()
    @Override
    protected void onStart(){
        super.onStart();
        Log.d("MyTracker","=HistoryActivity onStart()");
    }

    //Activity Lifecycle onRestart()
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("MyTracker","=HistoryActivity onRestart()");
    }

    //Activity Lifecycle onResume()
    @Override
    protected void onResume(){
        display();
        super.onResume();
        Log.d("MyTracker","=HistoryActivity onResume()");
    }

    //Activity Lifecycle onPause()
    @Override
    protected void onPause(){
        super.onPause();
        Log.d("MyTracker","=HistoryActivity onPause()");
    }

    //Activity Lifecycle onStop()
    @Override
    protected void onStop(){
        super.onStop();
        Log.d("MyTracker","=HistoryActivity onStop()");
    }

    //Activity Lifecycle onDestroy()
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("MyTracker","=HistoryActivity onDestroy()");
    }
}

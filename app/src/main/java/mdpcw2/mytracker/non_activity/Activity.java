/*
 * Activity.java : Basic abstract class to create Activity as an object
 *
 * Methods       : setter() and getter()
 */

package mdpcw2.mytracker.non_activity;

public class Activity {
    private int _id;
    private String date;
    private String activity;
    private int step;
    private int distance;
    private int duration;
    private int calories;
    private String GPX;

    //empty constructor
    public Activity(){}

    //full pledged constructor
    public Activity(int id, String date, String activity,int step, int distance, int duration,int calories, String GPX){
        this._id = id;
        this.date = date;
        this.activity = activity;
        this.step = step;
        this.distance = distance;
        this.duration = duration;
        this.calories = calories;
        this.GPX = GPX;
    }

    //return the id
    public int get_id() {
        return _id;
    }

    //set the id
    public void set_id(int _id) {
        this._id = _id;
    }

    //return the date
    public String getDate() {
        return date;
    }

    //set the date
    public void setDate(String date) {
        this.date = date;
    }

    //return the activity
    public String getActivity() {
        return activity;
    }

    //set the activity
    public void setActivity(String activity) {
        this.activity = activity;
    }

    //return the distance
    public Integer getDistance() {
        return distance;
    }

    //set the distance
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    //return the duration
    public Integer getDuration() {
        return duration;
    }

    //set the duration
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    //return the gpx
    public String getGPX() {
        return GPX;
    }

    //set the gpx
    public void setGPX(String GPX) {
        this.GPX = GPX;
    }

    //return the steps
    public int getStep() {
        return step;
    }

    //set the steps
    public void setStep(int step) {
        this.step = step;
    }

    //return the calory
    public int getCalories() {
        return calories;
    }

    //set the calories
    public void setCalories(int calories) {
        this.calories = calories;
    }
}

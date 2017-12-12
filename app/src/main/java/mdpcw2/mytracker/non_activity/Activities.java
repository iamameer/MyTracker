/*
 * Activity.java : Basic abstract class to create Activity as an object
 *
 * Methods       : setter() and getter()
 */

package mdpcw2.mytracker.non_activity;

public class Activities {
    private int _id;
    private String date;
    private String step;
    private String distance;
    private String duration;
    private String calories;

    //empty constructor
    public Activities(){}

    //full pledged constructor
    public Activities(int id, String date, String step, String distance, String duration, String calories){
        this._id = id;
        this.date = date;
        this.step = step;
        this.distance = distance;
        this.duration = duration;
        this.calories = calories;
    }

    //constructor without ID
    public Activities(String date, String step, String distance, String duration, String calories){
        this.date = date;
        this.step = step;
        this.distance = distance;
        this.duration = duration;
        this.calories = calories;
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

    //return the distance
    public String getDistance() {
        return distance;
    }

    //set the distance
    public void setDistance(String distance) {
        this.distance = distance;
    }

    //return the duration
    public String getDuration() {
        return duration;
    }

    //set the duration
    public void setDuration(String duration) {
        this.duration = duration;
    }

    //return the steps
    public String getStep() {
        return step;
    }

    //set the steps
    public void setStep(String step) {
        this.step = step;
    }

    //return the calory
    public String getCalories() {
        return calories;
    }

    //set the calories
    public void setCalories(String calories) {
        this.calories = calories;
    }
}

/*
 * Activity.java : Basic abstract class to create Activity as an object
 */

package mdpcw2.mytracker.non_activity;

public class Activity {
    private int _id;
    private String date;
    private String activity;
    private Boolean isTheBest;
    private Integer distance;
    private Integer duration;
    private String GPX;

    //empty constructor
    public Activity(){}

    //full pledged constructor
    public Activity(int id, String date, String activity, Boolean isTheBest, int distance, int duration, String GPX){
        this._id = id;
        this.date = date;
        this.activity = activity;
        this.isTheBest = isTheBest;
        this.distance = distance;
        this.duration = duration;
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

    //return the boolean, if its the best
    public Boolean getTheBest() {
        return isTheBest;
    }

    //set the boolean, if its the best
    public void setTheBest(Boolean theBest) {
        isTheBest = theBest;
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
}

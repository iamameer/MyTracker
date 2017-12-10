package mdpcw2.mytracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TrackerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,intent.getExtras().get("coordinates").toString(),Toast.LENGTH_SHORT).show();
    }
}

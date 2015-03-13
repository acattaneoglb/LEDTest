package com.globant.cattaneo.ariel.ledtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.RadioGroup;


public class MainActivity extends ActionBarActivity {

    static int lightColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            rootView.findViewById(R.id.button_led).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.lightColor = Color.WHITE;
                    int selectedRadio = ((RadioGroup)getActivity().findViewById(R.id.radio_group_colors))
                            .getCheckedRadioButtonId();

                    switch (selectedRadio) {
                        case R.id.radio_button_red:
                            MainActivity.lightColor = Color.RED;
                            break;
                        case R.id.radio_button_green:
                            MainActivity.lightColor = Color.GREEN;
                            break;
                        case R.id.radio_button_blue:
                            MainActivity.lightColor = Color.BLUE;
                            break;
                        case R.id.radio_button_yellow:
                            MainActivity.lightColor = Color.YELLOW;
                            break;
                        case R.id.radio_button_magenta:
                            MainActivity.lightColor = Color.MAGENTA;
                            break;
                        case R.id.radio_button_cyan:
                            MainActivity.lightColor = Color.CYAN;
                            break;
                        case R.id.radio_button_yellow_in_my_nexus:
                            MainActivity.lightColor = 0xFF30FF00;
                            break;
                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                            .setContentTitle("Testing the LED")
                            .setContentText("We are testing the LED")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                            .setLights(lightColor, 500, 500)
                            .setContentIntent(PendingIntent.getActivity(getActivity(), 0, new Intent(), 0));

                    final Notification notification = builder.build();
                    notification.flags |= Notification.FLAG_SHOW_LIGHTS;

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                            mNotificationManager.notify(1, notification);
                        }
                    }, 10000);
                }
            });

            return rootView;
        }
    }
}

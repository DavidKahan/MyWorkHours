package com.kahan.david.myworkhours.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.kahan.david.myworkhours.R;
import com.kahan.david.myworkhours.model.StatsDbHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by david on 18/04/2018.
 */
public class GeofenceTransitionsIntentService extends IntentService {

    private static final String TAG = "GeofenceTransitions";
    private boolean enteredGeofence = false;
    private Date enteredTime;

    public GeofenceTransitionsIntentService() {
        super("GeofenceTransitionsIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(this.TAG, "onHandleIntent");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceErrorMessages.getErrorString(this,
                    geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }

        // Get the transition type.
        int currentTransitionType = geofencingEvent.getGeofenceTransition();
        Date currentTime = Calendar.getInstance().getTime();

        if (currentTransitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
            enteredGeofence = true;
            enteredTime = currentTime;
        } else if (currentTransitionType == Geofence.GEOFENCE_TRANSITION_EXIT && enteredGeofence) {
            Calendar.getInstance().getTime();
            long millis = currentTime.getTime() - enteredTime.getTime();
            int hours = (int) (millis / (1000 * 60 * 60));
            int mins = (int) ((millis / (1000 * 60)) % 60);
            String duration = hours + ":" + mins;
            StatsDbHelper mDbHelper = StatsDbHelper.getInstance(getApplicationContext());
            mDbHelper.insertStat(duration);
        }


        // Test that the reported transition was of interest.
        if (currentTransitionType == Geofence.GEOFENCE_TRANSITION_ENTER ||
                currentTransitionType == Geofence.GEOFENCE_TRANSITION_EXIT ||
                currentTransitionType == Geofence.GEOFENCE_TRANSITION_DWELL) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(currentTransitionType, triggeringGeofences);
            Log.i(TAG, geofenceTransitionDetails);
        } else {
            // Log the error.
            Log.e(TAG, getString(R.string.geofence_transition_invalid_type,
                    currentTransitionType));
        }
    }


    /**
     * Gets transition details and returns them as a formatted string.
     *
     * @param geofenceTransition  The ID of the geofence transition.
     * @param triggeringGeofences The geofence(s) triggered.
     * @return The transition details formatted as String.
     */
    private String getGeofenceTransitionDetails(
            int geofenceTransition,
            List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList<String> triggeringGeofencesIdsList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);

        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }


    /**
     * Maps geofence transition types to their human-readable equivalents.
     *
     * @param transitionType A transition type constant defined in Geofence
     * @return A String indicating the type of transition
     */
    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.geofence_transition_entered);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.geofence_transition_exited);
            default:
                return getString(R.string.unknown_geofence_transition);
        }
    }
}

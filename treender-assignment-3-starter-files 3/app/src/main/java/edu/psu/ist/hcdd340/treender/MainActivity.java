package edu.psu.ist.hcdd340.treender;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;

/**
 * Data class representing a tree profile
 */
final class TreeProfile {
    private final String profileName;
    private final String profileID;
    private final String location;
    private final int profileImageID;

    TreeProfile(String profileName, String profileID, String location, int profileImageID) {
        this.profileName = profileName;
        this.profileID = profileID;
        this.location = location;
        this.profileImageID = profileImageID;
    }

    public int getProfileImageID() {
        return profileImageID;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getProfileID() {
        return profileID;
    }

    public String getLocation() {
        return location;
    }
}

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TREENDER_ACTIVITY";

    private static final int[] ACTION_ICON_IDS = {
            R.id.rewindIcon,
            R.id.nopeIcon,
            R.id.boostIcon,
            R.id.likeIcon,
            R.id.superLikeIcon
    };

    private static final TreeProfile[] TREE_PROFILES = {
            new TreeProfile("Hosler Oak", "7863", "Arboretum", R.drawable.hosler_tree),
            new TreeProfile("Banana", "7864", "Esplanade in Arboretum", R.drawable.banana_tree),
            new TreeProfile("Hemlock", "7865", "Entry Walk in Arboretum", R.drawable.hemlock_tree),
            new TreeProfile("Santa Cruz lily", "7866", "Oasis Garden & Lotus Pool", R.drawable.santa_cruiz_tree),
            new TreeProfile("Flapjack", "7867", "Arboretum", R.drawable.flapjack_tree),
            new TreeProfile("Japanese Peony", "7868", "Rose & Fragrance Garden", R.drawable.japanese_peony_tree),
            new TreeProfile("Crab Apple", "7869", "Strolling Garden", R.drawable.crabapple_tree),
            new TreeProfile("Black Pine", "7870", "Strolling Garden", R.drawable.blackpine_tree),
            new TreeProfile("Hellebore", "7871", "Strolling Garden", R.drawable.hellebore_tree),
            new TreeProfile("False Spirea", "7872", "Strolling Garden", R.drawable.spirea_tree),
    };

    private static int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Set event handler for icons
         */
        for (int id : ACTION_ICON_IDS) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        /*
        Handle onClick events
         */
        int id = view.getId();
        if ((id == R.id.nopeIcon) || (id == R.id.boostIcon) ||
                (id == R.id.likeIcon) || (id == R.id.superLikeIcon)) {
            updateTreeProfile(moveToNextProfile());
        } else if (id == R.id.rewindIcon)
            updateTreeProfile(moveToPreviousProfile());
        else
            Log.d(TAG, "Unknown ID: " + id);
    }

    /**
     * Updates the screen to show the given tree
     */
    private void updateTreeProfile(TreeProfile profile) {
        showTreeProfile(profile);
    }

    /**
     * Show a given profile
     */
    private void showTreeProfile(TreeProfile treeProfile) {
        ShapeableImageView treeImage = findViewById(R.id.imageTree);
        treeImage.setImageResource(treeProfile.getProfileImageID());

        TextView view;
        view = findViewById(R.id.treeName);
        view.setText(treeProfile.getProfileName());

        view = findViewById(R.id.treeID);
        view.setText(treeProfile.getProfileID());

        view = findViewById(R.id.locationText);
        view.setText(treeProfile.getLocation());

    }

    /**
     * Gets next tree profile by increasing the index by 1. Also, saves the index.
     */
    private TreeProfile moveToNextProfile() {
        index = (index + 1) % TREE_PROFILES.length;
        return TREE_PROFILES[index];
    }

    /**
     * Gets previous tree profile by decreasing the index by 1. Also, saves the index.
     */
    private TreeProfile moveToPreviousProfile() {
        index = index - 1;
        if (index < 0)
            index = TREE_PROFILES.length - 1;
        return TREE_PROFILES[index];
    }


    /**
     * Gets current profile
     */
    private TreeProfile getCurrentProfile() {
        return TREE_PROFILES[index];
    }

    /**
     * Reset profiles — move to the first profile
     */
    private TreeProfile resetProfile() {
        index = 0;
        return TREE_PROFILES[index];
    }

    @Override public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item){
        int menuId = item.getItemId();

        if(menuId == R.id.menu_reset){
            Log.d(TAG, "Reset button clicked!");
            index = 0;
            showTreeProfile(getCurrentProfile());

            ShapeableImageView view = findViewById(R.id.imageTree);
            Snackbar.make(view, R.string.snack_bar_reset, Snackbar.LENGTH_LONG).show();
            return true;
        }

        if(menuId == R.id.menu_about){
            AlertDialog.Builder d = new AlertDialog.Builder(this);
            d.setTitle("About Treender");
            d.setMessage(R.string.about_alert_message);
            d.setPositiveButton("OK", null);
            d.show();
        }
        return super.onOptionsItemSelected(item);
    }



}
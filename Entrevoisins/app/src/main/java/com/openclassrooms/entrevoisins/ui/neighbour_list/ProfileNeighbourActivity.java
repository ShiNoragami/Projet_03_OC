package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.openclassrooms.entrevoisins.R;

import java.io.Serializable;


public class ProfileNeighbourActivity extends AppCompatActivity {

    private ProfileFragment mProfileFragment;
    private Serializable mNeighbour;
    public static String NEIGHBOUR = "NEIGHBOUR";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_neighbour);

        if (getIntent().hasExtra(NEIGHBOUR)) {
            mNeighbour = getIntent().getSerializableExtra(NEIGHBOUR);
        }

        Bundle args = new Bundle();
        args.putSerializable(NEIGHBOUR, mNeighbour);

        mProfileFragment = new ProfileFragment();
        mProfileFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.profile_activity_framelayout, mProfileFragment)
                .commit();

    }
}

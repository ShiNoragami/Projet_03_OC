package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfileFragment extends Fragment {

    @BindView(R.id.profile_name)
    TextView profile_name;
    @BindView(R.id.profile_picture)
    ImageView picture;
    @BindView(R.id.info_name)
    TextView name;
    @BindView(R.id.info_fb)
    TextView social;
    @BindView(R.id.profile_favorite)
    FloatingActionButton fab_favorite;
    @BindView(R.id.profile_return)
    ImageView back;
    @BindView(R.id.info_location)
    TextView adresse;
    @BindView(R.id.info_tel)
    TextView phone;
    @BindView(R.id.describe)
    TextView about;

    private NeighbourApiService mApiService;
    protected Neighbour mNeighbour;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args.getSerializable(ProfileNeighbourActivity.NEIGHBOUR) != null) {
            mNeighbour = (Neighbour) args.getSerializable(ProfileNeighbourActivity.NEIGHBOUR);
        }
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_neighbour, container, false);
        ButterKnife.bind(this, view);

        //scroll describe text
        about.setMovementMethod(new ScrollingMovementMethod());

        //Get profile information
        profile_name.setText(mNeighbour.getName());
        name.setText(mNeighbour.getName());
        social.setText("www.facebook.com/" + mNeighbour.getName());
        adresse.setText(mNeighbour.getAddress());
        phone.setText(mNeighbour.getPhoneNumber());
        about.setText(mNeighbour.getAboutMe());
        Glide.with(this).load(mNeighbour.getAvatarUrl()).centerCrop().into(picture);

        setFavoriteImg();

        fab_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFabFavorite();
            }
        });

        back.setOnClickListener(v -> getActivity().finish());
        return view;
    }

    public void setFavoriteImg() {
        if (mApiService.isFavorite(mNeighbour)) {
            fab_favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_white_24dp));
        } else {
            fab_favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_white_24dp));
        }
    }

    public void setFabFavorite() {
        if (mApiService.isFavorite(mNeighbour)) {
            mApiService.removeFavorite(mNeighbour);
            Toast.makeText(this.getContext(), R.string.toast_not_fav, Toast.LENGTH_SHORT).show();
        } else {
            mApiService.addFavorite(mNeighbour);
            Toast.makeText(this.getContext(), R.string.toast_fav, Toast.LENGTH_SHORT).show();
        }
        setFavoriteImg();
    }
}

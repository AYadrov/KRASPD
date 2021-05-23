package com.example.android_project.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.android_project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        LatLng SFU_IKIT = new LatLng(55.994485, 92.797299);
        map.addMarker(new MarkerOptions().position(SFU_IKIT).title("SFU IKIT"));
        map.moveCamera(CameraUpdateFactory.newLatLng(SFU_IKIT));

        LatLng Garage = new LatLng(56.009326, 93.057422);
        map.addMarker(new MarkerOptions().position(Garage).title("Homicide"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Garage));

        LatLng Situation = new LatLng(56.005535, 92.853668);
        map.addMarker(new MarkerOptions().position(Situation).title("Assault"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Situation));

        LatLng Ebalo = new LatLng(55.986774, 93.013476);
        map.addMarker(new MarkerOptions().position(Ebalo).title("Vandalism"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Ebalo));
    }
}
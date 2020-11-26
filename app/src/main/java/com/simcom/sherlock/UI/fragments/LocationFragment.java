package com.simcom.sherlock.UI.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.simcom.sherlock.R;
import com.simcom.sherlock.UI.viewModels.ShareViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationFragment extends Fragment {
    private GoogleMap map;
    private MapView mapView;
    private ShareViewModel viewModel;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_location, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        mapView = root.findViewById(R.id.map);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(googleMap -> map = googleMap);
        viewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(ShareViewModel.class);
        ((TextView)view.findViewById(R.id.location_heading)).setText(viewModel.getFriend().getDisplayName()+"'s location");
        viewModel.getFriendsLocations().addSnapshotListener(requireActivity(), (value, error) -> {
            if(error!=null){
                error.printStackTrace();
            }else {
                if (value.getBoolean("currentlySharing")) {
                    HashMap<String, Double> rawLocation = (HashMap<String, Double>) value.get("location");
                    drawLocation(new LatLng(rawLocation.get("latitude"),rawLocation.get("longitude")));
                }else{
                    Toast.makeText(requireContext(), "Sharing ended", Toast.LENGTH_LONG).show();
                navController.navigateUp();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mapView!=null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    private void drawLocation(LatLng location){
        System.out.println("Drawing...");
        map.clear();
        MarkerOptions options = new MarkerOptions()
                .position(location)
                .title("Location")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        map.addMarker(options);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15));
    }
}

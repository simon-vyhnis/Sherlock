package com.simcom.sherlock.UI.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.simcom.sherlock.LocationPermissions;
import com.simcom.sherlock.R;
import com.simcom.sherlock.UI.viewModels.LoginViewModel;
import com.simcom.sherlock.UI.viewModels.ShareViewModel;
import com.simcom.sherlock.model.Repository;
import com.simcom.sherlock.services.ShareLocationService;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class ShareFragment extends Fragment implements EasyPermissions.PermissionCallbacks{
    private MapView mapView;
    private GoogleMap map;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        final NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        ShareViewModel viewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(ShareViewModel.class);
        mapView = root.findViewById(R.id.map);
        button = root.findViewById(R.id.button_share);
        button.setOnClickListener(view -> {
            if(ShareLocationService.isRunning().getValue() == null || !ShareLocationService.isRunning().getValue()){
                viewModel.startSharing().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Intent intent = new Intent(requireContext(), ShareLocationService.class);
                        intent.putExtra("action",ShareLocationService.ACTION_START);
                        requireContext().startForegroundService(intent);
                    }else{
                        Toast.makeText(getContext(),"Something went wrong, check your internet connection", Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Intent intent = new Intent(requireContext(), ShareLocationService.class);
                intent.putExtra("action",ShareLocationService.ACTION_STOP);
                requireContext().startForegroundService(intent);
                viewModel.stopSharing();
            }
        });
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        ShareLocationService.isRunning().observe(requireActivity(), aBoolean -> {
            if(aBoolean){
                button.setText(R.string.stop_sharing);
            }else{
                button.setText(R.string.share_location);
            }
        });
        mapView.getMapAsync(googleMap -> map = googleMap);
        ShareLocationService.getLocations().observe(requireActivity(), this::drawLocation);
        requestPermissions();
    }

    private void requestPermissions() {
        if(LocationPermissions.hasLocationPermissions(requireContext())) {
            return;
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                    this,
                    "You need to accept location permissions to use this app.",
                    0,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            );
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "You need to accept location permissions to use this app.",
                    0,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
            );
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        } else {
            requestPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults, this);
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

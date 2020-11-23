package com.simcom.sherlock.UI.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.simcom.sherlock.R;
import com.simcom.sherlock.UI.PortraitCaptureActivity;
import com.simcom.sherlock.UI.viewModels.FriendsViewModel;
import com.simcom.sherlock.UI.viewModels.LoginViewModel;

public class FriendFragment extends Fragment {

    private FriendsViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friend, container, false);
        final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        root.findViewById(R.id.scan).setOnClickListener(view->{
            scan();
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(FriendsViewModel.class);
        ImageView qrImage = view.findViewById(R.id.qr_code);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            Bitmap bitmap = barcodeEncoder.encodeBitmap(viewModel.getUid(), BarcodeFormat.QR_CODE, width, width);
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void scan(){
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scan your friends code");
        integrator.setCaptureActivity(PortraitCaptureActivity.class);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                viewModel.addFriend(result.getContents()).observe(getActivity(),bool -> {
                    if(bool){
                        Toast.makeText(getContext(), "Friend added", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}

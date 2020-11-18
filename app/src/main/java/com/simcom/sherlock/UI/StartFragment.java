package com.simcom.sherlock.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.simcom.sherlock.R;

public class StartFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_start, container, false);
        final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        root.findViewById(R.id.button_share).setOnClickListener(view -> navController.navigate(R.id.action_startFragment_to_shareFragment));
        root.findViewById(R.id.button_observe).setOnClickListener(view -> navController.navigate(R.id.action_startFragment_to_observeFragment));
        root.findViewById(R.id.button_friend).setOnClickListener(view -> navController.navigate(R.id.action_startFragment_to_friendFragment));
        return root;
    }
}

package com.simcom.sherlock.UI.fragments;

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
import androidx.recyclerview.widget.RecyclerView;

import com.simcom.sherlock.R;
import com.simcom.sherlock.UI.adapters.ObserveViewAdapter;
import com.simcom.sherlock.UI.viewModels.ShareViewModel;
import com.simcom.sherlock.model.Friend;

import java.util.ArrayList;
import java.util.List;

public class ObserveFragment extends Fragment {
    private ObserveViewAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_observe, container, false);
        final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        adapter = new ObserveViewAdapter();
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ShareViewModel viewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(ShareViewModel.class);
        viewModel.getActiveFriends();
        adapter.setFriends(new ArrayList<>());
    }
}

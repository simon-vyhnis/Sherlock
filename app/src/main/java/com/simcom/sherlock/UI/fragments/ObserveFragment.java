package com.simcom.sherlock.UI.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.simcom.sherlock.R;
import com.simcom.sherlock.UI.adapters.ObserveViewAdapter;
import com.simcom.sherlock.UI.viewModels.ShareViewModel;
import com.simcom.sherlock.model.Friend;

import java.util.ArrayList;
import java.util.List;

public class ObserveFragment extends Fragment {
    private ObserveViewAdapter adapter;
    private ShareViewModel viewModel;
    private NavController navController;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_observe, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        adapter = new ObserveViewAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(ShareViewModel.class);
        viewModel.getActiveFriends().addSnapshotListener(requireActivity(), (value, error) -> {
           if(error!=null){
               error.printStackTrace();
               Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
           }else {
               System.out.println(value.size());
               List<Friend>list = new ArrayList<>();
               for(QueryDocumentSnapshot document:value){
                   list.add(new Friend(document.getId(),document.getString("displayName")));
               }
               System.out.println(list.size());
               adapter.setFriends(list);
           }
        });
    }
    public void openMap(Friend friend){
        viewModel.setFriendUid(friend);
        navController.navigate(R.id.action_observeFragment_to_locationFragment);
    }
}

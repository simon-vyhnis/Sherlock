package com.simcom.sherlock.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.simcom.sherlock.R;

public class RegisterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        LoginViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(LoginViewModel.class);
        EditText name = root.findViewById(R.id.name);
        EditText email = root.findViewById(R.id.email);
        EditText password = root.findViewById(R.id.password);
        name.setPadding(10,0,0,0);
        email.setPadding(10,0,0,0);
        password.setPadding(10,0,0,0);
        Button register = root.findViewById(R.id.button_register);
        register.setOnClickListener(view -> {
            String nameText = name.getText().toString();
            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();
            if(!nameText.isEmpty()){
                if(emailText.contains("@")){
                    if(password.toString().length()>5){
                        if(viewModel.registerUser(emailText, passwordText)){
                            Toast.makeText(getContext(), "Registration successful",Toast.LENGTH_SHORT).show();
                            navController.navigate(R.id.action_registerFragment_to_startFragment);
                        }else{
                            Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        password.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_text_background_error));
                    }
                }else{
                    email.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_text_background_error));
                }
            }else{
                name.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_text_background_error));
            }
        });
        root.findViewById(R.id.button_go_login).setOnClickListener(view -> navController.navigate(R.id.action_registerFragment_to_loginFragment));
        return root;
    }
}

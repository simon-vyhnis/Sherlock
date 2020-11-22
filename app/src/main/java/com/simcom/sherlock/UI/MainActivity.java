
package com.simcom.sherlock.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;

import com.simcom.sherlock.R;
import com.simcom.sherlock.UI.viewModels.LoginViewModel;

public class MainActivity extends AppCompatActivity {
    public static final int ACTION_NAVIGATE_TO_SHARE_FRAGMENT = 1;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        LoginViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(LoginViewModel.class);
        if(getIntent()!=null){
            navigateToShareFragmentIfNeeded(getIntent());
        }
        if(viewModel.isLoggedIn() && navController.getCurrentDestination().getId() == R.id.registerFragment){
            navController.navigate(R.id.action_registerFragment_to_startFragment);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        navigateToShareFragmentIfNeeded(intent);
    }

    private void navigateToShareFragmentIfNeeded(Intent intent){
        if(intent.getIntExtra("action",0) == ACTION_NAVIGATE_TO_SHARE_FRAGMENT){
            navController.navigate(R.id.action_global_shareFragment);
        }
    }
}
package cpe.mobile.mybrowzik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import cpe.mobile.mybrowzik.databinding.ActivityMainBinding;
import cpe.mobile.mybrowzik.fragments.AudioFileListFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public void showStartup() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        AudioFileListFragment fragment = new AudioFileListFragment();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        showStartup();
    }
}

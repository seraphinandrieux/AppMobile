package cpe.mobile.mybrowzik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import cpe.mobile.mybrowzik.databinding.ActivityMainBinding;
import cpe.mobile.mybrowzik.fragments.AudioFileListFragment;
import cpe.mobile.mybrowzik.fragments.AudioManagerFragment;
import cpe.mobile.mybrowzik.listeners.MyListener;

public class MainActivity  extends AppCompatActivity implements MyListener {

    private ActivityMainBinding binding;

    public MyListener listener = new MyListener() {
        @Override
        public void onStartMusic() {

        }

        @Override
        public void onNextMusic() {

        }

        @Override
        public void onPreviousMusic() {

        }

        @Override
        public void onStopMusic() {

        }

        @Override
        public void onSelectMusic() {

        }
    };



    public void showStartup() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        AudioFileListFragment fragment = new AudioFileListFragment();
        AudioManagerFragment fragmentManager = new AudioManagerFragment();
        transaction.replace(R.id.fragment_container,fragment);
        //transaction.add(fragmentManager);
        transaction.commit();
        fragment.setMyListener(listener);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        showStartup();


    }


}

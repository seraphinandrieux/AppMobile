package cpe.mobile.mybrowzik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import cpe.mobile.mybrowzik.databinding.ActivityMainBinding;
import cpe.mobile.mybrowzik.fragments.AudioFileListFragment;
import cpe.mobile.mybrowzik.fragments.ControlMusicFragment;

import cpe.mobile.mybrowzik.listener.MyListener;

public class MainActivity extends AppCompatActivity{

    private ActivityMainBinding binding;



    public void showStartup() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        AudioFileListFragment audioFileListFragment = new AudioFileListFragment();
        ControlMusicFragment controlMusicFragment = new ControlMusicFragment();
        MyListener listener = new MyListener() {
            @Override
            public void onStartMusic() {

            }
            @Override
            public void onStopMusic() {

            }
            @Override
            public void onNextMusic(){}

            @Override
            public void onSelectMusic(AudioFile file) {
                controlMusicFragment.updateSelectMusic(file);
            }

            @Override
            public void onPreviousMusic() {}

        };
        audioFileListFragment.setMyListener(listener);
        controlMusicFragment.setMyListener(listener);

        transaction.replace(R.id.audio_file_list_fragment_container,audioFileListFragment);
        transaction.replace(R.id.control_music_fragment_container,controlMusicFragment);
        transaction.commit();




        //fragment.setMyListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);



        showStartup();
    }
}

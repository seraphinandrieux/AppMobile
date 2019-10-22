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
import cpe.mobile.mybrowzik.models.AudioFile;

/*
Question a poser :



 */
public  class MainActivity  extends AppCompatActivity  {

    private ActivityMainBinding binding;





    public void showStartup() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        AudioFileListFragment fragment = new AudioFileListFragment();

        AudioManagerFragment fragmentManager = new AudioManagerFragment();

        transaction.replace(R.id.musicListLayout,fragment);

        transaction.replace(R.id.musicManagerLayout,fragmentManager);

        MyListener listener = new MyListener() {
            @Override
            public void onSelectMusic(AudioFile audioFile) {
                fragmentManager.updateCurrentMusic(audioFile);
            }

            @Override
            public void onNextMusic() {

                fragmentManager.changeCurrentMusic(fragment.getAudioList(),(+1));
            }

            @Override
            public void onPreviousMusic() {
                fragmentManager.changeCurrentMusic(fragment.getAudioList(),(-1));
            }
        };


        fragment.setMyListener(listener);
        fragmentManager.setMyListener(listener);

        //transaction.add(fragmentManager);
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

package cpe.mobile.mybrowzik.fragments;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import cpe.mobile.mybrowzik.MainActivity;
import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.databinding.AudioManagerFragmentBinding;
import cpe.mobile.mybrowzik.listeners.MyListener;
import cpe.mobile.mybrowzik.models.AudioFile;

import cpe.mobile.mybrowzik.services.PlayerService;

import cpe.mobile.mybrowzik.viewModel.AudioManagerViewModel;

public class AudioManagerFragment extends Fragment {


    private AudioManagerViewModel viewModel = new AudioManagerViewModel();
    private MyListener myListener;
    private Thread progressBarThread;
    private ProgressBar progressBar;






    public void setMyListener(MyListener listener){

        myListener = listener;

    }

    public void updateCurrentMusic(AudioFile file){

        viewModel.setAudioFile(file);
    }

    public void changeCurrentMusic(List<AudioFile> audioList,int pNextPrevious){

        int indexCurrentMusic,indexNextMusic;

        indexCurrentMusic = audioList.indexOf(viewModel.getAudioFile());
        indexNextMusic      = indexCurrentMusic + pNextPrevious;
        if (indexNextMusic<0){
            indexNextMusic = audioList.size() -1;
        }else if(indexNextMusic == audioList.size()){
            indexNextMusic = 0;
        }
        viewModel.setAudioFile(audioList.get(indexNextMusic));

    }

    public AudioFile getCurrentMusic(){

        return viewModel.getAudioFile();
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        AudioManagerFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.audio_manager_fragment,container,false);

        binding.setAudioManagerViewModel(viewModel);
        progressBar = binding.progressMusicBar;
        /*progressBarThread =new Thread(new Runnable() {
            public void run() {
                while (myListener.getProgress() < viewModel.getProgressMax() || myListener ) {

                    // Update the progress bar and display the
                    //current value in the text view
                    viewModel.setProgress(myListener.getProgress());
                    progressBar.setProgress(myListener.getProgress());
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/


            binding.NextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(musicIsSelected()){
                        myListener.onNextMusic();
                        //progressBarThread.interrupt();

                    }
                }
            });

        binding.PreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(musicIsSelected()) {
                    myListener.onPreviousMusic();
                    //progressBarThread.interrupt();
                }
            }
        });

        binding.PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicIsSelected()) {
                    myListener.onPlayMusic(viewModel.getPath());

                   // progressBarThread.start();

                }

            }
        });

        binding.PauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicIsSelected()){

                    myListener.onPauseMusic();
                    progressBarThread.interrupt();
                }
            }
        });

        return binding.getRoot();
    }

    public boolean musicIsSelected(){
        boolean lreturn =false;

        if(getCurrentMusic().getTitle()==null){
            Toast.makeText(getContext(),"Any music selected, please select one :)", Toast.LENGTH_LONG).show();

        }else{
            lreturn = true;
        }

        return lreturn;
    }


}

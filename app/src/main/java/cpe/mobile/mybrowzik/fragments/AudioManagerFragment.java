package cpe.mobile.mybrowzik.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import java.util.List;

import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.databinding.AudioManagerFragmentBinding;
import cpe.mobile.mybrowzik.listeners.MyListener;
import cpe.mobile.mybrowzik.models.AudioFile;
import cpe.mobile.mybrowzik.viewModel.AudioManagerViewModel;

public class AudioManagerFragment extends Fragment {


    private AudioManagerViewModel viewModel = new AudioManagerViewModel();
    private MyListener myListener;
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




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        AudioManagerFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.audio_manager_fragment,container,false);

            binding.setAudioManagerViewModel(viewModel);
            binding.NextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myListener.onNextMusic();
                }
            });

        binding.PreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListener.onPreviousMusic();
            }
        });
        return binding.getRoot();
    }
}

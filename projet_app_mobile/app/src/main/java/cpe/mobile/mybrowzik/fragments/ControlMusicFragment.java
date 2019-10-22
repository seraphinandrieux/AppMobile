package cpe.mobile.mybrowzik.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import cpe.mobile.mybrowzik.AudioFile;
import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.databinding.ControlMusicFragmentBinding;
import cpe.mobile.mybrowzik.listener.MyListener;
import cpe.mobile.mybrowzik.viewModel.ControlMusicViewModel;

public class ControlMusicFragment extends Fragment {

    public ControlMusicFragment(){

    }

    private ControlMusicViewModel controlMusicViewModel =new ControlMusicViewModel();

    public void updateSelectMusic(AudioFile file){
        controlMusicViewModel.setAudioFile(file);
    }

    private MyListener myListener;

    public void setMyListener(MyListener listener){
        myListener=listener;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        ControlMusicFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.control_music_fragment,container,false);
        binding.setControlMusicViewModel(controlMusicViewModel);
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return binding.getRoot();
    }
}

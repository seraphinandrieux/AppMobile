package cpe.mobile.mybrowzik.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import cpe.mobile.mybrowzik.listeners.MyDBListener;
import cpe.mobile.mybrowzik.listeners.MyListener;
import cpe.mobile.mybrowzik.models.AudioFile;
import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.adapters.AudioFileListAdapter;
import cpe.mobile.mybrowzik.databinding.AudioFileListFragmentBinding;

public class AudioFileListFragment extends Fragment {

    private List<AudioFile> audioList = new ArrayList<>();


    private MyListener myListener;
    private MyDBListener myDBListener;
    public void setMyListener(MyListener listener){
        myListener = listener;
    }



    public AudioFileListFragment(List<AudioFile> audioListFile) {

        audioList = audioListFile;

    }

    public List<AudioFile> getAudioList(){
        return audioList;
    }





    @Nullable
    @Override
    public  View onCreateView(@NonNull LayoutInflater inflater,
                      @Nullable ViewGroup container,
                      @Nullable Bundle savedInstanceState){

        AudioFileListFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.audio_file_list_fragment,container,false);

        binding.audioFileList.setAdapter(new AudioFileListAdapter(audioList,myListener,myDBListener));
        binding.audioFileList.setLayoutManager(
                new LinearLayoutManager(binding.getRoot().getContext())
        );

        return binding.getRoot();
    }



    public void setMyDBListener(MyDBListener myDBListener) {
        this.myDBListener = myDBListener;
    }
}

package cpe.mobile.mybrowzik.fragments;

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

import cpe.mobile.mybrowzik.models.AudioFile;
import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.adapters.AudioFileListAdapter;
import cpe.mobile.mybrowzik.databinding.AudioFileListFragmentBinding;

public class AudioFileListFragment extends Fragment {

    private List<AudioFile> fakeList = new ArrayList<>();




    public AudioFileListFragment() {

        for (int i = 1; i <= 50; i++) {
            AudioFile audiofile = new AudioFile();
            audiofile.setAlbum("Album " +  i);
            audiofile.setArtist("Artiste " + i);
            audiofile.setDuration(200);
            audiofile.setFilePath("");
            audiofile.setGenre("Rock");
            audiofile.setTitle("Song " + i );
            fakeList.add(audiofile);

        }
    }


    @Nullable
    @Override
    public  View onCreateView(@NonNull LayoutInflater inflater,
                      @Nullable ViewGroup container,
                      @Nullable Bundle savedInstanceState){

        AudioFileListFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.audio_file_list_fragment,container,false);

        binding.audioFileList.setAdapter(new AudioFileListAdapter(fakeList));
        binding.audioFileList.setLayoutManager(
                new LinearLayoutManager(binding.getRoot().getContext())
        );

        return binding.getRoot();
    }
}

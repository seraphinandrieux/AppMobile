package cpe.mobile.mybrowzik.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.databinding.ChangeViewFragmentBinding;
import cpe.mobile.mybrowzik.listeners.ChangeViewListener;

public class ChangeViewFragment extends Fragment {
    private ChangeViewListener listener;

    public void setListener(ChangeViewListener listener){
        this.listener=listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        ChangeViewFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.change_view_fragment,container,false);
        binding.artistButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onArtists();
            }
        });
        binding.albumButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onAlbums();
            }
        });
        binding.genreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onGenre();
            }
        });
        return binding.getRoot();
    }
}

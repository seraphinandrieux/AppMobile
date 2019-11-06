package cpe.mobile.mybrowzik.fragments;

import android.os.Bundle;
import android.text.Layout;
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

import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.adapters.ArtistListAdapter;
import cpe.mobile.mybrowzik.databinding.ArtistListFragmentBinding;
import cpe.mobile.mybrowzik.listeners.MyDBListener;
import cpe.mobile.mybrowzik.models.Artist;

public class ArtistListFragment extends Fragment {
    private List<Artist> artistList = new ArrayList<>();

    private MyDBListener myDBListener;

    public ArtistListFragment(List<Artist> artistList) {
        this.artistList = artistList;
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    public void setMyDBListener(MyDBListener myDBListener) {
        this.myDBListener = myDBListener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        ArtistListFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.artist_list_fragment,container,false);

        binding.artistList.setAdapter(new ArtistListAdapter(artistList,myDBListener));
        binding.artistList.setLayoutManager(
                new LinearLayoutManager(binding.getRoot().getContext())
        );

        return binding.getRoot();
    }
}

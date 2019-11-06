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

import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.adapters.AlbumListAdapter;
import cpe.mobile.mybrowzik.databinding.AlbumListFragmentBinding;
import cpe.mobile.mybrowzik.listeners.MyDBListener;
import cpe.mobile.mybrowzik.models.Album;

public class AlbumListFragment extends Fragment {
    private List<Album> albumList = new ArrayList<>();

    private MyDBListener myDBListener;

    public AlbumListFragment(List<Album> albumList) {
        this.albumList = albumList;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }

    public void setMyDBListener(MyDBListener myDBListener) {
        this.myDBListener = myDBListener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        AlbumListFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.album_list_fragment,container,false);

        binding.albumList.setAdapter(new AlbumListAdapter(albumList,myDBListener));
        binding.albumList.setLayoutManager(
                new LinearLayoutManager(binding.getRoot().getContext())
        );

        return binding.getRoot();

    }


}

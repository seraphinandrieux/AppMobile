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
import cpe.mobile.mybrowzik.adapters.GenreListAdapter;
import cpe.mobile.mybrowzik.databinding.GenreListFragmentBinding;
import cpe.mobile.mybrowzik.models.Genre;

public class GenreListFragment extends Fragment {
    private List<Genre> genreList = new ArrayList<>();

    public GenreListFragment(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        GenreListFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.genre_list_fragment,container,false);
        binding.genreList.setAdapter(new GenreListAdapter(genreList));
        binding.genreList.setLayoutManager(
                new LinearLayoutManager(binding.getRoot().getContext())
        );

        return binding.getRoot();
    }
}

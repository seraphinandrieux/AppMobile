package cpe.mobile.mybrowzik.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.databinding.GenreItemBinding;
import cpe.mobile.mybrowzik.models.Genre;
import cpe.mobile.mybrowzik.viewModel.GenreViewModel;

public class GenreListAdapter extends RecyclerView.Adapter<GenreListAdapter.ViewHolder>{
    List<Genre> genreList;

    public GenreListAdapter(List<Genre> genreList) {
        this.genreList = genreList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GenreItemBinding binding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.genre_item,parent,false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Genre genre =genreList.get(position);
        holder.viewModel.setGenre(genre);
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private GenreItemBinding binding;
        private GenreViewModel viewModel = new GenreViewModel();
        ViewHolder(GenreItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            this.binding.setGenreViewModel(viewModel);
        }
    }
}

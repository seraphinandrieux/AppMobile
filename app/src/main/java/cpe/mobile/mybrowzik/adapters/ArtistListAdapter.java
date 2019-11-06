package cpe.mobile.mybrowzik.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.databinding.ArtistItemBinding;
import cpe.mobile.mybrowzik.listeners.MyDBListener;
import cpe.mobile.mybrowzik.models.Artist;
import cpe.mobile.mybrowzik.viewModel.ArtistViewModel;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder> {
    List<Artist> artistList;
    MyDBListener myDBListener;

    public ArtistListAdapter(List<Artist> artistList,MyDBListener myDBListener) {
        assert artistList !=null;
        this.artistList = artistList;
        this.myDBListener=myDBListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArtistItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.artist_item,parent,false
        );

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Artist artist=artistList.get(position);

        holder.viewModel.setArtist(artist);

    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }




    static class ViewHolder extends RecyclerView.ViewHolder{
        private ArtistItemBinding binding;

        private ArtistViewModel viewModel = new ArtistViewModel();

        ViewHolder(ArtistItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            this.binding.setArtistViewModel(viewModel);

        }

    }
}

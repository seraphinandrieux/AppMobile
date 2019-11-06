package cpe.mobile.mybrowzik.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.databinding.AlbumItemBinding;
import cpe.mobile.mybrowzik.databinding.ArtistItemBinding;
import cpe.mobile.mybrowzik.listeners.MyDBListener;
import cpe.mobile.mybrowzik.models.Album;
import cpe.mobile.mybrowzik.viewModel.AlbumViewModel;
import cpe.mobile.mybrowzik.viewModel.ArtistViewModel;
import cpe.mobile.mybrowzik.webServices.ImageAlbumService;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.ViewHolder> {

    List<Album> albumList;

    private MyDBListener myDBListener;

    public AlbumListAdapter(List<Album> albumList,MyDBListener myDBListener) {
        assert albumList != null;
        this.albumList = albumList;
        this.myDBListener=myDBListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AlbumItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.album_item,parent,false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album=albumList.get(position);

        ImageAlbumService imageAlbumService = new ImageAlbumService(album.getAlbumImgUrl(),holder.binding.albumImage);
        imageAlbumService.start();
        holder.viewModel.setAlbum(album);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private AlbumItemBinding binding;

        private AlbumViewModel viewModel = new AlbumViewModel();

        ViewHolder(AlbumItemBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            this.binding.setAlbumViewModel(viewModel);

        }

    }
}

package cpe.mobile.mybrowzik.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cpe.mobile.mybrowzik.DBManager.DBOpenHelper;
import cpe.mobile.mybrowzik.MainActivity;
import cpe.mobile.mybrowzik.listeners.MyHttpListener;
import cpe.mobile.mybrowzik.listeners.MyListener;
import cpe.mobile.mybrowzik.models.AudioFile;
import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.databinding.AudioFileItemBinding;
import cpe.mobile.mybrowzik.viewModel.AudioFileViewModel;
import cpe.mobile.mybrowzik.webServices.LastFMService;

public class AudioFileListAdapter extends RecyclerView.Adapter<AudioFileListAdapter.ViewHolder>{
    List<AudioFile> audioFileList;
    MyListener myListener;
    MyHttpListener myHttpListener;


    public AudioFileListAdapter(List<AudioFile> fileList, MyListener pMyListener) {
        assert fileList != null;
        audioFileList = fileList;
        myListener    = pMyListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            AudioFileItemBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.audio_file_item, parent,false
            );


            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myListener.onSelectMusic(binding.getAudioFileViewModel().getAudioFile());
                }
            });

            return new ViewHolder(binding);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            AudioFile file = audioFileList.get(position);


            MyHttpListener myHttpListener = initMyHttpListener(file,holder);



            LastFMService myFmService = new LastFMService(file,myHttpListener); // we start a thread which will call the webservices
            myFmService.start();



            holder.viewModel.setAudioFile(file);

        }

        @Override
        public int getItemCount() {

            return audioFileList.size();

        }

    public MyHttpListener initMyHttpListener(AudioFile file, ViewHolder holder) {
        MyHttpListener httpListener = new MyHttpListener() {
            @Override
            public void setAlbum(String album) { //update the album name

                file.setAlbum(album);
                holder.viewModel.setAudioFile(file);

            }

            @Override
            public void setAlbumPath(String albumPath) { //update the album link image


                file.setAlbumPath(albumPath);
                holder.viewModel.setAudioFile(file);

            }

            @Override
            public ImageView getMyCurrentImageView(){ // allows to get back the image album's imageView
                return holder.binding.albumImage;
            }

        };
        return httpListener;

    }




        static class ViewHolder extends RecyclerView.ViewHolder {


            private AudioFileItemBinding binding;


            private AudioFileViewModel viewModel = new AudioFileViewModel();


            ViewHolder(AudioFileItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
                this.binding.setAudioFileViewModel(viewModel);
            }
        }

       /* public MyHttpListener initMyHttpListener(AudioFileViewModel pViewModel, LastFMService myFmService){
            MyHttpListener httpListener = new MyHttpListener() {
            @Override
            public String getAlbum(String title, String artist) {
                myFmService.getInfoTrack(pViewModel.getTitle(),pViewModel.getArtist());
                return "ok";
            }
        };
        return httpListener ;
    }*/


}

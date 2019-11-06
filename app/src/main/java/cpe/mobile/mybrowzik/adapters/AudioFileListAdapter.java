package cpe.mobile.mybrowzik.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cpe.mobile.mybrowzik.listeners.MyDBListener;
import cpe.mobile.mybrowzik.listeners.MyHttpListener;
import cpe.mobile.mybrowzik.listeners.MyListener;
import cpe.mobile.mybrowzik.models.AudioFile;
import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.databinding.AudioFileItemBinding;
import cpe.mobile.mybrowzik.viewModel.AudioFileViewModel;
import cpe.mobile.mybrowzik.webServices.ImageAlbumService;
import cpe.mobile.mybrowzik.webServices.LastFMService;

public class AudioFileListAdapter extends RecyclerView.Adapter<AudioFileListAdapter.ViewHolder>{
    List<AudioFile> audioFileList;
    MyListener myListener;
    MyDBListener myDBListener;


    public AudioFileListAdapter(List<AudioFile> fileList, MyListener pMyListener, MyDBListener myDBListener) {
        assert fileList != null;
        audioFileList = fileList;
        myListener    = pMyListener;
        this.myDBListener         = myDBListener;
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



            if(!myDBListener.isInMyDB(file.getTitle())){
                LastFMService myFmService = new LastFMService(file,myHttpListener,myDBListener,holder.binding.albumImage); // we start a thread which will call the webservices
                try {
                    myFmService.start();
                }catch(Exception e){
                    System.out.println(e);
                }
            }else{

                file = fillAudioFileFromDB(file);
                ImageAlbumService myImageAlbumService = new ImageAlbumService(file.getAlbumPath(),holder.binding.albumImage); // we inside the url + the listener which contains the ImageView to update
                myImageAlbumService.start();
            }



            holder.viewModel.setAudioFile(file);

        }

        @Override
        public int getItemCount() {

            return audioFileList.size();

        }

        public AudioFile fillAudioFileFromDB(AudioFile audioFile){
            AudioFile newAudioFile = audioFile;

            String title;


            title = newAudioFile.getTitle();

            newAudioFile.setAlbum(myDBListener.getAlbumFromID(myDBListener.getAlbumIDFromTitle(title)));
            newAudioFile.setAlbumPath(myDBListener.getImageAlbumFromAlbumID(myDBListener.getAlbumIDFromTitle(title)));
            newAudioFile.setYear(myDBListener.getYearFromAlbumID(myDBListener.getAlbumIDFromTitle(title)));
            newAudioFile.setGenre(myDBListener.getGenreFromID(myDBListener.getGenreIDFromTitle(title)));

            return newAudioFile;
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
            public void setGenre(String genre) { //update the album link image


                file.setGenre(genre);
                holder.viewModel.setAudioFile(file);

            }

            @Override
            public void setYear(Integer year) { //update the album link image


                file.setYear(year);
                holder.viewModel.setAudioFile(file);

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




}

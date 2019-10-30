package cpe.mobile.mybrowzik.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cpe.mobile.mybrowzik.AudioFile;
import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.databinding.AudioFileItemBinding;
import cpe.mobile.mybrowzik.listener.MyListener;
import cpe.mobile.mybrowzik.viewModel.AudioFileViewModel;

public class AudioFileListAdapter extends RecyclerView.Adapter<AudioFileListAdapter.ViewHolder>{
    List<AudioFile> audioFileList;
    AudioFileViewModel viewModel;
    MyListener myListener;

    public AudioFileListAdapter(List<AudioFile> fileList, MyListener listener) {
        assert fileList != null;
        audioFileList = fileList;
        myListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            AudioFileItemBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.audio_file_item, parent,false
            );
            binding.getRoot().setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    myListener.onSelectMusic(binding.getAudioFileViewModel().getAudioFile());
                }
            });
            return new ViewHolder(binding);
        }


        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            AudioFile file = audioFileList.get(position);
            holder.viewModel.setAudioFile(file);
        }

        @Override
        public int getItemCount() {
            return audioFileList.size();
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
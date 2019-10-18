package cpe.mobile.mybrowzik.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.databinding.AudioManagerFragmentBinding;
import cpe.mobile.mybrowzik.listeners.MyListener;
import cpe.mobile.mybrowzik.models.AudioFile;
import cpe.mobile.mybrowzik.viewModel.AudioManagerViewModel;

public class AudioManagerFragment extends Fragment {


    static class ViewHolder extends RecyclerView.ViewHolder {


        private AudioManagerFragmentBinding binding;


        private AudioManagerViewModel viewModel = new AudioManagerViewModel();


        ViewHolder(AudioManagerFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setAudioFileViewModel(viewModel);

        }
    }



    MyListener listener = new MyListener() {
        @Override
        public void onSelectMusic(AudioFile audioFile) {
            this.

        }
    };



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        AudioManagerFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.audio_manager_fragment,container,false);

        return binding.getRoot();
    }
}

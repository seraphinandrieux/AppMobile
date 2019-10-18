package cpe.mobile.mybrowzik.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import cpe.mobile.mybrowzik.R;
import cpe.mobile.mybrowzik.databinding.AudioManagerFragmentBinding;

public class AudioManagerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        AudioManagerFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.audio_manager_fragment,container,false);

        return binding.getRoot();
    }
}

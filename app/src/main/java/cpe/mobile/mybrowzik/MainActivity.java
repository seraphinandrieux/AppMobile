package cpe.mobile.mybrowzik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import cpe.mobile.mybrowzik.databinding.ActivityMainBinding;
import cpe.mobile.mybrowzik.fragments.AudioFileListFragment;
import cpe.mobile.mybrowzik.fragments.AudioManagerFragment;
import cpe.mobile.mybrowzik.listeners.MyListener;
import cpe.mobile.mybrowzik.models.AudioFile;

/*
Question a poser :
je veux faire un onclick sur mon item, c'est ou dans l'adapter ou avant l'appel de l'adapter, et ou ca car je vois pas mon objet
mon listener plus dans mon fragment ? si oui dois-je instancier dans celui ci mon viewholder ? qu'est ce que vraiment mon view holder ? 




 */
public  class MainActivity  extends AppCompatActivity  {

    private ActivityMainBinding binding;





    public void showStartup() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        AudioFileListFragment fragment = new AudioFileListFragment();

        AudioManagerFragment fragmentManager = new AudioManagerFragment();

        transaction.replace(R.id.musicListLayout,fragment);

        transaction.replace(R.id.musicManagerLayout,fragmentManager);
        //transaction.add(fragmentManager);
        transaction.commit();
        //fragment.setMyListener(listener);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        showStartup();


    }


}

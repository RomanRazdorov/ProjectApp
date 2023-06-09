package com.example.flutte.UI.StateHolder.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flutte.Data.Models.AudioModel;
import com.example.flutte.Data.Repository.AudioRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainPageViewModel extends AndroidViewModel {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final AudioRepository audioRepository;
    private final MutableLiveData<List<AudioModel>> songsListLiveData = new MutableLiveData<>();

    public MainPageViewModel(Application application) {
        super(application);
        audioRepository = new AudioRepository(application);
    }

    public LiveData<List<AudioModel>> getSongsListLiveData() {
        return songsListLiveData;
    }

    public void loadSongsList() {
        executor.execute(() -> {
            List<AudioModel> songsList = audioRepository.getSongsList();
            songsListLiveData.postValue(songsList);
        });
    }
}

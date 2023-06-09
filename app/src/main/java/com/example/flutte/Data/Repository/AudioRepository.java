package com.example.flutte.Data.Repository;

import android.app.Application;

import com.example.flutte.Data.DataSources.AudioDataSource;
import com.example.flutte.Data.Models.AudioModel;

import java.util.List;


public class AudioRepository {


    private final AudioDataSource audioDataSource;

    public AudioRepository(Application application) {
        audioDataSource = new AudioDataSource(application.getContentResolver());
    }


    public List<AudioModel> getSongsList() {
        return audioDataSource.getSongsList();
    }
}

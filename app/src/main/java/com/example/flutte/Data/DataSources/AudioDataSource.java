package com.example.flutte.Data.DataSources;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.provider.MediaStore;

import com.example.flutte.Data.Models.AudioModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AudioDataSource {
    private final ContentResolver contentResolver;

    public AudioDataSource(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;

    }

    public List<AudioModel> getSongsList() {
        List<AudioModel> songsList = new ArrayList<>();

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        try (Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null)) {
            while (cursor.moveToNext()) {
                AudioModel songData = new AudioModel(cursor.getString(1), cursor.getString(0), cursor.getString(2));
                if (new File(songData.getPath()).exists()) {
                    songsList.add(songData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return songsList;
    }
}

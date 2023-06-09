package com.example.flutte.UI.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.flutte.Data.Models.AudioModel;
import com.example.flutte.UI.StateHolder.Adapters.MusicListAdapter;
import com.example.flutte.UI.StateHolder.ViewModel.MainPageViewModel;
import com.example.flutte.databinding.FragmentMainPageBinding;

import java.util.ArrayList;
import java.util.List;


public class MainPageFragment extends Fragment {

    private FragmentMainPageBinding mainPageBinding;
    private MainPageViewModel mainPageViewModel;
    private MusicListAdapter adapter;
    private RecyclerView recyclerView;

    
    public MainPageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                    mainPageBinding = FragmentMainPageBinding.inflate(inflater);
        return mainPageBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = mainPageBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MusicListAdapter(new ArrayList<>(), requireContext().getApplicationContext());
        recyclerView.setAdapter(adapter);
        mainPageViewModel = new ViewModelProvider(this).get(MainPageViewModel.class);
        mainPageViewModel.getSongsListLiveData().observe(getViewLifecycleOwner(), this::updateSongsList);

        if (!checkPermission()) {
            requestPermission();
            return;
        }
        mainPageViewModel.loadSongsList();
    }

    private void updateSongsList(List<AudioModel> songsList) {
        if (songsList == null || songsList.isEmpty()) {
            adapter = new MusicListAdapter(new ArrayList<>(), requireContext().getApplicationContext());
            recyclerView.setAdapter(adapter);
            mainPageBinding.noSongsText.setVisibility(View.VISIBLE);
        } else {
            adapter = new MusicListAdapter(songsList, requireContext().getApplicationContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            mainPageBinding.noSongsText.setVisibility(View.GONE);
        }
    }

    boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(requireContext(), "READ PERMISSION IS REQUIRED,PLEASE ALLOW FROM SETTINGS", Toast.LENGTH_SHORT).show();
        } else
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
    }
}
package com.example.flutte.UI.StateHolder.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flutte.Data.Models.AudioModel;
import com.example.flutte.R;
import com.example.flutte.UI.StateHolder.Holders.ViewHolder;
import com.example.flutte.UI.View.MainPageFragmentDirections;
import com.example.flutte.UI.View.MyMediaPlayer;
import com.example.flutte.UI.View.MusicPlayerFragment;
import com.example.flutte.databinding.RecyclerItemBinding;

import java.util.ArrayList;
import java.util.List;


public class MusicListAdapter extends RecyclerView.Adapter<ViewHolder>{

    RecyclerItemBinding binding;
    List<AudioModel> songsList;
    Context context;

    public MusicListAdapter(List<AudioModel> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AudioModel songData = songsList.get(position);
        holder.getTitleTextView().setText(songData.getTitle());

        if(MyMediaPlayer.currentIndex==position){
            holder.getTitleTextView().setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.getTitleTextView().setTextColor(Color.parseColor("#000000"));
        }

        holder.itemView.setOnClickListener(v -> {

            MyMediaPlayer.getInstance().reset();
            MyMediaPlayer.currentIndex = holder.getAdapterPosition();
            Intent intent = new Intent(context, MusicPlayerFragment.class);
            intent.putExtra("LIST", new ArrayList<>(songsList));
            context.startActivity(intent);

            Navigation.findNavController(v).navigate(R.id.action_mainPageFragment_to_musicPlayerFragment);

        });
    }


    @Override
    public int getItemCount() {
        return songsList.size();
    }


}



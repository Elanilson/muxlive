package br.com.apkdoandroid.muxlive.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.apkdoandroid.muxlive.databinding.LayoutLiveBinding;
import br.com.apkdoandroid.muxlive.databinding.TransmissaoLayoutBinding;
import br.com.apkdoandroid.muxlive.entities.LiveStream;
import br.com.apkdoandroid.muxlive.interfaces.OnLiveStream;
import br.com.apkdoandroid.muxlive.viewHolder.ViewHolderLiveDisponiveis;

public class LiveDisponiveisAdapter extends RecyclerView.Adapter<ViewHolderLiveDisponiveis> {

    private List<LiveStream> liveStreams = new ArrayList<>();
    private OnLiveStream listener;

    @NonNull
    @Override
    public ViewHolderLiveDisponiveis onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
       LayoutLiveBinding binding = LayoutLiveBinding.inflate(inflater,parent,false);
        return new ViewHolderLiveDisponiveis(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLiveDisponiveis holder, int position) {
        holder.bind(liveStreams.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return liveStreams.size();
    }


    public void attackLiveStrame(List<LiveStream> liveStreams){
        this.liveStreams.clear();
        this.liveStreams = liveStreams;
        notifyDataSetChanged();

    }

    public void attackListener(OnLiveStream listener){
        this.listener = listener;
    }

}

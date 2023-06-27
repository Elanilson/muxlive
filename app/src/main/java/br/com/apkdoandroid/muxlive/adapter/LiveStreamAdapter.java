package br.com.apkdoandroid.muxlive.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.apkdoandroid.muxlive.R;
import br.com.apkdoandroid.muxlive.databinding.TransmissaoLayoutBinding;
import br.com.apkdoandroid.muxlive.entities.LiveStream;
import br.com.apkdoandroid.muxlive.interfaces.OnLiveStream;
import br.com.apkdoandroid.muxlive.viewHolder.ViewHolderLiveStream;

public class LiveStreamAdapter extends RecyclerView.Adapter<ViewHolderLiveStream> {

    private List<LiveStream> liveStreams = new ArrayList<>();
    private OnLiveStream listener;

    @NonNull
    @Override
    public ViewHolderLiveStream onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
       TransmissaoLayoutBinding binding = TransmissaoLayoutBinding.inflate(inflater,parent,false);
        return new ViewHolderLiveStream(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLiveStream holder, int position) {
        holder.bind(liveStreams.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return liveStreams.size();
    }


    public void attackLiveStrame(List<LiveStream> liveStreams){
        this.liveStreams = liveStreams;
        notifyDataSetChanged();
    }

    public void attackListener(OnLiveStream listener){
        this.listener = listener;
    }

}

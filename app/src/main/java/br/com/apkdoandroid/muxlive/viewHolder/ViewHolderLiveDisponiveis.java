package br.com.apkdoandroid.muxlive.viewHolder;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import br.com.apkdoandroid.muxlive.R;
import br.com.apkdoandroid.muxlive.databinding.LayoutLiveBinding;
import br.com.apkdoandroid.muxlive.databinding.TransmissaoLayoutBinding;
import br.com.apkdoandroid.muxlive.entities.LiveStream;
import br.com.apkdoandroid.muxlive.helper.DateUtils;
import br.com.apkdoandroid.muxlive.interfaces.OnLiveStream;

public class ViewHolderLiveDisponiveis extends RecyclerView.ViewHolder {
    private LayoutLiveBinding binding;
    public ViewHolderLiveDisponiveis(LayoutLiveBinding itemview) {
        super(itemview.getRoot());

        binding = itemview;
    }

    public void bind(LiveStream liveStream, OnLiveStream listener){
        if(liveStream.getStatus().equals("active")){
            binding.textViewStatus.setText("Ativo");
            binding.textViewStatus.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(),R.color.verde));
        }else if(liveStream.getStatus().equals("idle")){
            binding.textViewStatus.setText("Parado");
            binding.textViewStatus.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(),R.color.cinza300));
        }else if(liveStream.getStatus().equals("disabled")){
            binding.textViewStatus.setText("Desativado");
            binding.textViewStatus.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(),R.color.vermelho));
        }else{
            binding.textViewStatus.setText("Parado");
            binding.textViewStatus.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(),R.color.cinza300));
        }

        binding.textViewDataCriacao.setText(DateUtils.convertMillisToDateTime(liveStream.getCreated_at()));


        binding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickAssistir(liveStream);
            }
        });

    }
}

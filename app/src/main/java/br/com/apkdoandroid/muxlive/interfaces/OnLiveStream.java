package br.com.apkdoandroid.muxlive.interfaces;

import br.com.apkdoandroid.muxlive.entities.LiveStream;

public interface OnLiveStream {
    void onClickTransmitir(LiveStream liveStream);
    void onClickAssistir(LiveStream liveStream);
    void onDelete(LiveStream liveStream);
}

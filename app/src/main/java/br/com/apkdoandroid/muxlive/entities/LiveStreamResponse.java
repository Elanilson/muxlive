package br.com.apkdoandroid.muxlive.entities;

import java.util.List;

public class LiveStreamResponse {

    /**
     * data : {"test":true,"stream_key":"6f69c422-aaca-ca0a-0572-3ddd37da36f8","status":"idle","reconnect_window":60,"playback_ids":[{"policy":"public","id":"TlTslXh3r8ThpWY1SAmYfd8zbtCu6cktFSu8iP602Ycg"}],"new_asset_settings":{"playback_policies":["public"]},"max_continuous_duration":300,"latency_mode":"standard","id":"vGwZuX2uHMHCIlkR025Fl00lSAOhYxLoITjtNoDyVyMoQ","created_at":"1687802733"}
     */

    private LiveStream data;

    public LiveStream getData() {
        return data;
    }

    public void setData(LiveStream data) {
        this.data = data;
    }


}

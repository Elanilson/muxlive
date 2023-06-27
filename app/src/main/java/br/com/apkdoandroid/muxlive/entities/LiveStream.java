package br.com.apkdoandroid.muxlive.entities;

import java.util.List;

public class LiveStream {
    /**
     * test : true
     * stream_key : 6f69c422-aaca-ca0a-0572-3ddd37da36f8
     * status : idle
     * reconnect_window : 60
     * playback_ids : [{"policy":"public","id":"TlTslXh3r8ThpWY1SAmYfd8zbtCu6cktFSu8iP602Ycg"}]
     * new_asset_settings : {"playback_policies":["public"]}
     * max_continuous_duration : 300
     * latency_mode : standard
     * id : vGwZuX2uHMHCIlkR025Fl00lSAOhYxLoITjtNoDyVyMoQ
     * created_at : 1687802733
     */

    private boolean test;
    private String stream_key;
    private String status;
    private int reconnect_window;
    private NewAssetSettingsBean new_asset_settings;
    private int max_continuous_duration;
    private String latency_mode;
    private String id;
    private String created_at;
    private List<Playback_ids> playback_ids;

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public String getStream_key() {
        return stream_key;
    }

    public void setStream_key(String stream_key) {
        this.stream_key = stream_key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReconnect_window() {
        return reconnect_window;
    }

    public void setReconnect_window(int reconnect_window) {
        this.reconnect_window = reconnect_window;
    }

    public NewAssetSettingsBean getNew_asset_settings() {
        return new_asset_settings;
    }

    public void setNew_asset_settings(NewAssetSettingsBean new_asset_settings) {
        this.new_asset_settings = new_asset_settings;
    }

    public int getMax_continuous_duration() {
        return max_continuous_duration;
    }

    public void setMax_continuous_duration(int max_continuous_duration) {
        this.max_continuous_duration = max_continuous_duration;
    }

    public String getLatency_mode() {
        return latency_mode;
    }

    public void setLatency_mode(String latency_mode) {
        this.latency_mode = latency_mode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public List<Playback_ids> getPlayback_ids() {
        return playback_ids;
    }

    public void setPlayback_ids(List<Playback_ids> playback_ids) {
        this.playback_ids = playback_ids;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "test=" + test +
                ", stream_key='" + stream_key + '\'' +
                ", status='" + status + '\'' +
                ", reconnect_window=" + reconnect_window +
                ", new_asset_settings=" + new_asset_settings +
                ", max_continuous_duration=" + max_continuous_duration +
                ", latency_mode='" + latency_mode + '\'' +
                ", id='" + id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", playback_ids=" + playback_ids +
                '}';
    }

    public static class Playback_ids {
        /**
         * policy : public
         * id : TlTslXh3r8ThpWY1SAmYfd8zbtCu6cktFSu8iP602Ycg
         */

        private String policy;
        private String id;

        public String getPolicy() {
            return policy;
        }

        public void setPolicy(String policy) {
            this.policy = policy;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Playback_ids{" +
                    "policy='" + policy + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }
}

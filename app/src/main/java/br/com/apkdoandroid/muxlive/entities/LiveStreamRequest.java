package br.com.apkdoandroid.muxlive.entities;

import java.util.List;

public class LiveStreamRequest {

    /**
     * playback_policy : ["public"]
     * new_asset_settings : {"playback_policy":["public"]}
     */

    private NewAssetSettingsBean new_asset_settings;
    private List<String> playback_policy;

    public NewAssetSettingsBean getNew_asset_settings() {
        return new_asset_settings;
    }

    public void setNew_asset_settings(NewAssetSettingsBean new_asset_settings) {
        this.new_asset_settings = new_asset_settings;
    }

    public List<String> getPlayback_policy() {
        return playback_policy;
    }

    public void setPlayback_policy(List<String> playback_policy) {
        this.playback_policy = playback_policy;
    }

}

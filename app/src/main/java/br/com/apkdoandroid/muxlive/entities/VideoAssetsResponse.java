package br.com.apkdoandroid.muxlive.entities;

import java.util.List;

public class VideoAssetsResponse {

    /**
     * data : {"tracks":[{"type":"video","max_width":1080,"max_height":1920,"max_frame_rate":30,"id":"N6gOjJ02eKhRZN5pDp01qiVv800QzR8RR3lFLK6J91dZvU","max_channels":2,"max_channel_layout":"stereo"},{"type":"audio","max_channels":2,"max_channel_layout":"stereo","id":"gBLbKhR2IgouEvnE1mV2lihd84jzMOAor66m01bNHrMI"}],"test":true,"status":"ready","recording_times":[{"type":"content","started_at":"2023-06-29T17:11:03.567Z","duration":13.908}],"playback_ids":[{"policy":"public","id":"UbLe4wJ1OGRhk02J3fSP0266u001SKee9P8WP65twr6nEU"}],"mp4_support":"none","max_stored_resolution":"HD","max_stored_frame_rate":30,"master_access":"none","live_stream_id":"n027fTneylpKPqCNNpu5bxl35hatgDOkwxq6BsWPFQI00","id":"pTg00ya7FkB6nbwTmFgNHIBvcnT0001F5hYhIscJaX2oqk","duration":13.908333333333333,"created_at":"1688058659","aspect_ratio":"9:16"}
     */

    private VideoAssets data;

    public VideoAssets getData() {
        return data;
    }

    public void setData(VideoAssets data) {
        this.data = data;
    }


}

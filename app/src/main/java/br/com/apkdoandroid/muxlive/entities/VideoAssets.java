package br.com.apkdoandroid.muxlive.entities;

import java.util.List;

public  class VideoAssets {
    /**
     * tracks : [{"type":"video","max_width":1080,"max_height":1920,"max_frame_rate":30,"id":"N6gOjJ02eKhRZN5pDp01qiVv800QzR8RR3lFLK6J91dZvU"},{"type":"audio","max_channels":2,"max_channel_layout":"stereo","id":"gBLbKhR2IgouEvnE1mV2lihd84jzMOAor66m01bNHrMI"}]
     * test : true
     * status : ready
     * recording_times : [{"type":"content","started_at":"2023-06-29T17:11:03.567Z","duration":13.908}]
     * playback_ids : [{"policy":"public","id":"UbLe4wJ1OGRhk02J3fSP0266u001SKee9P8WP65twr6nEU"}]
     * mp4_support : none
     * max_stored_resolution : HD
     * max_stored_frame_rate : 30
     * master_access : none
     * live_stream_id : n027fTneylpKPqCNNpu5bxl35hatgDOkwxq6BsWPFQI00
     * id : pTg00ya7FkB6nbwTmFgNHIBvcnT0001F5hYhIscJaX2oqk
     * duration : 13.908333333333333
     * created_at : 1688058659
     * aspect_ratio : 9:16
     */

    private boolean test;
    private String status;
    private String mp4_support;
    private String max_stored_resolution;
    private int max_stored_frame_rate;
    private String master_access;
    private String live_stream_id;
    private String id;
    private double duration;
    private String created_at;
    private String aspect_ratio;
    private List<TracksBean> tracks;
    private List<RecordingTimesBean> recording_times;
    private List<PlaybackIdsBean> playback_ids;

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMp4_support() {
        return mp4_support;
    }

    public void setMp4_support(String mp4_support) {
        this.mp4_support = mp4_support;
    }

    public String getMax_stored_resolution() {
        return max_stored_resolution;
    }

    public void setMax_stored_resolution(String max_stored_resolution) {
        this.max_stored_resolution = max_stored_resolution;
    }

    public int getMax_stored_frame_rate() {
        return max_stored_frame_rate;
    }

    public void setMax_stored_frame_rate(int max_stored_frame_rate) {
        this.max_stored_frame_rate = max_stored_frame_rate;
    }

    public String getMaster_access() {
        return master_access;
    }

    public void setMaster_access(String master_access) {
        this.master_access = master_access;
    }

    public String getLive_stream_id() {
        return live_stream_id;
    }

    public void setLive_stream_id(String live_stream_id) {
        this.live_stream_id = live_stream_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(String aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public List<TracksBean> getTracks() {
        return tracks;
    }

    public void setTracks(List<TracksBean> tracks) {
        this.tracks = tracks;
    }

    public List<RecordingTimesBean> getRecording_times() {
        return recording_times;
    }

    public void setRecording_times(List<RecordingTimesBean> recording_times) {
        this.recording_times = recording_times;
    }

    public List<PlaybackIdsBean> getPlayback_ids() {
        return playback_ids;
    }

    public void setPlayback_ids(List<PlaybackIdsBean> playback_ids) {
        this.playback_ids = playback_ids;
    }

    public static class TracksBean {
        /**
         * type : video
         * max_width : 1080
         * max_height : 1920
         * max_frame_rate : 30
         * id : N6gOjJ02eKhRZN5pDp01qiVv800QzR8RR3lFLK6J91dZvU
         * max_channels : 2
         * max_channel_layout : stereo
         */

        private String type;
        private int max_width;
        private int max_height;
        private int max_frame_rate;
        private String id;
        private int max_channels;
        private String max_channel_layout;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getMax_width() {
            return max_width;
        }

        public void setMax_width(int max_width) {
            this.max_width = max_width;
        }

        public int getMax_height() {
            return max_height;
        }

        public void setMax_height(int max_height) {
            this.max_height = max_height;
        }

        public int getMax_frame_rate() {
            return max_frame_rate;
        }

        public void setMax_frame_rate(int max_frame_rate) {
            this.max_frame_rate = max_frame_rate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getMax_channels() {
            return max_channels;
        }

        public void setMax_channels(int max_channels) {
            this.max_channels = max_channels;
        }

        public String getMax_channel_layout() {
            return max_channel_layout;
        }

        public void setMax_channel_layout(String max_channel_layout) {
            this.max_channel_layout = max_channel_layout;
        }
    }

    public static class RecordingTimesBean {
        /**
         * type : content
         * started_at : 2023-06-29T17:11:03.567Z
         * duration : 13.908
         */

        private String type;
        private String started_at;
        private double duration;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStarted_at() {
            return started_at;
        }

        public void setStarted_at(String started_at) {
            this.started_at = started_at;
        }

        public double getDuration() {
            return duration;
        }

        public void setDuration(double duration) {
            this.duration = duration;
        }
    }

    public static class PlaybackIdsBean {
        /**
         * policy : public
         * id : UbLe4wJ1OGRhk02J3fSP0266u001SKee9P8WP65twr6nEU
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
    }
}

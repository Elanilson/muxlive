package br.com.apkdoandroid.muxlive.entities;

import java.util.List;

public class LiveStream {

        /**
         * test : true
         * stream_key : e610a69c-6ba2-b79d-566d-0381accb84d0
         * status : idle
         * recording : true
         * reconnect_window : 60
         * recent_asset_ids : ["003QZ19GyMKxrGRNDSC8tdOo9x28cZbbgxCcvNHPE9GA","pTg00ya7FkB6nbwTmFgNHIBvcnT0001F5hYhIscJaX2oqk","BQsaJS14r7HNiu9Znh02W01mYJan2EiJzpZ3TGUM9srwE"]
         * playback_ids : [{"policy":"public","id":"kKXK600jRNBl4zQobRMGESYOuToNlWcQNQoD01opZ6D3w"}]
         * new_asset_settings : {"playback_policies":["public"]}
         * max_continuous_duration : 300
         * latency_mode : standard
         * id : n027fTneylpKPqCNNpu5bxl35hatgDOkwxq6BsWPFQI00
         * created_at : 1688058596
         * connected : true
         * active_asset_id : Stbk0201J00cFt6RwpN16VLvIS6rGA4p01mr7f02tL8AL1lE
         */

        private boolean test;
        private String stream_key;
        private String status;
        private boolean recording;
        private int reconnect_window;
        private NewAssetSettingsBean new_asset_settings;
        private int max_continuous_duration;
        private String latency_mode;
        private String id;
        private String created_at;
        private boolean connected;
        private String active_asset_id;
        private List<String> recent_asset_ids;
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

        public boolean isRecording() {
            return recording;
        }

        public void setRecording(boolean recording) {
            this.recording = recording;
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

        public boolean isConnected() {
            return connected;
        }

        public void setConnected(boolean connected) {
            this.connected = connected;
        }

        public String getActive_asset_id() {
            return active_asset_id;
        }

        public void setActive_asset_id(String active_asset_id) {
            this.active_asset_id = active_asset_id;
        }

        public List<String> getRecent_asset_ids() {
            return recent_asset_ids;
        }

        public void setRecent_asset_ids(List<String> recent_asset_ids) {
            this.recent_asset_ids = recent_asset_ids;
        }

        public List<Playback_ids> getPlayback_ids() {
            return playback_ids;
        }

        public void setPlayback_ids(List<Playback_ids> playback_ids) {
            this.playback_ids = playback_ids;
        }

        public static class Playback_ids {
            /**
             * policy : public
             * id : kKXK600jRNBl4zQobRMGESYOuToNlWcQNQoD01opZ6D3w
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

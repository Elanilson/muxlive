package br.com.apkdoandroid.muxlive.api;

import br.com.apkdoandroid.muxlive.entities.LiveStreamRequest;
import br.com.apkdoandroid.muxlive.entities.LiveStreamResponse;
import br.com.apkdoandroid.muxlive.entities.LiveStreamsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MuxApiService {

    @POST("/video/v1/live-streams")
    Call<LiveStreamResponse> createLiveStream(
            @Body LiveStreamRequest liveStreamRequest
    );

    @GET("/video/v1/live-streams")
    Call<LiveStreamsResponse> getLiveStreams();

    @DELETE("/video/v1/live-streams/{LIVE_STREAM_ID}")
    Call<Void> deleteLiveStream(@Path("LIVE_STREAM_ID") String id);
}

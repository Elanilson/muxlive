package br.com.apkdoandroid.muxlive.api;

import android.content.Context;

import java.io.IOException;

import br.com.apkdoandroid.muxlive.helper.Constantes;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit INSTANCE;

    private RetrofitClient(){}

    private static Retrofit getRetrofitInstance() {

        synchronized (RetrofitClient.class){
            if(INSTANCE == null){
                INSTANCE = new Retrofit.Builder()
                        .baseUrl("https://api.mux.com")
                        .client(clientHttp().build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }

        return INSTANCE;
    }

    public static <T> T classService(Class<T> classService){
        return getRetrofitInstance().create(classService);
    }

    private static OkHttpClient.Builder clientHttp(){
        return new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(Constantes.TOKEN,Constantes.TOKEN_SECRETE));

    }

    private static class  BasicAuthInterceptor implements  Interceptor{
        private String credentials;

        public BasicAuthInterceptor(String username, String password) {
            this.credentials = Credentials.basic(username, password);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    .addHeader("Authorization",credentials)
                    .build();
            return chain.proceed(request);
        }
    }

}

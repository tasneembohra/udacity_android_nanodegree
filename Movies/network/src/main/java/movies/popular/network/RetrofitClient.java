package movies.popular.network;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private RetrofitClient() {
    }

    public static ApiInterface getClient() {

        OkHttpClient client = new OkHttpClient.Builder()
                // this is my interceptor to set the authorization header
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Accept", "application/json")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .protocols(Arrays.asList(/*Protocol.HTTP_2, */Protocol.HTTP_1_1))
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.apiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiInterface.class);
    }
}

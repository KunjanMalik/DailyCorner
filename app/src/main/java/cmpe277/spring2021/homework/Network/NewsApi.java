package cmpe277.spring2021.homework.Network;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Class to instantiate the Retrofit and Moshi.
 */
public  class NewsApi {
    private Moshi moshi;
    private Retrofit retrofit;
    String BASE_URL = "https://newsapi.org/v2/";

    NewsAPIService retrofitService;

    public NewsApi (){
         moshi = new Moshi.Builder()
                .add(new KotlinJsonAdapterFactory())
                .build();

       retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build();


    }

    public final NewsAPIService getRetrofitService() {
       return retrofit.create(NewsAPIService.class);
    }


}




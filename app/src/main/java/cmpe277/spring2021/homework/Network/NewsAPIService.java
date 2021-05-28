package cmpe277.spring2021.homework.Network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Inteface for the Api's to fetch the headlines,search query.
 */
interface NewsAPIService {


    @GET("top-headlines")
    Call<HeadLines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );


    @GET("everything")
    Call<HeadLines> getSpecificData(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<HeadLines> getHeadlines(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey
    );
}

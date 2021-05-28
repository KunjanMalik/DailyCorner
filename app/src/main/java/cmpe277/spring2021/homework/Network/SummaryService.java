package cmpe277.spring2021.homework.Network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SummaryService {

    String API_ROUTE = "summarize";
    @Headers({

            "Content-type: application/json",
            "x-rapidapi-key:  ",
            "x-rapidapi-host: text-monkey-summarizer.p.rapidapi.com"

    })
    @POST(API_ROUTE)
    Call<Posts> sendPosts(@Body Posts posts);
}

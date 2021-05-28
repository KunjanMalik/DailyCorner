package cmpe277.spring2021.homework.Network;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * View Model class to get the HTTP response using retrofit.
 */
public class  NewsViewModel extends ViewModel {

    public static MutableLiveData<List<Articles>> mutableLiveData = new MutableLiveData<>();
    public static NewsApi api;
    private static final String API_KEY = "6476a42f3c4c4707a3d2f1cd66f0c9ad";
    String country;
    public NewsViewModel() {
         country = getCountry();

        retreiveNews(null,null);
    }


         public MutableLiveData<List<Articles>> retreiveNews(String query , String category ){
             api = new NewsApi();

             Call<HeadLines> call;
             if (category !=null && !category.isEmpty()){
                 call= api.getRetrofitService().getHeadlines(country, category,API_KEY);
             } else if(query !=null && !query.isEmpty()){
                 call= api.getRetrofitService().getSpecificData(query,API_KEY);
             }else {
                 call= api.getRetrofitService().getHeadlines(country,API_KEY);
             }

             call.enqueue(new Callback<HeadLines>() {
                 @Override
                 public void onResponse(Call<HeadLines> call, Response<HeadLines> response) {
                     if (response.isSuccessful() && response.body().getArticles() != null){
                         List<Articles> articles = response.body().getArticles();
                         mutableLiveData.setValue(articles);
                     }
                 }

                 @Override
                 public void onFailure(Call<HeadLines> call, Throwable t) {
                  // Toast.makeText(this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                 }
             });
             return mutableLiveData;
         }

    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

}

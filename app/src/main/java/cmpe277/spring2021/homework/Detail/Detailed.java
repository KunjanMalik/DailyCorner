package cmpe277.spring2021.homework.Detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import cmpe277.spring2021.homework.Network.Posts;

import cmpe277.spring2021.homework.Network.SummaryService;
import cmpe277.spring2021.homework.Network.SummaryView;
import cmpe277.spring2021.homework.R;
import cmpe277.spring2021.homework.databinding.FragmentDetailBinding;

public class Detailed extends Fragment {

    ImageView imageView;
    WebView webView;
    Switch toggleButton;
    String title;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cmpe277.spring2021.homework.databinding.FragmentDetailBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_detail, container, false);

        imageView = binding.imageView;

        webView = binding.webView;
        toggleButton = binding.switch1;
        Bundle bundle = getArguments();

        title = (String) bundle.get("title");

        String imageUrl = (String) bundle.get("imageUrl");
        final String url = (String) bundle.get("url");

        Picasso.with(getContext()).load(imageUrl).into(imageView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        toggleButton.setOnClickListener(view -> callRetrofit(url ,view));

        return binding.getRoot();
    }



        private void callRetrofit(String url , final View view){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://text-monkey-summarizer.p.rapidapi.com/nlp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            SummaryService postsService = retrofit.create(SummaryService.class);
            Posts post = new Posts();
        post.setUrl(url);



        Call<Posts> call = postsService.sendPosts(post);
        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {

                if(response.isSuccessful()) {
                    response.body().toString();
                    SummaryView summary = new SummaryView();
                    Bundle bundle = new Bundle();
                    bundle.putString("summary",response.body().getSummary());
                    bundle.putString("title",title);
                    summary.setArguments(bundle);
                    Navigation.findNavController(view).navigate(R.id.action_detailFragment_to_summaryFragment,bundle,null,null);

                }

            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_SHORT).show();
            }

        });

    }



}

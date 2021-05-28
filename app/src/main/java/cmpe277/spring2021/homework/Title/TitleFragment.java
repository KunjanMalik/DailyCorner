package cmpe277.spring2021.homework.Title;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cmpe277.spring2021.homework.Network.Adapter;
import cmpe277.spring2021.homework.Network.Articles;
import cmpe277.spring2021.homework.Network.NewsViewModel;
import cmpe277.spring2021.homework.R;
import cmpe277.spring2021.homework.databinding.FragmentTitleBinding;

public class TitleFragment extends Fragment {

    private FragmentTitleBinding binding;
    private EditText etQuery;
    private List<Articles> articles = new ArrayList<>();
    Adapter adapter;
    Bundle bundle;
    Articles article;
    NewsViewModel viewModel;
    String category;
    String query;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        bundle = getArguments();
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_title,container,false);

        final String country = getCountry();
        etQuery = binding.etQuery;

        category = (bundle!=null) ?bundle.getString("category"): "";

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            retreiveNews(query,category);


        }
    });

        viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
 binding.etQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = (etQuery!=null)?etQuery.getText().toString():"";
                if(!binding.etQuery.getText().toString().equals("")){
                    binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retreiveNews(query,category);
                        }
                    });
                    retreiveNews(query,category);
                }else{
                    binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retreiveNews(query,category);
                        }
                    });
                    retreiveNews(query,category);

                }
                //hideKeyboardFrom(getContext(),v);
            }


        });
       

        retreiveNews(query,category);

        return binding.getRoot();
    }
    public void retreiveNews(String query ,String category){

            viewModel.retreiveNews(query,category).observe(getViewLifecycleOwner(), newresponse -> {
            binding.swipeRefresh.setRefreshing(false);
            articles = newresponse;
            binding.progressBar.setVisibility(View.GONE);
            adapter = new Adapter(getContext(),articles);
            binding.recyclerView.setAdapter(adapter);

    });
    }
    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }


    
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}

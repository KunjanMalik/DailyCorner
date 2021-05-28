package cmpe277.spring2021.homework.Network;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import cmpe277.spring2021.homework.R;
import cmpe277.spring2021.homework.databinding.FragmentSummaryBinding;

public class SummaryView extends Fragment {
    TextView summary;
    ScrollView scrollView;
    LinearLayout ll;
    private FragmentSummaryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_summary,container,false);

        Bundle bundle = getArguments();


        summary = new TextView(getContext());
        summary.setTextSize(15);
        summary.setTextColor(Color.parseColor("#FFFFFF"));
        summary.setPadding(0,40,0,0);

        TextView title = new TextView(getContext());
        title.setTextSize(20);
        title.setPadding(0,0,0,100);
        title.setTextColor(Color.parseColor("#FFFFFF"));
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        final String text =(String) bundle.get("summary");
        String t = (String) bundle.get("title");
        title.setText(t);
        summary.setText(text);
        binding.ll.addView(title);
        binding.ll.addView(summary);
        return binding.getRoot();
    }

}
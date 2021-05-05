package com.example.bitcoinratemvvm;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import retrofit2.Response;

public class MainPageFragment extends Fragment {

    private final String[] arrayPicker = new String[]{"USD", "UAH", "EUR", "POL", "RUB", "FRA", "KWD"};
    private NumberPicker pickers;
    private TextView price, name;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PostViewModel postViewModel;
    private final Handler handler = new Handler();
    private PickerListener pickerListener;
    private Post modal;


    public MainPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickerListener = new PickerListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        price = view.findViewById(R.id.valute_price);
        name = view.findViewById(R.id.valute_name);
        pickers = view.findViewById(R.id.pickerId);

        postViewModel = new ViewModelProvider(this, new PostViewModelFactory(getActivity().getApplication())).get(PostViewModel.class);

        postViewModel.getCall("USD");

        postViewModel.myResponse.observe(this, new Observer<Response<Post>>() {
            @Override
            public void onChanged(Response<Post> postResponse) {
                if (postResponse.isSuccessful()) {
                    modal = postResponse.body();
                    price.setText(String.format("%.2f", modal.getPrice()));
                    name.setText(String.valueOf(modal.getName()));
                } else {
                    Toast.makeText(getContext(), "Response :: " + postResponse.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        pickers.setMinValue(0);
        pickers.setMaxValue(arrayPicker.length - 1);
        pickers.setDisplayedValues(arrayPicker);
        pickers.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pickers.setWrapSelectorWheel(true);

        pickers.setOnValueChangedListener(pickerListener);
        pickers.setOnScrollListener(pickerListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postViewModel.getCall(arrayPicker[pickers.getValue()]);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private class PickerListener implements NumberPicker.OnScrollListener, NumberPicker.OnValueChangeListener {
        private int scrollState = 0;

        @Override
        public void onScrollStateChange(NumberPicker numberPicker, int i) {
            this.scrollState = i;
            if (scrollState == SCROLL_STATE_IDLE) {
                numberPicker.postDelayed(new Runnable() {
                    public void run() {
                        postViewModel.getCall(arrayPicker[pickers.getValue()]);
                    }
                }, 500);//set time
            }
        }

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            if (scrollState == 0) {
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (newVal == pickers.getValue()) {
                            postViewModel.getCall(arrayPicker[pickers.getValue()]);
                        }
                    }
                }, 500);//set time
            }
        }
    }
}
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
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPageFragment extends Fragment {

    private final String[] arrayPicker = new String[]{"USD", "UAH", "EUR", "POL", "RUB", "FRA", "KWD"};
    private NumberPicker pickers;
    private TextView price, name;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PostViewModel postViewModel;
    private final Handler handler = new Handler();
    private PickerListener pickerListener;
    private Call<Post> call;



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


        PostRepository repository = new PostRepository();
        postViewModel = new ViewModelProvider(this,new PostViewModelFactory(getActivity().getApplication())).get(PostViewModel.class);


        pickers.setMinValue(0);
        pickers.setMaxValue(arrayPicker.length - 1);
        pickers.setDisplayedValues(arrayPicker);
        pickers.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pickers.setWrapSelectorWheel(true);

        pickers.setOnValueChangedListener(pickerListener);
        pickers.setOnScrollListener(pickerListener);


        //https://stackoverflow.com/questions/31003668/numberpicker-getvalue-on-scroll-state-idle-might-not-be-the-last-updated-valu
//        pickers.setOnScrollListener(new NumberPicker.OnScrollListener() {
//            @Override
//            public void onScrollStateChange(NumberPicker numberPicker, int i) {
//                if (i == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
//                    numberPicker.postDelayed(new Runnable() {
//                        public void run() {
//                            setValue2(arrayPicker[numberPicker.getValue()]);
//                            //Toast.makeText(getContext(), "Number=" + numberPicker.getValue(), Toast.LENGTH_SHORT).show();
//                        }
//                    }, 500);//set time
//                }
//            }
//        });
//
//        pickers.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        if (i1 == pickers.getValue()) {
//                            setValue2(arrayPicker[i1]);
//                            //Toast.makeText(getContext(), "Number=" + i1, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, 500);//set time
//            }
//        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setValue2(arrayPicker[pickers.getValue()]);
                //setValue(postViewModel.getCallValute(arrayPicker[pickers.getValue()]));
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        setValue2("USD");
    }

    private void setValue2(String valute){
        call = postViewModel.getCallValute(valute);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post modal = response.body();

                    price.setText(String.format("%.2f", modal.getPrice()));
                    name.setText(String.valueOf(modal.getName()));
                }else{
                    Toast.makeText(getContext(), "Request Error :: " +response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    //System.out.println("Request Error :: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to get data..", Toast.LENGTH_SHORT).show();
                System.out.println("Network Error :: " + t.getLocalizedMessage());
            }
        });
    }

    private void setValue(Call<Post> call) {
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post modal = response.body();

                    price.setText(String.format("%.2f", modal.getPrice()));
                    name.setText(String.valueOf(modal.getName()));
                }else{
                    Toast.makeText(getContext(), "Error: "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
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
                        setValue2(arrayPicker[numberPicker.getValue()]);
                        //Toast.makeText(getContext(), "Number=" + numberPicker.getValue(), Toast.LENGTH_SHORT).show();
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
                            setValue2(arrayPicker[newVal]);
                            //Toast.makeText(getContext(), "Number=" + i1, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 500);//set time
            }
        }
    }

    private void update(){

    }
}
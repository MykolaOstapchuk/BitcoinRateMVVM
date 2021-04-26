package com.example.bitcoinratemvvm;

import android.os.Bundle;
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

    public MainPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        pickers.setMinValue(0);
        pickers.setMaxValue(arrayPicker.length - 1);
        pickers.setDisplayedValues(arrayPicker);
        pickers.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pickers.setWrapSelectorWheel(true);

        pickers.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                setValue(arrayPicker[i1]);
            }
        });

        setValue("USD");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setValue(arrayPicker[pickers.getValue()]);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setValue(String current_valute) {
        Call<Post> call = postViewModel.getCurrentValute(current_valute);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post modal = response.body();

                    price.setText(String.format("%.2f", modal.getPrice()));
                    name.setText(String.valueOf(modal.getName()));
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.example.uas_search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.uas_search.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ProductAdapter productAdapter;
    private ApiService apiService;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.RVProduct.setLayoutManager(layoutManager);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dummyjson.com/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        binding.searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String query = binding.etSearch.getText().toString();
                performSearch(query);
                binding.etSearch.setText("");
            }
        });

    }

    private void performSearch(String query) {
        Call<ResponseData> call = apiService.searchItems(query);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful()) {
                    ResponseData responseData = response.body();
                    List<ProductItem> searchResults = responseData.getProducts();
                    selectionSortByRating(searchResults);

                    productAdapter = new ProductAdapter(searchResults);
                    binding.RVProduct.setAdapter(productAdapter);
                    System.out.println(searchResults.get(0));
                    System.out.println("success get data");
                } else {
                    System.out.println("Error unsuccess get data");
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                System.out.println("Error on failure");
            }
        });
    }

    private void selectionSortByRating(List<ProductItem> searchResults) {
        int n = searchResults.size();

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            double minValue = searchResults.get(i).getRating();

            for (int j = i + 1; j < n; j++) {
                double currentRating = searchResults.get(j).getRating();
                if (currentRating < minValue) {
                    minIndex = j;
                    minValue = currentRating;
                }
            }

            if (minIndex != i) {
                // Swap the items
                ProductItem temp = searchResults.get(i);
                searchResults.set(i, searchResults.get(minIndex));
                searchResults.set(minIndex, temp);
            }
        }
    }

}
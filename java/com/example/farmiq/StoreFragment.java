package com.example.farmiq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmiq.adapters.ProductAdapter;
import com.example.farmiq.models.Product;

import java.util.ArrayList;
import java.util.List;

public class StoreFragment extends Fragment {

    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);


        recyclerViewProducts = view.findViewById(R.id.recycler_view_products);
        recyclerViewProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));

        productList = new ArrayList<>();
        productList.add(new Product("Apple", "Fruit", 150.0, "Kashmir Organic Apple Orchard", R.drawable.apple));
        productList.add(new Product("Virgin Olive Oil", "Organic", 1175.0, "Katipalla 4th Block", R.drawable.olive_oil));
        productList.add(new Product("Kiwi", "Fruit", 699.0, "Kadri Temple Road, Kadri", R.drawable.kiwi));
        productList.add(new Product("Maize", "Crop", 40.0, "Davangere Harappana Halli", R.drawable.maize));
        productList.add(new Product("Wheat", "Cereal", 52, "Popular Flour Mills in Kengeri-Ullal Upanagar", R.drawable.wheat));
        productList.add(new Product("Garlic", "Vegetable", 199, "S V K Agencies mangalore", R.drawable.garlic));
        productList.add(new Product("Pure Honey", "Organic", 402, "South Kanara Bee Keepers Coop Society Sulia Branch", R.drawable.honey));
        // Add more products

        productAdapter = new ProductAdapter(getContext(), productList);
        recyclerViewProducts.setAdapter(productAdapter);

        // Add scroll listener to RecyclerView
        recyclerViewProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });



        return view;
    }


}
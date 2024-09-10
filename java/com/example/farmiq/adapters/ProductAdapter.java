package com.example.farmiq.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmiq.R;
import com.example.farmiq.BuyFragment;
import com.example.farmiq.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productName.setText(product.getName());
        holder.productType.setText(product.getType());
        holder.productPrice.setText(String.format("₹%.2f/kg", product.getPrice()));
        holder.productAddress.setText(product.getAddress());
        holder.productImage.setImageResource(product.getImageResId());

        holder.buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyFragment buyFragment = BuyFragment.newInstance(product);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, buyFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productType, productPrice, productAddress;
        Button buttonBuy;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.image_product);
            productName = itemView.findViewById(R.id.text_product_name);
            productType = itemView.findViewById(R.id.text_product_type);
            productPrice = itemView.findViewById(R.id.text_product_price);
            productAddress = itemView.findViewById(R.id.text_product_address);
            buttonBuy = itemView.findViewById(R.id.button_buy);
        }
    }
}

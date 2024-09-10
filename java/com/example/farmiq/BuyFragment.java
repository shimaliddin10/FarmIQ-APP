package com.example.farmiq;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.farmiq.models.Product;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class BuyFragment extends Fragment {

    private static final String ARG_PRODUCT = "product";
    private Product product;
    private int quantity = 1;
    private double totalPrice;
    private String deliveryMessage;

    public static BuyFragment newInstance(Product product) {
        BuyFragment fragment = new BuyFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = getArguments().getParcelable(ARG_PRODUCT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy, container, false);

        ImageView imageProduct = view.findViewById(R.id.image_product);
        TextView textProductName = view.findViewById(R.id.text_product_name);
        TextView textProductType = view.findViewById(R.id.text_product_type);
        TextView textProductPrice = view.findViewById(R.id.text_product_price);
        TextView textProductAddress = view.findViewById(R.id.text_product_address);
        TextView textQuantity = view.findViewById(R.id.text_quantity);
        TextView textTotalPrice = view.findViewById(R.id.text_total_price);
        TextView textDeliveryInfo = view.findViewById(R.id.text_delivery_info);
        Button buttonDecrement = view.findViewById(R.id.button_decrement);
        Button buttonIncrement = view.findViewById(R.id.button_increment);
        Button buttonBuy = view.findViewById(R.id.button_buy);

        textProductName.setText(product.getName());
        textProductType.setText(product.getType());
        textProductPrice.setText(String.format("₹%.2f/kg", product.getPrice()));
        textProductAddress.setText(product.getAddress());

        // Load image using Glide or any other image loading library
        Glide.with(this).load(product.getImageResId()).into(imageProduct);

        // Set initial total price
        totalPrice = product.getPrice();
        textTotalPrice.setText(String.format("Total: ₹%.2f", totalPrice));

        buttonDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    textQuantity.setText(String.valueOf(quantity));
                    updateTotalPrice();
                }
            }
        });

        buttonIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                textQuantity.setText(String.valueOf(quantity));
                updateTotalPrice();
            }
        });

        // Generate random delivery info
        int deliveryDays = new Random().nextInt(3) + 2;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, deliveryDays);
        String deliveryDate = new SimpleDateFormat("E, MMM d", Locale.getDefault()).format(calendar.getTime());

        deliveryMessage = String.format("Will Be Delivered within %d days (%s)", deliveryDays, deliveryDate);
        textDeliveryInfo.setText(deliveryMessage);

        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });

        return view;
    }

    private void updateTotalPrice() {
        totalPrice = product.getPrice() * quantity;
        TextView textTotalPrice = getView().findViewById(R.id.text_total_price);
        textTotalPrice.setText(String.format("Total: ₹%.2f", totalPrice));
    }

    private void showConfirmationDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirmation, null);
        EditText editName = dialogView.findViewById(R.id.edit_name);
        EditText editNumber = dialogView.findViewById(R.id.edit_number);
        EditText editAddress = dialogView.findViewById(R.id.edit_address);

        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Purchase")
                .setView(dialogView)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    String name = editName.getText().toString().trim();
                    String number = editNumber.getText().toString().trim();
                    String address = editAddress.getText().toString().trim();

                    if (name.isEmpty() || number.isEmpty() || address.isEmpty()) {
                        Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        sendWhatsAppMessage(name, number, address);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void sendWhatsAppMessage(String name, String number, String address) {
        String message = String.format("Product: %s\nType: %s\nPrice: ₹%.2f/kg\nQuantity: %d\nTotal: ₹%.2f\nAddress: %s\n%s\n\nCustomer Name: %s\nCustomer Number: %s\nCustomer Address: %s",
                product.getName(), product.getType(), product.getPrice(), quantity, totalPrice, product.getAddress(), deliveryMessage, name, number, address);

        String phoneNumberWithCountryCode = "+91" + number; // Change country code as needed

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + phoneNumberWithCountryCode + "&text=" + Uri.encode(message)));
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getContext(), "WhatsApp is not installed", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.farmiq;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SellFragment extends Fragment {

    private EditText editName, editType, editPrice, editAddress, editSellerName, editSellerPhone, editSellerEmail;
    private Button submitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell, container, false);

        editName = view.findViewById(R.id.edit_name);
        editType = view.findViewById(R.id.edit_type);
        editPrice = view.findViewById(R.id.edit_price);
        editAddress = view.findViewById(R.id.edit_address);
        editSellerName = view.findViewById(R.id.edit_seller_name);
        editSellerPhone = view.findViewById(R.id.edit_seller_phone);
        editSellerEmail = view.findViewById(R.id.edit_seller_email);
        submitButton = view.findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitProduct();
            }
        });

        return view;
    }

    private void submitProduct() {
        String name = editName.getText().toString();
        String type = editType.getText().toString();
        String price = editPrice.getText().toString();
        String address = editAddress.getText().toString();
        String sellerName = editSellerName.getText().toString();
        String sellerPhone = editSellerPhone.getText().toString();
        String sellerEmail = editSellerEmail.getText().toString();

        // Validate input
        if (name.isEmpty() || type.isEmpty() || price.isEmpty() || address.isEmpty() || sellerName.isEmpty() || sellerPhone.isEmpty() || sellerEmail.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Construct the URL for Google Form submission
        String googleFormUrl = "https://docs.google.com/forms/d/e/1FAIpQLSeqCJ9VLRB7YhHpWZOGPs6sBUfPfTrDunaD5Lu9sccve6ueAA/viewform";
        googleFormUrl += "?usp=pp_url";
        googleFormUrl += "&entry.2005620554=" + Uri.encode(name); // Product Name
        googleFormUrl += "&entry.1045781291=" + Uri.encode(type); // Product Type
        googleFormUrl += "&entry.1065046570=" + Uri.encode(price); // Product Price
        googleFormUrl += "&entry.1166974658=" + Uri.encode(address); // Product Address
        googleFormUrl += "&entry.868291784=" + Uri.encode(sellerName); // Seller Name
        googleFormUrl += "&entry.527148146=" + Uri.encode(sellerPhone); // Seller Phone
        googleFormUrl += "&entry.1230997339=" + Uri.encode(sellerEmail); // Seller Email

        // Open Google Form in the browser
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleFormUrl));
        startActivity(intent);

        Toast.makeText(getContext(), "Redirecting to Google Form.", Toast.LENGTH_SHORT).show();

        // Navigate back to the store or another fragment
        getFragmentManager().popBackStack();
    }
}

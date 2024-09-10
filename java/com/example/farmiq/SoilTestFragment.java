package com.example.farmiq;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.farmiq.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoilTestFragment extends Fragment {

    private Spinner labSpinner;
    private Spinner timeSpinner;
    private Spinner testTypeSpinner;
    private EditText addressInput;
    private EditText emailInput;
    private EditText phoneInput;
    private TextView originalCostTextView;
    private TextView discountedCostTextView;
    private Button bookButton;

    // Cost variables
    private int originalCost = 0;
    private int discountedCost = 0;

    // Mapping of costs
    private Map<String, Map<String, Integer>> costMapping;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soil_test, container, false);

        labSpinner = view.findViewById(R.id.lab_spinner);
        timeSpinner = view.findViewById(R.id.time_spinner);
        testTypeSpinner = view.findViewById(R.id.test_type_spinner);
        addressInput = view.findViewById(R.id.address_input);
        emailInput = view.findViewById(R.id.email_input);
        phoneInput = view.findViewById(R.id.phone_input);
        originalCostTextView = view.findViewById(R.id.original_cost);
        discountedCostTextView = view.findViewById(R.id.discounted_cost);
        bookButton = view.findViewById(R.id.book_button);

        setupCostMapping();
        setupLabSpinner();
        setupTimeSpinner();
        setupTestTypeSpinner();

        // Setup listeners for spinners
        labSpinner.setOnItemSelectedListener(new SpinnerListener());
        testTypeSpinner.setOnItemSelectedListener(new SpinnerListener());

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });

        return view;
    }

    private void setupCostMapping() {
        costMapping = new HashMap<>();

        // Lab: SOIL HEALTH CENTER, MANGALORE
        Map<String, Integer> lab1Costs = new HashMap<>();
        lab1Costs.put("Primary Nutrition Test", 1700);
        lab1Costs.put("Secondary Nutrition Test", 2500);
        lab1Costs.put("Micro Nutrition Test", 1200);
        costMapping.put("SOIL HEALTH CENTER, MANGALORE", lab1Costs);

        // Lab: Shripada Raithamithra Services Sullia
        Map<String, Integer> lab2Costs = new HashMap<>();
        lab2Costs.put("Primary Nutrition Test", 1600);
        lab2Costs.put("Secondary Nutrition Test", 2800);
        lab2Costs.put("Micro Nutrition Test", 1000);
        costMapping.put("Shripada Raithamithra Services Sullia", lab2Costs);

        // Lab: Alankar PACS Ltd Soil testing lab
        Map<String, Integer> lab3Costs = new HashMap<>();
        lab3Costs.put("Primary Nutrition Test", 1550);
        lab3Costs.put("Secondary Nutrition Test", 2750);
        lab3Costs.put("Micro Nutrition Test", 1150);
        costMapping.put("Alankar PACS Ltd Soil testing lab", lab3Costs);

        // Lab: Soil and water testing laboratory, KVK, Mangaluru
        Map<String, Integer> lab4Costs = new HashMap<>();
        lab4Costs.put("Primary Nutrition Test", 2580);
        lab4Costs.put("Secondary Nutrition Test", 3580);
        lab4Costs.put("Micro Nutrition Test", 1900);
        costMapping.put("Soil and water testing laboratory, KVK, Mangaluru", lab4Costs);
    }

    private void setupLabSpinner() {
        List<String> labs = new ArrayList<>();
        labs.add("SOIL HEALTH CENTER, MANGALORE");
        labs.add("Shripada Raithamithra Services Sullia");
        labs.add("Alankar PACS Ltd Soil testing lab");
        labs.add("Soil and water testing laboratory, KVK, Mangaluru");

        ArrayAdapter<String> labAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, labs);
        labAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        labSpinner.setAdapter(labAdapter);
    }

    private void setupTimeSpinner() {
        List<String> times = new ArrayList<>();
        for (int i = 9; i <= 16; i++) {
            times.add(i + ":00");
        }

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, times);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);
    }

    private void setupTestTypeSpinner() {
        List<String> testTypes = new ArrayList<>();
        testTypes.add("Primary Nutrition Test");
        testTypes.add("Secondary Nutrition Test");
        testTypes.add("Micro Nutrition Test");

        ArrayAdapter<String> testTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, testTypes);
        testTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        testTypeSpinner.setAdapter(testTypeAdapter);
    }

    private void updateCostViews() {
        String selectedLab = labSpinner.getSelectedItem().toString();
        String selectedTestType = testTypeSpinner.getSelectedItem().toString();

        if (costMapping.containsKey(selectedLab) && costMapping.get(selectedLab).containsKey(selectedTestType)) {
            originalCost = costMapping.get(selectedLab).get(selectedTestType);
            discountedCost = originalCost - (originalCost * 20 / 100); // 20% discount
        } else {
            originalCost = 0;
            discountedCost = 0;
        }

        String originalCostStr = "₹" + originalCost;
        String discountedCostStr = "₹" + discountedCost;

        SpannableString spannableString = new SpannableString(originalCostStr + " " + discountedCostStr);
        spannableString.setSpan(new StrikethroughSpan(), 0, originalCostStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        originalCostTextView.setText(spannableString);
        discountedCostTextView.setText("Final Price: " + discountedCostStr);
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirm Your Booking?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendSoilTestFormByWhatsApp();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void sendSoilTestFormByWhatsApp() {
        String recipientPhone = phoneInput.getText().toString(); // Use user's phone number
        String message = "Dear Customer,\n\nThank you for booking your soil test with us.\n\nDetails:\n\n"
                + "Lab: " + labSpinner.getSelectedItem().toString() + "\n"
                + "Time: " + timeSpinner.getSelectedItem().toString() + "\n"
                + "Test Type: " + testTypeSpinner.getSelectedItem().toString() + "\n"
                + "Address: " + addressInput.getText().toString() + "\n"
                + "Email: " + emailInput.getText().toString() + "\n"
                + "Phone: " + phoneInput.getText().toString() + "\n"
                + "Final Price: " + discountedCostTextView.getText().toString() + "\n\n"
                + "Thank you for using FarmIQ.";

        try {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + recipientPhone + "&text=" + Uri.encode(message);
            sendIntent.setData(Uri.parse(url));
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "WhatsApp not installed. Please install WhatsApp and try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private class SpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            updateCostViews();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing
        }
    }
}

package com.example.farmiq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.farmiq.R;

import java.util.HashMap;
import java.util.Map;

public class CropRecommendationFragment extends Fragment {

    private TextView textViewRecommendations;
    private Spinner spinnerDistrict;
    private Switch switchState;

    private Map<String, String> cropRecommendations;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crop_recommendation, container, false);

        textViewRecommendations = view.findViewById(R.id.textViewRecommendations);
        spinnerDistrict = view.findViewById(R.id.spinnerDistrict);
        switchState = view.findViewById(R.id.switchState);

        initializeCropRecommendations();
        setupDistrictSpinner();

        switchState.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                // Currently only Karnataka is supported
                switchState.setChecked(true);
            }
        });

        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDistrict = parent.getItemAtPosition(position).toString();
                displayRecommendations(selectedDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        return view;
    }

    private void initializeCropRecommendations() {
        cropRecommendations = new HashMap<>();
        cropRecommendations.put("Dakshina Kannada", "Recommended crops:\n- Rice\n- Coconut\n- Arecanut");
        cropRecommendations.put("Bangalore", "Recommended crops:\n- Maize\n- Ragi\n- Vegetables");
        cropRecommendations.put("Mysore", "Recommended crops:\n- Sugarcane\n- Cotton\n- Pulses");
        cropRecommendations.put("Belgaum", "Recommended crops:\n- Soybean\n- Groundnut\n- Cotton");
        cropRecommendations.put("Bidar", "Recommended crops:\n- Jowar\n-Bengal gram\n- Green gram");
        cropRecommendations.put("Chikmagaluru", "Recommended crops:\n- Ragi\n- Jowar");
        cropRecommendations.put("Davanagere", "Recommended crops:\n- Maize\n- Sugarcane\n- Rice");
        cropRecommendations.put("Dharwad", "Recommended crops:\n- Cashew\n- Wheat\n- Bengal gram");
        cropRecommendations.put("Hassan", "Recommended crops:\n- Black Pepper\n- Coffee\n- Potatoes");
        cropRecommendations.put("Haveri", "Recommended crops:\n- Chilli\n- Paddy\n- Peanuts");

        cropRecommendations.put("Kolar", "Recommended crops:\n- Finger millet\n-Groundnut\n-Pulses");
        cropRecommendations.put("Mandya", "Recommended crops:\n-Ragi\n-Rice\n-Sugarcane");
        cropRecommendations.put("Raichur", "Recommended crops:\n-Sunflower\n- Cotton\n-Maize");
        cropRecommendations.put("Shivamogga", "Recommended crops:\n-Arecanut\n-Oilseeds\n-Ginger");
        cropRecommendations.put("Tumakuru ", "Recommended crops:\n-Coconut \n- Pomegranate\n-Banana");
        cropRecommendations.put("Bijapur", "Recommended crops: \n- Sunflower \n-Saffron \n- Jowar");
        cropRecommendations.put("Udupi", "Recommended crops:\n-Rubber\n-Sapota\n- Cucumber ");
        cropRecommendations.put("Yadgiri", "Recommended crops:\n- Redgram\n- Millet\n-Grondnut");
        cropRecommendations.put("Vijayanagara", "Recommended crops:\n-Ragi\n- wheat\n-Betel");
        cropRecommendations.put("Koppal", "Recommended crops:\n- Sesame\n- Niger seeds\n- Bajra");
    }

    private void setupDistrictSpinner() {
        String[] districts = {"Dakshina Kannada", "Bangalore", "Mysore", "Belgaum", "Bidar","Chikmagaluru", "Davanagere", "Dharwad", "Hassan", "Haveri", "Kolar", "Mandya", "Raichur", "Shivamogga", "Tumakuru","Bijapur", "Udupi", "Yadgiri", "Vijayanagara","Koppal"};        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, districts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(adapter);
    }

    private void displayRecommendations(String district) {
        String recommendations = cropRecommendations.get(district);
        if (recommendations != null) {
            textViewRecommendations.setText(recommendations);
        } else {
            textViewRecommendations.setText("No recommendations available for this district.");
        }
    }
}

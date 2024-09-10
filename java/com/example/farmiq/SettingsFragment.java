package com.example.farmiq;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private ImageView ivUserProfile;
    private TextView tvFullName, tvDateOfBirth, tvMobileNumber, tvEmail, tvAddress, tvDeveloperInfo, tvHelpMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ivUserProfile = view.findViewById(R.id.iv_user_profile);
        tvFullName = view.findViewById(R.id.tv_full_name);
        tvDateOfBirth = view.findViewById(R.id.tv_date_of_birth);
        tvMobileNumber = view.findViewById(R.id.tv_mobile_number);
        tvEmail = view.findViewById(R.id.tv_email);
        tvAddress = view.findViewById(R.id.tv_address);
        tvDeveloperInfo = view.findViewById(R.id.tv_developer_info);
        tvHelpMessage = view.findViewById(R.id.tv_help_message);

        setHasOptionsMenu(true);  // Enable options menu in the fragment

        // Set user details (randomly generated for demonstration)
        setUserDetails();
        tvDeveloperInfo.setText("Developed by S.S.Gang");

        // Set the help message
        setHelpMessage();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.settings_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_contact_us) {
            showContactUsDialog();
            return true;
        } else if (itemId == R.id.menu_about_us) {
            showAboutUsDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void setUserDetails() {
        // Randomly generated user details (replace with actual data logic)
        tvFullName.setText("Full Name: Rajesh Shetty");
        tvDateOfBirth.setText("Date of Birth: 15/08/1985");
        tvMobileNumber.setText("Mobile Number: +91 9876543210");
        tvEmail.setText("Email: rajesh.shetty@example.com");
        tvAddress.setText("Address: 123, MG Road, Mangaluru, Karnataka, India");
    }

    private void setHelpMessage() {
        String helpText = "If you want more information or help in increasing your farm yield or suggestions, you can visit our website.";
        String websiteUrl = "https://669df299ec4953b85cb32f82--comforting-kashata-1319d4.netlify.app/ ";

        SpannableString spannableString = new SpannableString(helpText + " " + websiteUrl);
        spannableString.setSpan(new UnderlineSpan(), helpText.length() + 1, spannableString.length(), 0);
        Linkify.addLinks(spannableString, Linkify.WEB_URLS);

        tvHelpMessage.setText(spannableString);
        tvHelpMessage.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void showContactUsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_contact_us, null);
        builder.setView(dialogView);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void showAboutUsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_about_us, null);
        builder.setView(dialogView);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}

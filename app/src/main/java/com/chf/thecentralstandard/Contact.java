package com.chf.thecentralstandard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import IntentUtils.IntentUtils;

public class Contact extends Fragment {

    private EditText firstName;
    private EditText lastName;
    private EditText emailAddress;
    private Button requestedDate;
    private EditText howCanIHelp;
    private Button send;
    private String fullName;
    private String requestedTour;
    private String requestedDateString;
    private String emailBody;
    private String[] requestedTourOptions;


    public static Fragment newInstance(Context context) {
        Contact f = new Contact();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.contact, null);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstName = (EditText) view.findViewById(R.id.edittext_first_name);
        lastName = (EditText) view.findViewById(R.id.edittext_last_name);
        emailAddress = (EditText) view.findViewById(R.id.edittext_email_address);
        requestedDate = (Button) view.findViewById(R.id.button_requested_date);
        howCanIHelp = (EditText) view.findViewById(R.id.edittext_how_can_i_help);
        send = (Button) view.findViewById(R.id.button_send_contact);
        requestedTourOptions = getResources().getStringArray(R.array.requested_tours_options);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_requested_tour);

        requestedTour = "no tour";

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.requested_tours_options, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    requestedTour = parent.getItemAtPosition(position).toString();
                } else {
                    requestedTour = "no tour requested";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = firstName.getText().toString() + " " + lastName.getText().toString();
                requestedDateString = ((MainActivity) getActivity()).getDate();
                emailBody = getResources().getString(R.string.contact_email_message, requestedTour, requestedDateString,
                        emailAddress.getText().toString(), howCanIHelp.getText().toString(), fullName);

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"reva.entringer@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Request by " + fullName);
                emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);
                emailIntent.setType("message/rfc822");

                if (IntentUtils.isIntentSupported(getActivity().getApplicationContext(), emailIntent)) {
                    startActivity(emailIntent);
                } else {
                    Toast toast = Toast.makeText(getActivity().getBaseContext(), "No Email Client Found", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

    }

}


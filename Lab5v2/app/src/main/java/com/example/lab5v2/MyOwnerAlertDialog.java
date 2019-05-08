package com.example.lab5v2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MyOwnerAlertDialog extends DialogFragment {

    private EditText ownerEditText;
    private OwnerAlertDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.owner_alert_dialog, null);

        this.ownerEditText = view.findViewById(R.id.username_edit_text);

        builder.setView(view)
        .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
        .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String owner = ownerEditText.getText().toString();
                listener.passOwner(owner);
            }
        });

        return builder.create();
    } // onCreateDialog

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OwnerAlertDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement OwnerAlertDialogListener");
        }
    }

    public interface OwnerAlertDialogListener {
        public void passOwner(String owner);
    }


} // class

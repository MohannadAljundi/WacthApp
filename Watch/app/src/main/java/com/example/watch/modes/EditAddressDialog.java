package com.example.watch.modes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.watch.R;

public class EditAddressDialog extends AppCompatDialogFragment {

    private EditText address;
    private EditAddressDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_address,null);

        builder.setView(view)

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String Address = address.getText().toString();
                        listener.TransferAddressText(Address);
                    }
                });

        address = view.findViewById(R.id.address_edit);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (EditAddressDialogListener)context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() +
                    "Must Implement EditAddressDialogListener");
        }
    }

    public interface EditAddressDialogListener{
        void TransferAddressText(String address);
    }


}

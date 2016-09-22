package com.smartlab.drivetrain.update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smartlab.drivetrain.license.R;

public class UpdateDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout linearLayout= (LinearLayout) inflater.inflate(R.layout.updatedialog,null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.show();
        builder.setView(linearLayout);
        TextView textView1= (TextView) linearLayout.findViewById(R.id.updatetitle);
        TextView textView2= (TextView) linearLayout.findViewById(R.id.updatemessage);
        Button button1= (Button) linearLayout.findViewById(R.id.updatequeding);
        Button button2= (Button) linearLayout.findViewById(R.id.updatequxiao);
        textView1.setText(R.string.newUpdateAvailable);
        textView2.setText(getArguments().getString(Check.APK_UPDATE_CONTENT));
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDownload();
               dismiss();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle(R.string.newUpdateAvailable);
//        builder.setMessage(getArguments().getString(Check.APK_UPDATE_CONTENT))
//                .setPositiveButton(R.string.dialogPositiveButton, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // FIRE ZE MISSILES!
//
//                        dismiss();
//                    }
//                })
//                .setNegativeButton(R.string.dialogNegativeButton, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User cancelled the dialog
//                        goToDownload();
//                        dismiss();
//                    }
//                });
        // Create the AlertDialog object and return it

        return builder.create();
    }


    private void goToDownload() {
    	Intent intent=new Intent(getActivity().getApplicationContext(),DownloadService.class);
    	intent.putExtra(Check.APK_DOWNLOAD_URL, getArguments().getString(Check.APK_DOWNLOAD_URL));
        Log.e("Apk_DOWNLOAD_URL", getArguments().getString(Check.APK_DOWNLOAD_URL));
    	getActivity().startService(intent);
    }
}

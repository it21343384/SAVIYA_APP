package com.example.saviya;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BankVH extends RecyclerView.ViewHolder {

    public TextView txt_bname,txt_hname,txt_accnumber,txt_branchname,txt_option;
    public BankVH(@NonNull View itemView) {
        super(itemView);
        txt_bname=itemView.findViewById(R.id.txt_bname);
        txt_hname=itemView.findViewById(R.id.txt_hname);
        txt_accnumber=itemView.findViewById(R.id.txt_accnumber);
        txt_branchname=itemView.findViewById(R.id.txt_branchname);
        txt_option=itemView.findViewById(R.id.txt_option);
    }
}

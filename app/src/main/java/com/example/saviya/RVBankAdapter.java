package com.example.saviya;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class RVBankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   private Context context;

   ArrayList<Bank> list = new ArrayList<>();
   public RVBankAdapter(Context ctx)
   {
       this.context=ctx;
   }
   public void setItms(ArrayList<Bank> bnk){
       list.addAll(bnk);
   }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bank,parent,false);
        return  new BankVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BankVH vh =(BankVH) holder;
        Bank bnk=list.get(position);

       vh.txt_bname.setText(bnk.getBankname());
        vh.txt_hname.setText(bnk.getHoldername());
        vh.txt_accnumber.setText(bnk.getAccnumber());
        vh.txt_branchname.setText(bnk.getBankBranch());
        vh.txt_option.setOnClickListener(v->{

            PopupMenu popupMenu = new PopupMenu(context,vh.txt_option);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item->
            {
                switch(item.getItemId())
            {
                case R.id.menu_edit:
                    Intent intent = new Intent(context,MainActivity.class);
                    intent.putExtra("EDIT",bnk);
                    context.startActivity(intent);
                    break;
                case R.id.menu_remove:
                    DAOBAnk dao = new DAOBAnk();
                    dao.remove(bnk.getKey()).addOnSuccessListener(suc->
                    {
                Toast.makeText(context,"Bank Details Succesfuly Delete", Toast.LENGTH_SHORT).show();
                notifyItemRemoved(position);
                 }).addOnFailureListener(er->{
                Toast.makeText(context," "+er.getMessage(), Toast.LENGTH_SHORT).show();
            });
            }
            return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

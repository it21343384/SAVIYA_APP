package com.example.saviya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RVBank extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
   // RVBankAdapter adapter;
    //boolean isLoading =false;

    DAOBAnk dao;
    FirebaseRecyclerAdapter adapter1;

            String key=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rvbank);
        swipeRefreshLayout=findViewById(R.id.swip);
        recyclerView=findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
     //   adapter=new RVBankAdapter(this);
       // recyclerView.setAdapter(adapter);
        dao = new DAOBAnk();
        FirebaseRecyclerOptions<Bank> option =
                new FirebaseRecyclerOptions.Builder<Bank>()
                        .setQuery(dao.get(), new SnapshotParser<Bank>() {
                            @NonNull
                            @Override
                            public Bank parseSnapshot(@NonNull DataSnapshot snapshot) {
                                Bank bnk = snapshot.getValue(Bank.class);
                                bnk.setKey(snapshot.getKey());
                                return bnk;
                            }
                        }).build();
        adapter1 = new FirebaseRecyclerAdapter(option) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull Object o) {

                BankVH vh =(BankVH) holder;
                Bank bnk=(Bank) o;

                vh.txt_bname.setText(bnk.getBankname());
                vh.txt_hname.setText(bnk.getHoldername());
                vh.txt_accnumber.setText(bnk.getAccnumber());
                vh.txt_branchname.setText(bnk.getBankBranch());
                vh.txt_option.setOnClickListener(v->{

                    PopupMenu popupMenu = new PopupMenu(RVBank.this,vh.txt_option);
                    popupMenu.inflate(R.menu.option_menu);
                    popupMenu.setOnMenuItemClickListener(item->
                    {
                        switch(item.getItemId())
                        {
                            case R.id.menu_edit:
                                Intent i = new Intent(RVBank.this,MainActivity.class);
                                i.putExtra("EDIT",bnk);
                                startActivity(i);
                                break;
                            case R.id.menu_remove:
                                DAOBAnk dao = new DAOBAnk();
                                dao.remove(bnk.getKey()).addOnSuccessListener(suc->
                                {
                                    Toast.makeText(RVBank.this,"Bank Details Succesfuly Delete", Toast.LENGTH_SHORT).show();

                                }).addOnFailureListener(er->{
                                    Toast.makeText(RVBank.this," "+er.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + item.getItemId());
                        }
                        return false;
                    });
                    popupMenu.show();
                });
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(RVBank.this).inflate(R.layout.layout_bank,parent,false);
                return  new BankVH(view);
            }

            @Override
            public void onDataChanged() {
                Toast.makeText(RVBank.this,"Data Changed",Toast.LENGTH_SHORT).show();
            }
        };
        recyclerView.setAdapter(adapter1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter1.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter1.startListening();
    }
    // loadData();
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//               LinearLayoutManager linearLayoutManager =(LinearLayoutManager)  recyclerView.getLayoutManager();
//               int totalItem = linearLayoutManager.getItemCount();
//               int lastVisible= linearLayoutManager.findLastCompletelyVisibleItemPosition();
//               if(totalItem<lastVisible+3)
//               {
//                   if(!isLoading){
//                       isLoading=true;
//                       loadData();
//                   }
//
//               }
//            }
//        });
//    }
//
//    private void loadData() {
//
//        swipeRefreshLayout.setRefreshing(true);
//        dao.get(key).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ArrayList<Bank> bnks= new ArrayList<>();
//                for(DataSnapshot data:snapshot.getChildren()){
//                    Bank bnk=data.getValue(Bank.class);
//                    bnk.setKey(data.getKey());
//                    bnks.add(bnk);
//                    key=data.getKey();
//                }
//                adapter.setItms(bnks);
//                adapter.notifyDataSetChanged();
//                isLoading=false;
//                swipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });

}
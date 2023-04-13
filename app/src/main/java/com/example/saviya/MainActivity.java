package com.example.saviya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          final   EditText edit_bankname = findViewById(R.id.edit_bankname);
          final    EditText edit_holdername = findViewById(R.id.edit_holdername);
          final   EditText edit_accnumber = findViewById(R.id.edit_accnumber);
          final   EditText edit_BankBranch = findViewById(R.id.edit_BankBranch);
          Button btn = findViewById(R.id.btn_submit);

        Button btn_open =findViewById(R.id.btn_open);
        btn_open.setOnClickListener(v->
        {
            Intent intent= new Intent(MainActivity.this,RVBank.class);
            startActivity(intent);
        });

        DAOBAnk dao = new DAOBAnk();
        Bank bnk_edit=(Bank)getIntent().getSerializableExtra("EDIT");
        if(bnk_edit !=null){
            btn.setText("UPADTE");
            edit_bankname.setText(bnk_edit.getBankname());
            edit_holdername.setText(bnk_edit.getHoldername());
            edit_accnumber.setText(bnk_edit.getAccnumber());
            edit_BankBranch.setText(bnk_edit.getBankBranch());
            btn_open.setVisibility(View.GONE);
        }else{
            btn.setText("SUBMIT");
            btn_open.setVisibility(View.VISIBLE);

        }
        btn.setOnClickListener(v->{
           // Insert Bank Details
            Bank bnk= new Bank(edit_bankname.getText().toString(),edit_holdername.getText().toString(),edit_accnumber.getText().toString(),edit_BankBranch.getText().toString());
            if(bnk_edit==null) {
                dao.add(bnk).addOnSuccessListener(suc -> {
                    Toast.makeText(this, "Bank Details Succesfuly inserted", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er -> {
                    Toast.makeText(this, " " + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
             else{
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("bankname",edit_bankname.getText().toString());
                hashMap.put("holdername",edit_holdername.getText().toString());
                hashMap.put("accnumber",edit_accnumber.getText().toString());
                hashMap.put("BankBranch",edit_BankBranch.getText().toString());
                dao.update(bnk_edit.getKey(),hashMap).addOnSuccessListener(suc->{
                   Toast.makeText(this,"Bank Details Succesfuly update", Toast.LENGTH_SHORT).show();
                   finish();
               }).addOnFailureListener(er->{
                    Toast.makeText(this," "+er.getMessage(), Toast.LENGTH_SHORT).show();
                });

            }
            //Update
//


//Delete method
//


        });


    }
}
package com.example.saviya;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DAOBAnk {

    private DatabaseReference databaseReference;
    public DAOBAnk(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Bank.class.getSimpleName());
    }
    public Task<Void> add(Bank bnk){

        return databaseReference.push().setValue(bnk);
    }
    public Task<Void> update (String Key, HashMap<String , Object > hashMap){
        return databaseReference.child(Key).updateChildren(hashMap);
    }
    public Task<Void> remove(String key){

        return databaseReference.child(key).removeValue();
    }
    public Query get(String key){

        if(key==null) {
            return databaseReference.orderByKey().limitToFirst(8);
        }
        return databaseReference.orderByKey().startAfter(key).limitToFirst(8);
    }

    public Query get()
    {
        return  databaseReference;
    }

}

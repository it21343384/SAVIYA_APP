package com.example.saviya;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Bank  implements Serializable {


    @Exclude
    private String key;
    private String bankname;
    private String holdername;
    private String accnumber;
    private String BankBranch;
    public Bank(){};
    public Bank(String bankname, String holdername, String accnumber, String bankBranch) {
        this.bankname = bankname;
        this.holdername = holdername;
        this.accnumber = accnumber;
        BankBranch = bankBranch;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getHoldername() {
        return holdername;
    }

    public void setHoldername(String holdername) {
        this.holdername = holdername;
    }

    public String getAccnumber() {
        return accnumber;
    }

    public void setAccnumber(String accnumber) {
        this.accnumber = accnumber;
    }

    public String getBankBranch() {
        return BankBranch;
    }

    public void setBankBranch(String bankBranch) {
        BankBranch = bankBranch;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}

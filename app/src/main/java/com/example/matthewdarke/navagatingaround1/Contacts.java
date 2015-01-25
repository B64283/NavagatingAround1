package com.example.matthewdarke.navagatingaround1;

import java.io.Serializable;

/**
 * Created by matthewdarke on 1/23/15.
 */
public class Contacts implements Serializable {

    public static final long serialVersionUID = 7877777587982937458L;

    public static Contacts newInstance(String _name, String _address, String _number) {

        Contacts contacts = new Contacts();
        contacts.setmName(_name);
        contacts.setmAddress(_address);
        contacts.setmNumber(_number);
        return contacts;
    }

    private String mName;
    private String mAddress;
    private String mNumber;

    public Contacts() {


        mName = "";
        mAddress = "";
        mNumber = "";

    }

    private Contacts(String _name, String _address, String _number) {

        mName = _name;
        mAddress = _address;
        mNumber = _number;


    }


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    @Override
    public String toString() {
        return mName;

    }

}

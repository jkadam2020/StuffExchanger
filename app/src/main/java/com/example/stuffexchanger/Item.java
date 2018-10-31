package com.example.stuffexchanger;


import android.util.Log;

/**
 * Created by jitu on 4/10/2017.
 */

public class Item {

        // private variables
        int id;
        int userid;
        String itemname;
        String username;
        byte[] image;
        double price;
        long contact;
        String description;
        String pdate;
        String email;
        double rating;

        // Empty constructor
        public Item() {

        }

        // constructor
        public Item(int userid, String itemname,String username, double price, long contact,byte[] image,String description,String d,String email) {
            this.userid=userid;
            this.itemname = itemname;
            this.username = username;
            this.image = image;
            this.price=price;
            this.contact=contact;
            this.description=description;
            this.pdate=d;
            this.email=email;
            this.rating=0.0;
        }
        public Item(int keyId) {
            this.id = keyId;

        }
        // getting ID
        public int getID() {
            return this.id;
        }
        // setting id
        public void setID(int keyId) {
            this.id = keyId;
        }
        // getting userID
        public int getUserID() {
        return this.userid;
    }
        // setting userid
        public void setUserID(int userid) {
        this.userid = userid;
    }

        public String getUsername() {
        return this.username;
    }

        public void setUsername(String uname) {
        this.username = uname;
    }

        // getting name
        public String getItemname() {
            return this.itemname;
        }

        // setting name
        public void setItemname(String name) {
            this.itemname = name;
        }

        public double getPrice() {
            return this.price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public byte[] getImage() {
        return this.image;
    }

        public void setImage(byte[] image) {
        this.image = image;
    }

        public long getContact() {
        return this.contact;
    }

        public void setContact(long contact) {
            this.contact = contact;
        }

        public String getDescription() {
        return this.description;
    }

        public void setDescription(String description) {
        this.description=description;
        }

        public String getPdate() {
        return this.pdate;
    }

        public void setPdate(String date) {
            this.pdate=date;
        }

        public String getEmail() {

            Log.d("getEmail():", this.email);
            return this.email;
    }

        public void setEmail(String email) {
        this.email = email;
    }

        public double getRating() {
            return rating;
        }
        public void setRating(double rating) {
            this.rating = rating;
        }


    @Override
    public String toString() {
        return "Item [id=" + id + ", UserID=" + userid +", User Name=" + username +", Item Name=" + itemname +", Price=" + price + ", Email=" + email +", Rating=" + rating +", Contact=" + contact + "]";
    }


}

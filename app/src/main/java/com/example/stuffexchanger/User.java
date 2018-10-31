package com.example.stuffexchanger;

/**
 * Created by jitu on 4/22/2017.
 */

public class User {
    int id;
    String firstname;
    String lastname;
    String password;
    String email;
    double rating;

    public User() {}
    public User(String firstname,String lastname,String email,String password)
    {
        this.firstname=firstname;
        this.lastname=lastname;
        this.password=password;
        this.email=email;
        this.rating=0.0;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if(this.rating==0)
            this.rating = rating;
        else
            this.rating=(this.rating+rating)/5.0;
    }
    @Override
public String toString()
    {
        return ("User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname
                + ", email=" + email + ", rating=" + rating + "]");
    }

}

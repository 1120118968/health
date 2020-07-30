package com.example.mobilehomework;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Food  {

    String imgUrl;
    String name;
    String price;

    public Food(String imgUrl, String name, String price, String kacl) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
        this.kacl = kacl;
    }

    public Food(String name, String price, String kacl) {
        this.name = name;
        this.price = price;
        this.kacl = kacl;
    }

    String kacl;

    public void setImgUrl(ImageView iv,String imgUrl) {
        this.imgUrl = imgUrl;
        Glide.with(iv.getContext()).load(imgUrl).into(iv);
        System.out.println("Image");

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setKacl(String kacl) {
        this.kacl = kacl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getKacl() {
        return kacl;
    }

    @Override
    public String toString() {
        return name +"\t "+ kacl ;
    }

    public void click(View v){

        Toast.makeText(v.getContext(),"you click "+getName(),Toast.LENGTH_LONG).show();

    }

}

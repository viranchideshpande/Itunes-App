package com.example.nirmalsella.itunesapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nirmal Sella on 23-10-2017.
 */

public class Itunes implements Serializable {
    String title, smallImage,largeImage;
    double price;
    boolean del;
    long _id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Itunes itunes = (Itunes) o;

        if (Double.compare(itunes.price, price) != 0) return false;
        if (title != null ? !title.equals(itunes.title) : itunes.title != null) return false;
        if (smallImage != null ? !smallImage.equals(itunes.smallImage) : itunes.smallImage != null)
            return false;
        return largeImage != null ? largeImage.equals(itunes.largeImage) : itunes.largeImage == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = title != null ? title.hashCode() : 0;
        result = 31 * result + (smallImage != null ? smallImage.hashCode() : 0);
        result = 31 * result + (largeImage != null ? largeImage.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public Itunes() {
    }

    public Itunes(String title, String smallImage, String largeImage, double price) {
        this.title = title;
        this.smallImage = smallImage;
        this.largeImage = largeImage;
        this.price = price;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Itunes{" +
                "title='" + title + '\'' +
                ", smallImage='" + smallImage + '\'' +
                ", largeImage='" + largeImage + '\'' +
                ", price=" + price +
                '}';
    }
}

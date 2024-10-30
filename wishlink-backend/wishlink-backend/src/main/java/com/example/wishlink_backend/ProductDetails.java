package com.example.wishlink_backend;



public class ProductDetails {
    private String title;
    private String price;
    private String image;


    public ProductDetails(String title, String price, String image) {
        this.title = title;
        this.price = price;
        this.image = image;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    @Override
    public String toString() {
        return "ProductDetails [title=" + title + ", price=" + price + ", image=" + image + "]";
    }
    

    
}

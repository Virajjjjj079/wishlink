package com.example.wishlink_backend;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;
    private String imageUrl;
    private String title;
    private String category;
    private String price; // New price attribute
    
    @ManyToOne
    private User user;

    public Link() {
    }

    // Updated constructor with price
    public Link(String title, String imageUrl, String url, User user, String category, String price) {
        this.url = url;
        this.imageUrl = imageUrl;
        this.title = title;
        this.user = user;
        this.category = category;
        this.price = price;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Link [id=" + id + ", category=" + category + ", url=" + url + ", imageUrl=" + imageUrl + ", title=" + title + 
               ", price=" + price + ", user=" + user + "]";
    }
}

package com.example.wishlink_backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wishlink_backend.Link;
import com.example.wishlink_backend.User;
import com.example.wishlink_backend.repository.UserRepository;
import com.example.wishlink_backend.repository.wishlinkRepository;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class wishlinkService {
   private static List<Link> links = new ArrayList<>();
    private final OkHttpClient client = new OkHttpClient();
    
    @Autowired
    private wishlinkRepository wishlinkRepository;
  @Autowired
    private UserRepository userRepository;

    
    
     
                
public String[] scrapeProductDetails(String url) throws IOException {
    // Create request
    Request request = new Request.Builder()
            .url(url)
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.5790.171 Safari/537.36")
            .build();
// .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 Edg/123.0.2420.81")
    // Execute request
    try (Response response = client.newCall(request).execute()) {
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        String finalUrl = response.request().url().toString();

        // Parse the response body with Jsoup
        String html = response.body().string();
        Document doc = Jsoup.parse(html);

        String title = "Title not found";
        String imageUrl = "Image URL not found";
        String price = "Price not found";

        // Check which site the URL belongs to
        if (finalUrl.contains("amazon") || finalUrl.contains("amzn")) {
            // Element titleElement = doc.selectFirst("span#productTitle");
            Element titleElement = doc.selectFirst("span#productTitle");
            Element imageElement = doc.selectFirst("img#landingImage, img#imgTagWrapperId");
            Element priceElement = doc.selectFirst("span.a-price-whole");
            title = titleElement != null ? titleElement.text().trim() : "Title not found";
            imageUrl = imageElement != null ? imageElement.attr("src") : "Image URL not found";
            price = priceElement != null ? priceElement.text().trim() : "Price not found";

        } else if (finalUrl.contains("flipkart")) {
            // Flipkart specific scraping
            Element titleElement = doc.selectFirst("span.VU-ZEz, h1, h2, span.product-title");
            Element imageElement = doc.selectFirst("img._53J4C-");
            // Elements imageElement = doc.select("img._53J4C- utBuJY");
            Element priceElement = doc.selectFirst("div.Nx9bqj.CxhGGd");
            title = titleElement != null ? titleElement.text().trim() : "Title not found";
            imageUrl = imageElement != null ? imageElement.attr("src") : "Image URL not found";
            price = priceElement != null ? priceElement.text().trim() : "Price not found";

        } else if (finalUrl.contains("meesho")) {
            // Meesho specific scraping
            Element titleElement = doc.selectFirst("span.sc-eDvSVe.fhfLdV");
            Element imageElement = doc.selectFirst("img[fetchpriority='high']");
            Element priceElement = doc.selectFirst("h4.sc-eDvSVe.biMVPh");

            title = titleElement != null ? titleElement.text().trim() : "Title not found";
            imageUrl = imageElement != null ? imageElement.attr("src") : "Image URL not found";
            price = priceElement != null ? priceElement.text().trim() : "Price not found";

        } else if (finalUrl.contains("croma")) {
            // Croma specific scraping
            Element titleElement = doc.selectFirst("h1.pd-title.pd-title-normal");
            Element imageElement = doc.selectFirst("img#0prod_img");
            Element priceElement = doc.selectFirst("span.pdp-price");

            title = titleElement != null ? titleElement.text().trim() : "Title not found";
            imageUrl = imageElement != null ? imageElement.attr("src") : "Image URL not found";
            price = priceElement != null ? priceElement.text().trim() : "Price not found";

        } else if (finalUrl.contains("snapdeal")) {
            // Snapdeal specific scraping
            Element titleElement = doc.selectFirst("h1.pdp-e-i-head");
            Element imageElement = doc.selectFirst("img.cloudzoom");
            Element priceElement = doc.selectFirst(".pdp-final-price .payBlkBig[itemprop=price]");

            title = titleElement != null ? titleElement.text().trim() : "Title not found";
            imageUrl = imageElement != null ? imageElement.attr("src") : "Image URL not found";
            price = priceElement != null ? priceElement.text().trim() : "Price not found";

        } else if (finalUrl.contains("firstcry")) {
            // FirstCry specific scraping
            Element titleElement = doc.selectFirst("h1.prod-name#prod_name");
            Element imageElement = doc.selectFirst("img.swiper-lazy");
            Element priceElement = doc.selectFirst("span.prod-price J15B_42  cl_21");
            title = titleElement != null ? titleElement.text().trim() : "Title not found";
            imageUrl = imageElement != null ? imageElement.attr("src") : "Image URL not found";
            price = priceElement != null ? priceElement.text().trim() : "Price not found";

        } else if (finalUrl.contains("cilory")) {
            // Cilory specific scraping
            Element titleElement = doc.selectFirst(".pname");
            Element imageElement = doc.selectFirst(".pimg");
            Element priceElement = doc.selectFirst("span[itemprop=price]");

            title = titleElement != null ? titleElement.text().trim() : "Title not found";
            imageUrl = imageElement != null ? imageElement.attr("src") : "Image URL not found";
            price = priceElement != null ? priceElement.text().trim() : "Price not found";

        }else if (finalUrl.contains("myntra")) {
            // Myntra specific scraping
            Element titleElement = doc.selectFirst("h1.pdp-name");
            Element imageElement = doc.selectFirst("div.image-grid-image[style*='background-image']");
            Element priceElement = doc.selectFirst("span.pdp-price strong");
        
            title = titleElement != null ? titleElement.text().trim() : "Title not found";
        
            title = titleElement != null ? titleElement.text().trim() : "Title not found";
            imageUrl = imageElement != null ? imageElement.attr("src") : "Image URL not found";
            price = priceElement != null ? priceElement.text().trim() : "Price not found";
            
        } else if (finalUrl.contains("hm.com")) {  
            // H&M specific scraping
            Element titleElement = doc.selectFirst("h1, h2, h3, title");
            Elements imageElement = doc.select("img[alt]");
            Element priceElement = doc.selectFirst("hm-product-price div.e26896 span.edbe20.ac3d9e.d9ca8b.e29fbf");


        
            title = titleElement != null ? titleElement.text().trim() : "Title not found";
            
            
            if (imageElement != null) {
                // Extract the srcset attribute and split it into different resolutions
                String srcset = imageElement.attr("srcset");
                String[] imageUrls = srcset.split(",");
                
                // Assuming the last one in the list is the highest resolution
                imageUrl = imageUrls.length > 0 ? imageUrls[imageUrls.length - 1].trim().split(" ")[0] : "Image URL not found";
            } else {
                imageUrl = "Image URL not found";
            }
            price = priceElement != null ? priceElement.text().trim() : "Price not found";
        } else {
            // General scraping for other websites
            Element titleElement = doc.selectFirst("h1, h2, h3, title");
            title = titleElement != null ? titleElement.text().trim() : "Title not found";
            Elements imageElement = doc.select("img");
            Elements priceElements = doc.select("span:contains(₹), span:contains(€), span:contains(£), span:contains($)"); // Searching for common currency symbols

            imageUrl = imageElement.stream()
                .filter(img -> !img.attr("src").isEmpty() && img.attr("src").contains("http"))
                .map(img -> img.attr("src"))
                .findFirst().orElse("Image URL not found");

            price = priceElements.size() > 0 ? priceElements.first().text().trim() : "Price not found";
        }

        String shortenedUrl = finalUrl.length() > 150 ? finalUrl.substring(0, 147) + "..." : finalUrl;

        return new String[]{title, imageUrl, shortenedUrl, price};
    }
}


           

    public String categorizeProductByTitle(String title) {
        title = title.toLowerCase(Locale.ROOT);
    
        // Clothing-related keywords
        if (title.contains("shirt") || title.contains("t-shirt") || title.contains("blouse") ||
        title.contains("jeans") || title.contains("pants") || title.contains("trousers") ||
        title.contains("dress") || title.contains("skirt") || title.contains("jacket") ||
        title.contains("coat") || title.contains("sweater") || title.contains("hoodie") ||
        title.contains("shoes") || title.contains("sneakers") || title.contains("boots") ||
        title.contains("sandals") || title.contains("kurta") || title.contains("kurti") ||
        title.contains("slippers") || title.contains("overalls") || title.contains("sweatshirt") ||
        title.contains("blazer") || title.contains("pajamas") || title.contains("scarf") ||
        title.contains("tie") || title.contains("shorts") || title.contains("cape") ||
        title.contains("vest")) {
    return "Clothing"; // Reduced Clothing category

} else if (title.contains("phone") || title.contains("smartphone") || title.contains("laptop") ||
           title.contains("tablet") || title.contains("camera") || title.contains("television") ||
           title.contains("tv") || title.contains("headphones") || title.contains("earphones") ||
           title.contains("speaker") || title.contains("console") || title.contains("smartwatch") ||
           title.contains("watches") || title.contains("charger") || title.contains("cables") ||
           title.contains("mouse") || title.contains("keyboard") || title.contains("router") ||
           title.contains("webcam") || title.contains("projector") || title.contains("drone") ||
           title.contains("accessories")||
           title.contains("iphone")||
           title.contains("motorola")) {
    return "Electronics & Gadgets"; // Combined Electronics and Watches

} else if (title.contains("sofa") || title.contains("couch") || title.contains("table") ||
           title.contains("chair") || title.contains("desk") || title.contains("bed") ||
           title.contains("wardrobe") || title.contains("cabinet") || title.contains("bookshelf") ||
           title.contains("dining") || title.contains("stool") || title.contains("bench") ||
           title.contains("shelf") || title.contains("furniture set") || title.contains("nightstand") ||
           title.contains("ottoman") || title.contains("console table") || title.contains("dresser") ||
           title.contains("futon")) {
    return "Furniture";

} else if (title.contains("makeup") || title.contains("skincare") || title.contains("perfume") ||
           title.contains("lotion") || title.contains("shampoo") || title.contains("conditioner") ||
           title.contains("soap") || title.contains("cream") || title.contains("serum") ||
           title.contains("deodorant") || title.contains("cosmetics") || title.contains("nail polish") ||
           title.contains("foundation") || title.contains("lipstick") || title.contains("eyeliner") ||
           title.contains("moisturizer") || title.contains("sunscreen") || title.contains("face mask") ||
           title.contains("body wash")) {
    return "Beauty & Personal Care";

} else if (title.contains("book") || title.contains("novel") || title.contains("journal") ||
           title.contains("magazine") || title.contains("comic") || title.contains("textbook") ||
           title.contains("manual") || title.contains("guide") || title.contains("biography") ||
           title.contains("encyclopedia") || title.contains("anthology") || title.contains("literature") ||
           title.contains("reference") || title.contains("graphic novel") || title.contains("story") ||
           title.contains("fiction") || title.contains("non-fiction")) {
    return "Books";

} else if(title.contains("toy") || title.contains("game") || title.contains("puzzle") ||
title.contains("lego") || title.contains("doll") || title.contains("action figure") ||
title.contains("board game") || title.contains("stuffed animal") || title.contains("card game") ||
title.contains("remote control") || title.contains("educational toy") || title.contains("building set") ||
title.contains("chess") || title.contains("carroms") || title.contains("balls") ||
title.contains("bat") || title.contains("badminton") || title.contains("racket") ||
title.contains("toy car") || title.contains("toy train") || title.contains("action figure") ||
title.contains("plush toy") || title.contains("dinosaur toy") || title.contains("model kit") ||
title.contains("pogo stick") || title.contains("scooter") || title.contains("kite") ||
title.contains("robot toy") || title.contains("magic set") || title.contains("finger puppet") ||
title.contains("craft kit")) {
    return "Toys & Games";

} else if (title.contains("jewelry") || title.contains("ring") || title.contains("necklace") ||
           title.contains("bracelet") || title.contains("earrings") || title.contains("brooch") ||
           title.contains("pendant") || title.contains("anklet") || title.contains("charm") ||
           title.contains("cufflinks") || title.contains("watch") || title.contains("accessory")) {
    return "Jewelry & Accessories"; // Combined Jewelry and Watches

} else if (title.contains("kitchen") || title.contains("appliance") || title.contains("utensil") ||
           title.contains("cookware") || title.contains("cutlery") || title.contains("mixer") ||
           title.contains("blender") || title.contains("oven") || title.contains("microwave") ||
           title.contains("toaster") || title.contains("pan") || title.contains("grater") ||
           title.contains("kettle") || title.contains("fryer") || title.contains("pot") ||
           title.contains("storage")) {
    return "Kitchen & Home Appliances";

} else if (title.contains("tool") || title.contains("hardware") || title.contains("drill") ||
           title.contains("screwdriver") || title.contains("hammer") || title.contains("wrench") ||
           title.contains("saw") || title.contains("ladder") || title.contains("sander") ||
           title.contains("painter") || title.contains("glue") || title.contains("measure") ||
           title.contains("toolbox")) {
    return "Tools & Hardware";

} else if (title.contains("sports") || title.contains("fitness") || title.contains("exercise") ||
           title.contains("yoga") || title.contains("running") || title.contains("cycling") ||
           title.contains("gym") || title.contains("ball") || title.contains("racket") ||
           title.contains("dumbbell") || title.contains("treadmill") || title.contains("bike") ||
           title.contains("outdoor") || title.contains("hiking") || title.contains("training")) {
    return "Sports & Outdoors";

}

// Default category for unmatched titles
return "Other";
    }
    

    public void saveProductDetails(String[] productDetails, Integer userId) {
        // Extract details
        String title = productDetails[0];
        String imageUrl = productDetails[1];
        String url = productDetails[2];
        String price = productDetails[3];
        // Find the User by userId
        String category = categorizeProductByTitle(title);
        User user = userRepository.findById(userId).orElse(null);

        // Create Link entity
        Link link = new Link(title, imageUrl, url, user,category,price);

        // Save Link to database
        wishlinkRepository.save(link);
    }



    
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        return userRepository.save(user);
    }

     public boolean checkUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    



    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            String storedPassword = user.getPassword().trim();
            String inputPassword = password.trim();
    
            // Debugging output
            System.out.println("Stored Password Length: " + storedPassword.length());
            System.out.println("Input Password Length: " + inputPassword.length());
    
            return inputPassword.equals(storedPassword);
        }
        return false;
    }
 
    //retrieve user id 
    public Integer getUserIdByUsername(String username) {
        Optional<User> user = userRepository.getUserIdByUsername(username);
        return user.map(User::getId).orElse(null);
        // if (user.isPresent()) {
        //     return user.get().getId().orElse(null);
        // } else {
        //     // throw new Exception("User not found with username: " + username);
        //     return 
        // }
    }



    public List<Link> getWishLinksByUserId(Integer userId) {
        return wishlinkRepository.findByUserId(userId);
    }

    public List<Link> getLinksByUserIdAndCategory(Integer userId, String category) {
        return wishlinkRepository.findByCategoryAndUserId(category, userId);
    }


public List<String[]> getRecommendedProducts(String title) throws IOException {
    List<String[]> recommendations = new ArrayList<>();

    // Search and scrape recommendations from Amazon
    recommendations.addAll(searchAndScrapeAmazon(title));

    // Search and scrape recommendations from Flipkart
    recommendations.addAll(searchAndScrapeFlipkart(title));

    // Search and scrape recommendations from Meesho
    recommendations.addAll(searchAndScrapeMeesho(title));

    return recommendations;
}

private List<String[]> searchAndScrapeAmazon(String query) throws IOException {
    String searchUrl = "https://www.amazon.in/s?k=" + query.replaceAll(" ", "+");
    return scrapeSite(searchUrl, "amazon");
}

private List<String[]> searchAndScrapeFlipkart(String query) throws IOException {
    String searchUrl = "https://www.flipkart.com/search?q=" + query.replaceAll(" ", "+");
    return scrapeSite(searchUrl, "flipkart");
}

private List<String[]> searchAndScrapeMeesho(String query) throws IOException {
    String searchUrl = "https://www.meesho.com/search?q=" + query.replaceAll(" ", "+");
    return scrapeSite(searchUrl, "meesho");
}

private List<String[]> scrapeSite(String url, String site) throws IOException {
    Request request = new Request.Builder()
        .url(url)
        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
        .build();

    try (Response response = client.newCall(request).execute()) {
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        String html = response.body().string();
        Document doc = Jsoup.parse(html);

        List<String[]> products = new ArrayList<>();
        
        switch (site) {
            case "amazon":
                products = scrapeAmazonRecommendations(doc);
                break;
            case "flipkart":
                products = scrapeFlipkartRecommendations(doc);
                break;
            case "meesho":
                products = scrapeMeeshoRecommendations(doc);
                break;


                
        }

        return products;
    }
}

// Scraping Amazon products including price
private List<String[]> scrapeAmazonRecommendations(Document doc) {
    List<String[]> products = new ArrayList<>();
    for (Element productElement : doc.select(".s-main-slot .s-result-item")) {
        String title = productElement.select(".a-text-normal").text();
        String imageUrl = productElement.select(".s-image").attr("src");
        String url = productElement.select(".a-link-normal").attr("href");
        String price = productElement.select(".a-price-whole").text();  // Selector for the price

        if (!title.isEmpty() && !imageUrl.isEmpty() && !url.isEmpty()) {
            products.add(new String[]{title, imageUrl, price, "https://www.amazon.in" + url});
        }
    }
    return products;
}

// Scraping Flipkart products including price
private List<String[]> scrapeFlipkartRecommendations(Document doc) {
    List<String[]> products = new ArrayList<>();
    for (Element productElement : doc.select("._1AtVbE")) {  // Updated Flipkart selector for product cards
        String title = productElement.select("._4rR01T").text();  // Title selector
        String imageUrl = productElement.select("img").attr("src");  // Image URL selector
        String url = productElement.select("a").attr("href");  // Link to the product
        String price = productElement.select("._30jeq3").text();  // Selector for the price

        if (!title.isEmpty() && !imageUrl.isEmpty() && !url.isEmpty()) {
            products.add(new String[]{title, imageUrl, price, "https://www.flipkart.com" + url});
        }
    }
    return products;
}

// Scraping Meesho products including price
private List<String[]> scrapeMeeshoRecommendations(Document doc) {
    List<String[]> products = new ArrayList<>();
    for (Element productElement : doc.select(".sc-dkzDqf")) {  // Updated Meesho selector
        String title = productElement.select(".sc-gsTCUz").text();  // Title selector
        String imageUrl = productElement.select("img").attr("src");  // Image URL selector
        String url = productElement.select("a").attr("href");  // Product link selector
        String price = productElement.select(".sc-fHYomB").text();  // Selector for the price

        if (!title.isEmpty() && !imageUrl.isEmpty() && !url.isEmpty()) {
            products.add(new String[]{title, imageUrl, price, "https://www.meesho.com" + url});
        }
    }
    return products;
}

}
package com.example.wishlink_backend.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wishlink_backend.Link;
import com.example.wishlink_backend.User;
import com.example.wishlink_backend.repository.UserRepository;
import com.example.wishlink_backend.repository.wishlinkRepository;
import com.example.wishlink_backend.service.wishlinkService;





@RestController
public class wishlinkJpaController {

    

private wishlinkRepository myrepository;
private wishlinkService wishlinkservice;


@Autowired
private UserRepository userRepository;


    public wishlinkJpaController(wishlinkRepository myrepository,wishlinkService wishlinkservice) {
    this.myrepository = myrepository;
    this.wishlinkservice=wishlinkservice;
    
}




    @GetMapping("/links")
    public List<Link> getMethodName() {
        return myrepository.findAll();
    }

    @GetMapping("public/links/{userId}")
    public List<Link> getpublicLinksByUserId(@PathVariable String userId) {
        Integer userIdInt = Integer.parseInt(userId);
        return wishlinkservice.getWishLinksByUserId(userIdInt);
    }
    @GetMapping("/links/{userId}")
    public List<Link> getLinksByUserId(@PathVariable String userId) {
        Integer userIdInt = Integer.parseInt(userId);
        return wishlinkservice.getWishLinksByUserId(userIdInt);
    }
     
    
    
    

    
    @PostMapping("scrape/extension")
    public ResponseEntity<String[]> webScrape(@RequestBody ScrapeRequest request) {
        try {
            String url = request.getUrl();
            Integer userIdInt = request.getUserId();
            String[] productDetails = wishlinkservice.scrapeProductDetails(url);

            // Process productDetails as needed
            wishlinkservice.saveProductDetails(productDetails, userIdInt);

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 OPR/109.0.0.0");
            return ResponseEntity.ok().headers(headers).body(productDetails);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Return a generic error message in case of failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }







    // @CrossOrigin(origins = "https://www.flipkart.com")
    @PostMapping("/scrape/{userId}")
    public ResponseEntity<String[]> webScrape(@RequestBody String url, @PathVariable String userId) {
        try {
            // String productDetails = wishlinkservice.scrapeProductDetails(url);
            Integer userIdInt = Integer.parseInt(userId);
                String[] productDetails = wishlinkservice.scrapeProductDetails(url);
                // Process productDetails as needed
             
                wishlinkservice.saveProductDetails(productDetails, userIdInt);
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 OPR/109.0.0.0");
            //  myrepository.save(productDetails);
            // return ResponseEntity.ok().headers(headers).body(productDetails);
            return ResponseEntity.ok().headers(headers).body(productDetails);
            
        } catch (Exception e) {
            e.printStackTrace();
        // Return a generic error message in case of failure
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> DeleteTodoById(@PathVariable  int id) {
       
    //todoService.deletebyId(id);
    myrepository.deleteById(id);
    
    return ResponseEntity.noContent().build();
    }
    

//create user 
@PostMapping("create-user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = wishlinkservice.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    //check login data authenticate
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        boolean isAuthenticated = wishlinkservice.authenticate(user.getUsername(), user.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password. Please try again.");
        }
    }

    //check username is taken or not
    
    @GetMapping("/users")
    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    // @GetMapping("/check-username")
    // public boolean checkUsername(@RequestParam String username) {
    //     return wishlinkservice.checkUsername(username);
    // }
    
    @GetMapping("/check-username")
public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestParam String username) {
    boolean exists = wishlinkservice.checkUsername(username);
    Map<String, Boolean> response = new HashMap<>();
    response.put("exists", exists);
    return ResponseEntity.ok(response);
}


//get id of user
@GetMapping("/getid")
public ResponseEntity<Integer> getUserId(@RequestParam String username) {
    Integer userId = wishlinkservice.getUserIdByUsername(username);
    if (userId != null) {
        return ResponseEntity.ok(userId);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


}







//     @PostMapping("/scrape")
// public ResponseEntity<String> webScrape(@RequestBody String url) {
//     try {
//         String productDetails = wishlinkservice.scrapeProductDetails(url);
//         HttpHeaders headers = new HttpHeaders();
//         headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 OPR/109.0.0.0");
//         return ResponseEntity.ok().headers(headers).body(productDetails);
//     } catch (Exception e) {
//         e.printStackTrace();
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching product details.");
//     }
// }




//recommend
@GetMapping("/recommendations")
    public ResponseEntity<List<String[]>> getRecommendedProducts(@RequestParam String title) {
        try {
            List<String[]> recommendedProducts = wishlinkservice.getRecommendedProducts(title);
            return ResponseEntity.ok(recommendedProducts);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }



    //url verified and tested 
    //http://192.168.0.113:8080/recommendations?title=Babyhug Marvel Cotton Knit Half Sleeve T-Shirt with Spiderman Graphics Pack Of 3 - Multicolour


    @GetMapping("/user/{userId}/category/{category}")
    public List<Link> getLinksByUserIdAndCategory(
            @PathVariable Integer userId,
            @PathVariable String category) {
        return wishlinkservice.getLinksByUserIdAndCategory(userId, category);
    }



    @PostMapping("/share")
    public ResponseEntity<String> handleShare(@RequestBody ScrapeRequest scrapeRequest) {
        try {
            String url = scrapeRequest.getUrl();
            int userId = scrapeRequest.getUserId();

            String[] productDetails = wishlinkservice.scrapeProductDetails(url);
            wishlinkservice.saveProductDetails(productDetails, userId);

            return ResponseEntity.ok("URL successfully scraped and saved.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to scrape and save URL.");
        }
    }

}
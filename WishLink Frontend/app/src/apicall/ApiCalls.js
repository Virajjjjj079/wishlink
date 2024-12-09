import { ApiClient } from "./ApiClient";


// export function webScrapeApi(url) {
    

//     console.log('Todo object sent:', url);
//     return ApiClient.post(`/scrape`, url);
// }





export function webScrapeApi(url,userId) {
    console.log('URL sent:', url);
    return ApiClient.post(`/scrape/${userId}`, url, {
        headers: {
            'Content-Type': 'text/plain',  // Ensure that the content type is correctly set
        },
    });
}

export function ShowDetailsBelow(){
    console.log('ShowDetailsBelow function called');
    return ApiClient.get('/links');
}

export function DeleteLinkApi(id){
    console.log('ShowDetailsBelow function called');
    return ApiClient.delete(`/delete/${id}`);
}

export function createUser(user){
    console.log('user created');
    return ApiClient.post('/create-user', user);
}

export function checkLoginApi(user){
    console.log('checking login')
    return ApiClient.post('/login', user);
}




// export function checkUsernameApi(username) {
//     return ApiClient.get(`/check-username`, {
//       params: { username }
//     });
//   }

export function checkUsernameApi(username) {
    return ApiClient.get('/check-username', { params: { username } });
  }

  export function getUserId(username) {
    return ApiClient.get('/getid', { params: { username } });
  }

  export function getLinksByuserId(userId) {
    return ApiClient.get(`/links/${userId}`);
  }

  export function getRecommendations(title) {
    return ApiClient.get('/recommendations', { params: { title} });
  }

  export function getLinksBycategoryAndUserId(userId,category) {
    return ApiClient.get(`/user/${userId}/category/${category}` );
  }

  

  

  




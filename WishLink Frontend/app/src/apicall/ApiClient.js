import axios from 'axios'

//create a base url so we dont need to repeat localhost:8080 again and again
export const ApiClient = axios.create(
    {
        // baseURL: 'http://192.168.0.113:8080'  //home wifi
        //    baseURL: 'http://172.18.128.1:8080'// deven  
        baseURL: 'http://192.168.68.134:8080'   
       //viraj phone
        
        
        //'http://localhost:8080' 'http://192.168.138.134:8080' http://192.168.20.134:8080
        
        // 
    }
)
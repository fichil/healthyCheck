
//全局路由守卫

//1、引入axios
import axios from "axios";
import { getToken, removeToken } from "@/utils/authToken";
import router from "@/router/index";

// import qs from 'qs'


//2、创建一个axios
var http = axios.create({
    baseURL:"/api",
    timeout:10000
})

//#允许向服务器发送前，修改数据
// http.defaults.transformRequest = function(data){
//     data = qs.stringify(data);
//     return data;
// }

//3、设置请求拦截器，统一设置一些请求头
http.interceptors.request.use(config=>{
    if (getToken()) {
        console.log(getToken());
        
        config.headers["user_token"] = getToken();
    }
    return config;
})

//4、设置响应拦截器
http.interceptors.response.use(
    res => {
        // console.log(res);
        if(res.data.code == 401){

            alert(res.data.message)
            removeToken(); // 移除过期的 token
            if (router.history.current.path != '/login') {
                router.push("/login") 
            }else{
                router(false)
            }
            
        }else{
            // 响应成功处理
            return res.data;

        }

    },
    error => {
        // 响应错误处理
        if (error.response) {
            const status = error.response.status;
            if (status == 404) {
                alert("资源不存在");
            } else if (status == 500) {
                alert("服务器错误");
            } else if (status == 403) {
                alert("权限不足");
            }
        } else {
            alert("网络错误");
        }
        return Promise.reject(error);
    }
);
//导出
export default http;
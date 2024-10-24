

import store from "@/store/index";

import { getToken } from "@/utils/authToken";

import router from "./router/index";

//全局路由守卫
router.beforeEach(async(to,from,next)=>{
    //放行百名单
    var whiteList = ['/','/login','/index','/register','/user/register'];
    var index = whiteList.indexOf(to.path)
  
    if (index != -1) {
        next();
    }else{

        if(getToken()){
            //vuex中是否存在用户信息
            if(!store.state.user.userId){
                try{
                    var res = await store.dispatch("getUserInfo")
                    if (res) {
                        next();
                    }else{
                        return
                    }
                    
                }catch(error){
                    console.error('获取管理员信息失败', error);
                    next('/index');
                }
       
                
            }else{
                next();
            }
            
        } else {
           alert("请先登陆")
           if (to.path != from.path) {
            next('/index')
           }else{
            next(false)
           }
            
        }
    } 
})
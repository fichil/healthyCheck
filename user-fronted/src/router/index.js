//引入路由数组
import VueRouter from "vue-router";
//引入Vue模块
import Vue from "vue";



//使用路由模版，将路由模块挂给vue
Vue.use(VueRouter);

//创建路由数组
var routes = [
    {
        name:"",
        path:"/",
        component:()=>import("@/views/IndexView.vue")
    },
    {
        name:"index",
        path:"/index",
        component:()=>import("@/views/IndexView.vue")
    },
    {
        name:"login",
        path:"/login",
        component:()=>import("@/views/LoginView.vue")
    },
    {
        name:"register",
        path:"/register",
        component:()=>import("@/views/RegisterView.vue")
    },
    {
        name:"register",
        path:"/register",
        component:()=>import("@/views/RegisterView.vue")
    },
    {
        name:"profile",
        path:"/profile",
        component:()=>import("@/views/ProfileView.vue")
    },
    
];

//创建路由器
var router = new VueRouter({
    mode:"hash",
    routes
});



//抛出路由器
export default router;

import Vue from "vue";

import Vuex from 'vuex';

import http from "@/utils/request";
import { getToken } from "@/utils/authToken";
import { removeToken } from "@/utils/authToken";
Vue.use(Vuex);

//创建vux状态管理器
var store = new Vuex.Store({
    state:{
        user:{},
    },
    getters:{

        
    },
    mutations:{
        setUser(state, user) {
            state.user = user;
        },
        clearUser(state) {
        state.user = {};
        }


    },
    actions:{
        getUserInfo(){
            return http.get(
                "/user/getUserInfo",
                {
                    params:{
                        token:getToken()
                    }
                }
            ).then(res =>{
                if (!res) {
                    return false;
                }else{
                    console.log(res);
                    
                    store.commit("setUser",res.data.userInfo);
                    return true;
                    
                }
      
            })
        },
        logoutUser({ commit }) {
            commit('clearUser');
            removeToken();
        },
    }
});

export default store;
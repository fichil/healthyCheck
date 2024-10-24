import Vue from 'vue';
import Vuex from 'vuex';
import App from './App.vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';


import router from '@/router/index';

import http from '@/utils/request';

import store from '@/store/index';

import './permission';

Vue.use(Vuex);

Vue.prototype.$axios = http;
Vue.config.productionTip = false

Vue.use(ElementUI);

new Vue({
  render: h => h(App),
  router,
  store,
}).$mount('#app')

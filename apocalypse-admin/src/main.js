// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Button from 'ant-design-vue/lib/button'
import App from './App'
import router from './router'
import 'ant-design-vue/dist/antd.css'
import Rate from 'ant-design-vue/lib/rate'
Vue.config.productionTip = false

Vue.use(Button)
Vue.use(Rate)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

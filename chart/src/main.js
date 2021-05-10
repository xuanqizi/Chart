import Vue from 'vue'
import App from './App.vue'
import Button from 'ant-design-vue/lib/button'
import Icon from 'ant-design-vue/lib/icon'
import Input from 'ant-design-vue/lib/input'

Vue.component(Button.name, Button)
Vue.component(Icon.name, Icon)
Vue.component(Input.name, Input)

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')

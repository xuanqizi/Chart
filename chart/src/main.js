import Vue from 'vue'
import App from './App.vue'
import Button from 'ant-design-vue/lib/button'
import Icon from 'ant-design-vue/lib/icon'
import Input from 'ant-design-vue/lib/input'
import VeLine from 'v-charts/lib/line.common'
import 'echarts/lib/component/dataZoom'

Vue.use(Button)
Vue.use(Icon)
Vue.use(Input)
Vue.component(VeLine.name, VeLine)

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')

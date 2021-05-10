import Vue from 'vue'
import App from './App.vue'
import VeLine from 'v-charts/lib/line.common'
import 'echarts/lib/component/dataZoom'

Vue.component(VeLine.name, VeLine)

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')

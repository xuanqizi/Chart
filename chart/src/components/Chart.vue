<template>
    <div>
        HelloWorld
        <LineChart :chartData="chartData" />
    </div>
</template>

<script>
import LineChart from './LineChart.vue'

export default {
    name: 'Chart',
    components: {
        LineChart
    },
    data() {
        return {
            chartData: null,
        }
    },
    mounted() {
        this.fillData();
    },
    methods: {
        fillData() {
            this.chartData = {
                labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
                datasets: [{
                    label: 'My First Dataset',
                    data: [65, 59, 80, 81, 56, 55, 40],
                    fill: false,
                    borderColor: 'rgb(75, 192, 192)',
                    tension: 0.1
                }]
            }
        }
    }
}
</script>

<style>

</style>
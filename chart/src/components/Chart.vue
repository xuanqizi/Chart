<template>
    <div>
        <a-input type="file" @change="handleChange"></a-input>
        <div class="chart-wrapper">
            <LineChart 
                v-if="fileDataLoaded"
                :chartData="chartData" 
                :options="options" />
        </div>
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
            chartData: {},      // 用于存储图表的数据
            fileDataLoaded: false,  // 用于标记文件读取是否完成
            options: {      // 图表的配置选项
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    zoom: {
                        zoom: {
                            enabled: true,      // 开启缩放功能
                            drag: false,         // 设定是否开启拖动缩放
                            mode: 'xy',         // xy轴均可缩放
                            speed: 0.1,
                        },
                        pan: {
                            enabled: true,      // 开启平移功能
                            mode: 'xy',         // xy轴均可平移
                        }
                    }
                }
            },
        }
    },
    mounted() {
        // do nothing
    },
    methods: {
        /**
         * 用于响应点击上传文件按钮的请求
         */
        handleChange(e) {
            const file = e.target.files[0];
            console.log(file);
            this.readFile(file);
        },
        /**
         * 对上传的文件进行读取处理
         */
        readFile(file) {
            const fileReader = new FileReader();
            fileReader.readAsArrayBuffer(file);
            fileReader.onloadend = (ev) => {
                console.log(ev);
                const arrayBuffer = ev.target.result;
                this.fillData(arrayBuffer);
            }
        },
        /**
         * 根据文件读取的结果，对图表数据进行填充
         */
        fillData(arrayBuffer) {
            console.log(arrayBuffer);
            const length = Math.floor(arrayBuffer.byteLength / 2);
            const int16Array = new Int16Array(arrayBuffer, 0, length);
            let _labels = [];
            let _dataset = {
                label: 'File Data',
                data: [],
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1
            }
            int16Array.forEach((value, index) => {
                _labels.push(index);
                _dataset.data.push(value);
            });
            this.chartData = {
                labels: _labels,
                datasets: [_dataset]
            }
            this.fileDataLoaded = true;
        },
    }
}
</script>

<style scoped>

.chart-wrapper {
    width: 100%;
    height: 100%;
}

</style>
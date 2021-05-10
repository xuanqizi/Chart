<template>
    <div>
        <input type="file" @change="handleChange" />
        <button type="primary" @click="handleDifferential">微分</button>
        <button type="primary" @click="handleIntegral">积分</button>
        <ve-line
            v-if="fileDataLoaded"
            :data="chartData"
            :data-zoom="dataZoom"
            height="300px"
        />
        <ve-line
            v-if="processed"
            :data="processedData"
            :data-zoom="dataZoom"
            height="300px"
        />
    </div>
</template>

<script>
import { differential } from '../functions/differential'
import { integral } from '../functions/integral'

export default {
    name: 'Chart',
    data() {
        return {
            chartData: {},      // 用于存储图表的数据
            processedData: {},  // 用于存储处理后的数据，作为第二图表的数据源
            fileDataLoaded: false,  // 用于标记文件读取是否完成
            processed: false,       // 用于标记是否进行了数据处理（即是否需要显示第二个通道）
            dataZoom: [
                {   // 开启x轴的滑动条缩放
                    type: 'slider',
                    xAxisIndex: 0,
                    start: 0,
                    end: 20
                },
                {   // 开启x轴的滚轮缩放
                    type: 'inside',
                    xAxisIndex: 0,
                    start: 0,
                    end: 20
                },
                {   // 开启y轴的滑动条缩放
                    type: 'slider',
                    yAxisIndex: 0,
                    start: 0,
                    end: 100
                },
                {   // 开启y轴的滚轮缩放
                    type: 'inside',
                    yAxisIndex: 0,
                    start: 0,
                    end: 100
                }
            ]
        }
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
            let _columns = ['x', 'y'];
            let _rows = [];
            int16Array.forEach((value, index) => {
                _rows.push({'x': index, 'y': value});
            });
            this.chartData = {
                columns: _columns,
                rows: _rows
            }
            this.fileDataLoaded = true;
        },
        /**
         * 处理对微分按钮的点击事件
         */
        handleDifferential() {
            const _columns = ['x', '微分'];
            const _rows = differential(this.chartData.rows);
            this.processedData = {
                columns: _columns,
                rows: _rows
            };
            this.processed = true;
        },
        /**
         * 处理对积分按钮的点击事件
         */
        handleIntegral() {
            const _columns = ['x', '积分'];
            const _rows = integral(this.chartData.rows);
            this.processedData = {
                columns: _columns,
                rows: _rows
            };
            this.processed = true;
        }
    }
}
</script>

<style scoped>

button {
    margin-right: 30px;
}

</style>
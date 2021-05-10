<template>
    <div>
        <a-input type="file" @change="handleChange"></a-input>
        <a-button type="primary" @click="reset">Reset Zoom</a-button>
        <div class="chart-wrapper">
            <ve-line
                v-if="fileDataLoaded"
                :data="chartData"
                :data-zoom="dataZoom"
            />
            <!--
            <LineChart 
                v-if="fileDataLoaded"
                :chartData="chartData" 
                :options="options" />
            -->
        </div>
    </div>
</template>

<script>
export default {
    name: 'Chart',
    data() {
        return {
            chartData: {},      // 用于存储图表的数据
            fileDataLoaded: false,  // 用于标记文件读取是否完成
            dataZoom: [
                {
                    type: 'slider',
                    xAxisIndex: 0,
                    start: 0,
                    end: 20
                },
                {
                    type: 'inside',
                    xAxisIndex: 0,
                    start: 0,
                    end: 20
                },
                {
                    type: 'slider',
                    yAxisIndex: 0,
                    start: 0,
                    end: 100
                },
                {
                    type: 'inside',
                    yAxisIndex: 0,
                    start: 0,
                    end: 100
                }
            ]
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
        reset() {
            
        }
    }
}
</script>

<style scoped>

.chart-wrapper {
    width: 100%;
    height: 100%;
}

</style>
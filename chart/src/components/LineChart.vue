<script>
import { Line, mixins } from 'vue-chartjs';

const { reactiveProp } = mixins;

const options = {
    plugins: {
        zoom: {
            enabled: true,      // 开启缩放功能
            mode: 'xy',         // xy轴均可缩放
        },
        pan: {
            enabled: true,      // 开启平移功能
            mode: 'xy',         // xy轴均可平移
        }
    }
};

export default {
    extends: Line,
    mixins: [reactiveProp],
    props: ['options'],
    mounted() {
        // this.chartData 在 mixin 创建
        this.renderData(this.chartData, options);
    }
}
</script>

<style></style>

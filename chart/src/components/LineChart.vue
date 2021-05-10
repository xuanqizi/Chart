<script>
import { Line, mixins } from 'vue-chartjs';
import zoom from 'chartjs-plugin-zoom'

const { reactiveProp } = mixins;

export default {
    extends: Line,
    mixins: [reactiveProp],
    props: ['options'],
    mounted() {
        // add zoom plugin
        this.addPlugin(zoom);
        // this.chartData 在 mixin 创建
        this.renderChart(this.chartData, this.options);
    }
}
</script>

<style></style>

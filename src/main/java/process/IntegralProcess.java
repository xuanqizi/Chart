package process;

import java.util.ArrayList;
import java.util.List;

/**
 * 对数据进行积分处理的类
 * @author Zhenxi Chen
 * @date 2021/4/30
 */
public class IntegralProcess implements Process {

    private static final int SUM_NUMBER = 100;
    
    /**
     * 对数据的前一百个点求和（若不足100个则将前面的所有点求和）
     * @param list 输入数据
     * @return 输出数据
     */
    @Override
    public List<Integer> process(List<Short> list) {
        if (list == null) {
            return null;
        }
        int length = list.size();
        List<Integer> res = new ArrayList<>();
        if (length == 1) {
            return res;
        }
        for (int i = 0; i < length; i++) {
            int begin = Math.max(0, i - SUM_NUMBER + 1);    // 计算求和开始的位置下标
            // 对所设范围进行求和
            int sum = 0;
            for (int j = begin; j <= i; j++) {
                sum += list.get(j);
            }
            res.add(sum);
        }
        return res;
    }

}

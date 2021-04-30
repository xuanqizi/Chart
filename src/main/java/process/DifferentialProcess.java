package process;

import java.util.ArrayList;
import java.util.List;

/**
 * 对数据进行微分处理的类
 * @author Zhenxi Chen
 * @date 2021/4/30
 */
public class DifferentialProcess implements Process {

    @Override
    public List<Integer> process(List<Short> list) {
        List<Integer> res = new ArrayList<>();
        int length = list.size();
        if (length <= 1) {      // 若数据点小于等于1个，直接返回空集合
            return res;
        }
        for (int i = 1; i < length; i++) {
            int diff = list.get(i) - list.get(i - 1);
            res.add(diff);
        }
        return res;
    }
}


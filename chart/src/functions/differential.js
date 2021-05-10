// differential.js
// 实现微分操作的函数

/**
 * 将数据进行微分操作
 * @param {Array} rows 处理前的数据 json数组 存储了x和y的值
 * @returns {Array} 处理后的数据，格式与输入的rows相同
 */
export function differential(rows) {
    const length = rows.length;     // 输入数组的长度
    let res = [];       // 返回数组，用于存放处理后的数据
    for (let i = 1; i < length; i++) {
        let rowJson = {};
        const xValue = i - 1;
        const yValue = rows[i]['y'] - rows[i - 1]['y'];
        rowJson['x'] = xValue;
        rowJson['微分'] = yValue;
        res.push(rowJson);
    }
    return res;
}
// integral.js

const sumNumber = 10;       // 将一个数的前十个数相加作为积分

/**
 * 将数据进行积分操作
 * @param {Array} rows 处理前的数据 json数组 存储了x和y的值
 * @returns {Array} 处理后的数据，格式与输入的rows相同
 */
export function integral(rows) {
    console.log(rows);
    const length = rows.length;     // 输入数组的长度
    let res = [];       // 返回数组，用于存放处理后的数据
    for (let i = 0; i < length; i++) {
        let rowJson = {};
        const xValue = i;
        const beginIndex = Math.max(0, i - sumNumber + 1);
        let y = 0;
        for (let j = beginIndex; j <= i; j++) {
            y += rows[j]['y'];
        }
        rowJson['x'] = xValue;
        rowJson['积分'] = y;
        res.push(rowJson);
    }
    console.log(res);
    return res;
}
package com.xs.micro.check.invoicing.extension.util;

/**
 * 比较两个字符串的相似度算法
 *
 * @author guochaohui
 */
public class LevenshteinDistanceUtil {

    /**
     * 比较两个字符串的相识度
     * 核心算法：用一个二维数组记录每个字符串是否相同，如果相同记为0，不相同记为1，每行每列相同个数累加
     * 则数组最后一个数为不相同的总数，从而判断这两个字符的相识度
     *
     * @param str
     * @param target
     * @return
     */
    private static double compare(String str, String target) {
        int d[][];              // 矩阵
        int n = str.length();
        int m = target.length();
        int i;                  // 遍历str的
        int j;                  // 遍历target的
        char ch1;               // str的
        char ch2;               // target的
        int temp;               // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        // 初始化第一列
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        // 初始化第一行
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }
        for (i = 1; i <= n; i++) {
            // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return Double.valueOf(d[n][m]);
    }


    /**
     * 获取最小的值
     */
    private static int min(int one, int two, int three) {
        return Integer.min(Integer.min(one, two), three);
    }

    /**
     * 获取两字符串的相似度
     */
    public static double getSimilarityRatio(String source, String target) {
        double sourceLength = Double.valueOf(source.length());
        double targetLength = Double.valueOf(target.length());
        double maxLength = Double.max(sourceLength, targetLength);
        return 1 - compare(source, target) / maxLength;
    }

}

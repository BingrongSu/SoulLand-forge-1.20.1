package net.robert.soulland.helper;

import net.robert.soulland.SoulLand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MathHelper {
    public static final List<Double> list = new ArrayList<>();
    public static List<Double> stuckList = new ArrayList<>();

    public static int getAFMaxProgress(int pillLevel) {
        return pillLevel * 200;
    }

    public static double level2SoulPower(Integer level) {
        if (level == 100) {
            return 30000000;
        }
        double amount = 1000000d*level*level/(980100d - 9801d*level);
        if (level < 90) {
            return amount;
        } else {
            return amount * Math.pow((1 + (level-89)/10d), (level)/10d - 7);
        }
    } // Checked

    public static double soulPower2level(int amount) {
        if (amount == 0) {
            return 0;
        }
        for (int i = 0; i < 99; i++) {
            if (list.get(i) <= amount && amount < list.get(i+1)) {
                return i + 1;
            }
        }
        return 100;
    } // Checked

    public static double naturalIncrease(int level) {
        double n = 1000 * (-level*level + 199*level + 100) / (1411344d * (99 - level) * (100 - level));
        double random = Math.random();
        if (level < 90) {
            if (random < n) {
                return 1;
            } else {
                return 0;
            }
        } else {
            n = (level2SoulPower(level + 1) - level2SoulPower(level)) / 144000d;
            if (random < n - (int) n) {
                return (int) n + 1;
            } else {
                return (int) n;
            }
        }
    } // Checked

    public static int getInitialLevel(long seed) {
        return gaussianRandom(seed, 6d, 1.5d, 1d, 10d);
    } // Checked

    public static int naturalRecover(int level) {
        int ans, base, bonus;
        double value;
        if (level < 70) {
            value = 11000 * Math.pow(level, 0.9) / (5880600d - 58806d * level);
        } else if (level < 90) {
            value = 12000 * Math.pow(level, 0.9) / (5880600d - 58806d * level);
        } else if (level < 99) {
            value = 15000 * Math.pow(level, 0.8) / (5880600d - 58806d * level) * Math.pow(1+(level-89)/10d, (level/10d)-7);
        } else {
            value = 20000 * Math.pow(level, 0.8) / (5880600d - 58806d * level) * Math.pow(1+(level-89)/9d, (level/10d)-7);
        }
        base = (int) value;
        bonus = Math.random() <= value - base ? 1 : 0;
        ans = base + bonus;
        return ans;
    } // checked

    /**
     * @param seed 随机种子
     * @param mean 期望值，中心点
     * @param stdDeviation 标准差，决定分布宽度
     * @param min 生成的最小值
     * @param max 生成的最大值
     * @return 正态分布的随机数
     */
    public static int gaussianRandom(long seed, double mean, double stdDeviation, double min, double max) {
        Random random = new Random(seed);

        int result;
        do {
            // 生成正态分布随机数
            double randomValue = mean + random.nextGaussian() * stdDeviation;

            // 四舍五入并强制限制范围在 1 到 10
            result = (int) Math.round(randomValue);
        } while (result < min || result > max); // 确保在范围内

        return result;
    } // checked

    public static void initialize() {
        SoulLand.LOGGER.info("Initializing Math Helper");
        for (int i = 0; i < 99; i++) {
            list.add(level2SoulPower(i + 1));
        }
        list.add(30000000d);     // 100级需要的魂力：3000万
        for (int i = 0; i < 10; i++) {
            stuckList.add(list.get(10 * i + 9) - 1);
        }
    }

    public static boolean isGetStuck(double maxSoulPower) {
        return stuckList.contains(maxSoulPower);
    }

    public static double addMaxSoulPower(double initial, double increment) {
        int index = Arrays.binarySearch(stuckList.toArray(), initial);
        if (index >= 0) return initial;
        return Math.min(increment, stuckList.get(index + 1) - initial) + initial;
    }
}

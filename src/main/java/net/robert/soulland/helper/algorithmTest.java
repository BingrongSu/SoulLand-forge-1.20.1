package net.robert.soulland.helper;

import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;

public class algorithmTest {
    public static void main(String[] args) {

//        List<Double> list1 = List.of(3d, 1d, 2d);
//        List<Double> list2 = new java.util.ArrayList<>(List.of(2d, 1d, 3d));
//        boolean b = true;
//        for (int i = 0; i < 3; i++) {
//            boolean fit = false;
//            for (int j = 0; j < 3-i; j++) {
//                if (list2.get(j).equals(list1.get(i))) {
//                    list2.remove(j);
//                    fit = true;
//                    break;
//                }
//            }
//            if (!fit) {
//                b = false;
//                break;
//            }
//        }
//        System.out.println(b);
        int result2 = 0, result3 = 0;
        List<String> pool = List.of("a", "b", "c");
        for (int i = 0; i < 1000; i++) {
            List<String> result = new ArrayList<>();
            RandomSource random = RandomSource.create();
            result.add(pool.get(random.nextInt(pool.size())));
            if (random.nextLong() % 6 == 0) {
                String element = pool.get(random.nextInt(pool.size()));
                if (!result.contains(element))
                    result.add(element);
            }
            if (random.nextLong() % 6 == 0) {
                String element = pool.get(random.nextInt(pool.size()));
                if (!result.contains(element))
                    result.add(element);
            }
            if (result.size() == 2) result2++;
            if (result.size() == 3) result3++;
        }
        System.out.println(result2);
        System.out.println(result3);
    }
}

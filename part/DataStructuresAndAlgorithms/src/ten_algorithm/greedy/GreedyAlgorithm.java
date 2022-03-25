package ten_algorithm.greedy;

import java.util.*;

/**
 * @ClassName GreedyAlgorithm
 * @Description 贪心算法解决集合覆盖问题
 * @Author Zhangyuhan
 * @Date 2021/3/23
 * @Version 1.0
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        // 创建广播电台，放入到Map中
        HashMap<String, Set<String>> broadcasts = new HashMap<String, Set<String>>();
        // 将各个电台放入到broadcasts里面去
        Set<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");

        Set<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");

        Set<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");

        Set<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");

        Set<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");

        // 初始化电台
        broadcasts.put("k1", hashSet1);
        broadcasts.put("k2", hashSet2);
        broadcasts.put("k3", hashSet3);
        broadcasts.put("k4", hashSet4);
        broadcasts.put("k5", hashSet5);

        //allAreas 存放所有的地区
        Set<String> allAreas = new HashSet<>();
        allAreas.add("北京");
        allAreas.add("上海");
        allAreas.add("天津");
        allAreas.add("广州");
        allAreas.add("深圳");
        allAreas.add("成都");
        allAreas.add("杭州");
        allAreas.add("大连");

        // 创建一个ArrayList，存放所选择的电台集合
        List<String> selects = new ArrayList<>();

        // 定义一个临时的集合，在遍历过程中，存放遍历到的电台所覆盖的地区和当前还没有的覆盖的地区的交集
        Set<String> tempSet = new HashSet<>();


        // 再定义一个maxKey,保存在一次遍历过程中，能够覆盖最大未覆盖地区对应的电台的key
        // 如果maxKey不为空，则会加入到selects里面
        String maxKey;
        int maxTemp;
        while (allAreas.size() != 0) {// 如果allAreas不为0则表示还没有覆盖到所有的地区
            // 每进行一次循环，就要将maxKey置空
            maxKey = null;
            maxTemp = 0;
            // 遍历broadcasts,去除对应的key
            for (String key : broadcasts.keySet()) {
                // 每次还需将tempSet清空
                tempSet.clear();

                // 当前这个key能够覆盖的地区
                Set<String> areas = broadcasts.get(key);
                tempSet.addAll(areas);

                // 求出两个tempSet和allAreas集合的交集，交集会赋给tempSet
                tempSet.retainAll(allAreas);

                // 如果当前这个集合包含的未覆盖地区的数量比maxKey指向的集合未覆盖的地区还要多
                // 就需要重置maxKey
                if (tempSet.size() > 0 &&
                        (maxKey == null || tempSet.size() > maxTemp)) {// 该判断体现出贪心算法的特点，每次都选最优的
                    maxKey = key;
                    maxTemp = tempSet.size();
                }
            }
            // 如果maxKey不等于空，将应该将maxKey假如到selects
            if (maxKey != null) {
                selects.add(maxKey);
                // 将maxKey指向的广播电台覆盖的地区从allAreas中清除掉
                allAreas.removeAll(broadcasts.get(maxKey));
            }
        }

        System.out.println("得到的选择结果是" + selects);
    }
}















import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class A {
    public static void main(String[] args) {
        List<Double> list = Arrays.asList(1000d, 900d, 800d, 700d, 600d, 500d, 400d, 300d, 200d, 100d);
        System.out.println(calBoxplotData(list));
    }

    public static Integer calTimeValue(String timeStr) {
        String[] arr = timeStr.split(":");
        String o1h = arr[0];
        String o1m = arr[1];
        if (o1h.length() == 1) {
            o1h = "0" + o1h;
        }
        if (o1m.length() == 1) {
            o1m = o1m + "0";
        }
        return Integer.parseInt(o1h) * 100 + Integer.parseInt(o1m);
    }

    public static Map<String, Object> calBoxplotData(List<Double> dataList) {
        int len = dataList.size();
        List<Double> boxplotData = new ArrayList<>();
        // 10 25 50 75 90
        // 从小到大排个序
        dataList.sort(Double::compareTo);
        Collections.addAll(boxplotData, dataList.get((int) (len * 0.1)),
                dataList.get((int) (len * 0.25)),
                dataList.get((int) (len * 0.5)),
                dataList.get((int) (len * 0.75)),
                dataList.get((int) (len * 0.9)));
        Map<String, Object> res = new HashMap<>();

        double avgData = dataList.stream().mapToDouble(e -> e).average().orElse(0D);

        res.put("boxplotData", boxplotData);
        res.put("avgData", avgData);
        return res;
    }
}
package com.zh.wordDemo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GenerateTaperPicture {
    public static void main(String[] args) {
//        contact_main.Class1 demo1 = new contact_main.Class1();
        String file1 = "/Users/zhangyuhan/Work/WorkProject/installconstruction/等效锥度/Rail_L1_K23+140.txt";
        List<List<Double>> list1 = new ArrayList<>();
        readData(file1, list1);
        String file2 = "/Users/zhangyuhan/Work/WorkProject/installconstruction/等效锥度/Rail_R1_K23+140.txt";
        List<List<Double>> list2 = new ArrayList<>();
        readData(file2, list2);
        String file3 = "/Users/zhangyuhan/Work/WorkProject/installconstruction/等效锥度/Wheel_1che1zhou_L_12003.txt";
        List<List<Double>> list3 = new ArrayList<>();
        readData(file3, list3);
        String file4 = "/Users/zhangyuhan/Work/WorkProject/installconstruction/等效锥度/Wheel_1che1zhou_R_12003.txt";
        List<List<Double>> list4 = new ArrayList<>();
        readData(file4, list4);

        System.out.println(list1.size());
        System.out.println(list2.size());
        System.out.println(list3.size());
        System.out.println(list4.size());

//        String filePath = "";
//        Double a = 0.025;
//        demo1.main_conicity(10, list1.toArray(), list2.toArray(), list3.toArray(), list4.toArray(), a, filePath);
    }

    public static void readData(String file, List<List<Double>> list) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String text;
            String[] split;
            List<Double> point;
            while ((text = bufferedReader.readLine()) != null) {
                split = text.split("\\t");
                point = new ArrayList<>();
                point.add(Double.parseDouble(split[0]));
                point.add(Double.parseDouble(split[1]));
                list.add(point);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

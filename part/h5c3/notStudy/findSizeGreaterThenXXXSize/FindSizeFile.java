import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FindSizeFile {

    public static void main(String[] args) {
        String path = "D:\\Work\\Study";
        List<String> list = new ArrayList<>();
        getFilePathList(path, list);
        System.out.println(list);
    }

    public static List<String> getFilePathList(String path, List<String> list) {
        File file = new File(path);
        if (file.isDirectory()) {
            for (File tempFile : file.listFiles()) {
                if (tempFile.isDirectory()) {
                    getFilePathList(tempFile.getAbsolutePath(), list);
                } else if (tempFile.length() > 50 * 1024 * 1024) {
                    list.add(tempFile.getAbsolutePath());
                }
            }
        } else if (file.length() > 25 * 1024) {
            list.add(file.getAbsolutePath());
        }
        return list;
    }

} 
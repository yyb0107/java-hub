import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bingo
 * @title: NoThreadDemo
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/16  17:39
 */
public class ThreadDemo {
    /**
     * 将copy文件的流程拆分为两个子集合，然后分给两个线程同时进行文件的复制
     * @param args
     */
    public static void main(String[] args) {
        List<File> files = new ArrayList<>();
        String targetDir = "";
        if (!files.isEmpty()) {
            List<File> files1 = files.subList(0, files.size() / 2);
            List<File> files2 = files.subList(files.size() / 2, files.size());
            new Thread(() -> {
                FileCopy fileCopy = new FileCopy(files1, targetDir);
                fileCopy.copy();
            }).start();
            new Thread(() -> {
                FileCopy fileCopy = new FileCopy(files2, targetDir);
                fileCopy.copy();
            }).start();
        }


    }
}

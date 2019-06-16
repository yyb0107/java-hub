import java.io.*;
import java.util.List;

public class FileCopy {
        List<File> files;
        String targetDir;

        public FileCopy(List<File> files,String targetDir) {
            this.files = files;
            this.targetDir = targetDir;
        }

        public void copy(){
            for(File file:files){
                copy(file, new File(targetDir,file.getName()));
            }
        }

        protected void copy(File source, File target) {
            try (FileInputStream fis = new FileInputStream(source);
                 FileOutputStream fos = new FileOutputStream(target)) {
                byte[] buf = new byte[1024];
                int len = fis.read(buf);
                while (len != -1) {
                    fos.write(buf, 0, len);
                    len = fis.read(buf);
                }
                fos.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
        }
    }
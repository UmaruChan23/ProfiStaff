import java.io.*;
import java.util.*;

public class FindTextFiles {
    private static final ArrayList<File> listWithFileNames = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        getListFiles("C:\\backet");
        File destFile = new File("C:\\backet\\global.txt");
        OutputStream os = new FileOutputStream(destFile, false);
        os.close();
        listWithFileNames.forEach( fil -> {
            if (fil.getName().contains(".txt") && !fil.getName().equals(destFile.getName())) {
                try {
                    addToFile(fil, destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void addToFile(File source, File dest) throws IOException {
        try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(dest, true)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

    public static void getListFiles(String rootDir) {
        File files = new File(rootDir);
        for (File tempFile : Objects.requireNonNull(files.listFiles())) {
            if (tempFile.isFile()) {
                listWithFileNames.add(tempFile);
            } else if (tempFile.isDirectory()) {
                getListFiles(tempFile.getAbsolutePath());
            }
        }
        listWithFileNames.sort(Comparator.comparing(File::getName));
    }
}
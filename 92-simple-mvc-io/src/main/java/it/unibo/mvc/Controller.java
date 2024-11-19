package it.unibo.mvc;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Application controller. Performs the I/O.
 */
public class Controller {

    private static final String USER_HOME = System.getProperty("user.home");
    private static final String OS_PATH_SEPARATOR = System.getProperty("file.separator");
    private static final String DEFAULT_FILE = "output.txt";
    private File file = new File(String.join(OS_PATH_SEPARATOR, USER_HOME, DEFAULT_FILE));

    public void setFile(final String path, final String... subpaths) {
        String filePath = String.join(OS_PATH_SEPARATOR, subpaths);
        filePath = String.join(OS_PATH_SEPARATOR, USER_HOME, path, filePath);
        this.setFile(new File(filePath));
    }

    public void setFile(final File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public String getFilePath() {
        return this.file.getPath();
    }

    public void saveToFile(final String content) throws IOException {
        try (PrintStream ps = new PrintStream(this.file, StandardCharsets.UTF_8)) {
            ps.println(content);
        }
    }
}

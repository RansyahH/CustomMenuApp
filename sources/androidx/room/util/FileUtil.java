package androidx.room.util;

import android.os.Build;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class FileUtil {
    public static void copy(ReadableByteChannel input, FileChannel output) throws IOException {
        try {
            if (Build.VERSION.SDK_INT <= 23) {
                InputStream inputStream = Channels.newInputStream(input);
                OutputStream outputStream = Channels.newOutputStream(output);
                byte[] buffer = new byte[4096];
                while (true) {
                    int read = inputStream.read(buffer);
                    int length = read;
                    if (read <= 0) {
                        break;
                    }
                    outputStream.write(buffer, 0, length);
                }
            } else {
                output.transferFrom(input, 0, Long.MAX_VALUE);
            }
            output.force(false);
        } finally {
            input.close();
            output.close();
        }
    }

    private FileUtil() {
    }
}

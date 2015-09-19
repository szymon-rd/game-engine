package pl.jacadev.engine.graphics.utility;

import java.io.*;

/**
 * @author Jaca777
 *         Created 18.12.14 at 16:11
 */
public class Streams {
    /**
     * Turns a stream of bytes into a String.
     * @param inputStream Input stream to be turned into string.
     * @return String
     */
    public static String streamToString(InputStream inputStream) {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null){
                content.append(line);
                content.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content.toString();
    }

    /**
     * Reads all bytes of the inputStream.
     * @param inputStream InputStream to be read.
     * @return Bytes of the inputStream.
     */
    public static byte[] streamToBytes(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        try {
            byte[] bytes = new byte[1024];
            while(inputStream.read(bytes) != -1){
                byteArrayOutputStream.write(bytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteArrayOutputStream.toByteArray();
    }
}

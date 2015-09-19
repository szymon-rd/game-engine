package pl.jacadev.game.resmanagement.manageables.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;

/**
 * @author Jaca777
 *         Created 29.01.15 at 21:28
 */
public abstract class DataFileProcessor {

    protected static final String SEPARATOR = ": ";

    public DataFileProcessor(InputStream properties) throws IOException, ParseException {
        read(properties);
    }

    private void read(InputStream stream) throws ParseException, IOException {
        if (stream == null) throw new IOException("Stream is null");
        else {
            String line;
            int i = 1;
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            try {
                while ((line = reader.readLine()) != null) {
                    String[] property = line.split(SEPARATOR);
                    String[] args = property[1].split(" ");
                    boolean matched = process(property[0], args, i);
                    if(!matched) throw new ParseException("Unknown target '" + property[0] + "'", i);
                    i++;
                }
            } catch (NumberFormatException e) {
                throw new ParseException("Wrong number formatting.", i);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected static void checkArgsAmount(String[] args, int exp, int line) throws ParseException {
        if (args.length != exp)
            throw new ParseException("Invalid arguments amount: " + args.length + ". Expected: " + exp, line);
    }

    protected abstract boolean process(String target, String[] args, int i) throws ParseException;
}

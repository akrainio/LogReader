import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;

public class LogReader {
    //File to be processed, Date to find, number of additional lines in output log,
    //Output file name, (Optional) pattern

    /**
     * <p>Attempts to perform a binary search for a timestamp in a file.
     * Timestamps must be at the beginning of every line in the file an
     * in ascending order. Outputs a shortened version of the input file,
     * starting at the given timestamp, and ending after given amount of
     * lines. If the given timestamp wasn't found, it will start at the
     * closest preceding timestamp instead.</p>
     * <p>args[0]: File to be processed</p>
     * <p>args[1]: Timestamp to find</p>
     * <p>args[2]: Number of additional lines in output file</p>
     * <p>args[3]: Name of output file to be generated</p>
     * <p>(optional) args[4]: Timestamp format. Uses
     * "yyyy-MM-dd HH:mm:ss.SSS Z" by default. Timestamp format must be
     * compatible with {@link java.text.SimpleDateFormat}.</p>
     * @param args
     * @throws IOException
     * @throws ParseException
     * @see java.text.SimpleDateFormat
     */
    public static void main (String args[]) throws IOException, ParseException {
        assert (args.length == 4 || args.length == 5);
        String pattern = "yyyy-MM-dd HH:mm:ss.SSS Z";
        int lineCount = Integer.parseInt(args[2]);
        if (args.length == 4) pattern= args[4];
        ArrayList<String> lines;
        try (RandomAccessFile inLog = new RandomAccessFile(args[0], "r")){
            BinaryFileSearch searcher = new BinaryFileSearch(inLog, pattern);
            lines = searcher.find(args[1], lineCount);
        }
        Path file = Paths.get(args[3]);
        Files.write(file, lines);
    }
}

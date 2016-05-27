import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BinaryFileSearch {
    private final RandomAccessFile file;
    private final SimpleDateFormat sdf;

    /**
     * Constructs a <code>BinaryFileSearch</code> using given file
     * and pattern.
     * @param file
     * @param pattern
     * @throws IOException
     * @throws ParseException
     */
    public BinaryFileSearch(RandomAccessFile file, String pattern)
            throws IOException, ParseException {
        this.file = file;
        this.sdf = new SimpleDateFormat(pattern);
    }

    /**
     * Initiates binary search on file
     * @param date
     * @param lineCount
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public ArrayList<String> find(String date, int lineCount) throws IOException, ParseException {
        State status = new State(0, file.length(), date, file, sdf);
        binarySearch(status);
        ArrayList<String> lineList = new ArrayList<>();
        //Adding first line to list, since for loop would be unable to access it
        lineList.add(status.getMidString());
        for (int i = 1; i < lineCount; ++i) {
            String line = file.readLine();
            if (line == null) {
                System.err.println("Requested output file length is larger the remaining file");
                return lineList;
            }
            lineList.add(line);
        }
        return lineList;
    }

    /**
     * Recursive function initiated by <code>find()</code> that
     * performs a binary search.
     * @param status
     * @throws IOException
     * @throws ParseException
     */
    private void binarySearch(State status) throws IOException, ParseException {
        if (status.noResult) {
            System.err.println("Time stamp [" + status.getKeyString() + "] not present in file");
            return;
        }
        switch (compare(status)){
            case 0:
                //Found key
                break;
            case 1:
                //Larger than key
                status.setNewMax();
                binarySearch(status);
                break;
            case -1:
                //Smaller than key
                status.setNewMin();
                binarySearch(status);
                break;
            default:
                System.err.println("BinaryFileSearch:searchFor:" +
                        " unexpected return from compare");
        }
    }

    /**
     * Compares given state's mid to key
     * @param status
     * @return 1 if mid is after key, 0 if mid is equal to key,
     * and -1 if mid is before key
     */
    private int compare(State status) {
        if (status.getMid().after(status.getKey())) return 1;
        if (status.getMid().before(status.getKey())) return -1;
        return 0;
    }
}

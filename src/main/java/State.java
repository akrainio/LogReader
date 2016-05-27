import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Small class used to hold and modify information
//concerning current state of the binary search.
class State {
    boolean noResult;
    private long min;
    private long mid;
    private long max;
    private Date midDate;
    private String midString;
    private final String keyString;
    private final Date key;
    private final RandomAccessFile file;
    private final SimpleDateFormat sdf;

    /**
     * Constructs <code>State</code>. Only one instance
     * should be created for each search.
     * @param min Starting floor of binary search
     * @param max Starting ceiling of binary search
     * @param key Timestamp being searched for
     * @param file File being searched
     * @param sdf Format of timestamp
     * @throws IOException
     * @throws ParseException
     */
    public State(long min, long max, String key, RandomAccessFile file, SimpleDateFormat sdf)
            throws IOException, ParseException {
        this.file = file;
        this.sdf = sdf;
        this.min = min;
        this.max = max;
        resetMid();
        keyString = key;
        this.key = stringToDate(key);
        noResult = false;
    }
    /**
     * Gets date from a string according to sdf's pattern
     * @param line String from which date is extracted. String
     *             must start with a timestamp.
     * @return <code>Date</code> extracted from the timestamp
     *         in the string.
     * @throws ParseException
     */
    private Date stringToDate(String line) throws ParseException {
        return sdf.parse(line);
    }

    /**
     * Gets first complete line in a file after given index
     * @param index Location from which to start looking.
     * @return First complete line after index.
     * @throws IOException
     */
    private String getLine(long index) throws IOException {
        assert file.length() > index;
        file.seek(index);
        file.readLine();
        return file.readLine();
    }

    /**
     * Sets mid to be halfway between min and max
     * @throws IOException
     * @throws ParseException
     */
    private void resetMid() throws IOException, ParseException {
        long prevMid = mid;
        mid = min + (max - min) / 2;
        if (prevMid == mid) noResult = true;
        midString = getLine(mid);
        midDate = stringToDate(midString);
    }

    /**
     * Sets min to mid, fixes mid
     * @throws IOException
     * @throws ParseException
     */
    public void setNewMin() throws IOException, ParseException {
        min = mid;
        resetMid();
    }

    /**
     * Sets max to mid, fixes mid
     * @throws IOException
     * @throws ParseException
     */
    public void setNewMax() throws IOException, ParseException {
        max = mid;
        resetMid();
    }

    public String getKeyString() { return keyString; }
    public Date getMid() {
        return midDate;
    }
    public String getMidString(){
        return midString;
    }
    public Date getKey() { return key; }
}
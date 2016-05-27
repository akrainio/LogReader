# LogReader
Attempts to perform a binary search for a timestamp in a file.
Timestamps must be at the beginning of every line in the file an
in ascending order. Outputs a shortened version of the input file,
starting at the given timestamp, and ending after given amount of
lines. If the given timestamp wasn't found, it will start at the
closest preceding timestamp instead.

Code entirely written by me.

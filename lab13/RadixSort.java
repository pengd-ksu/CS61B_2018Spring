/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    private static final int RADIX = 256;

    public static String[] sort(String[] asciis) {
        int maxLength = Integer.MIN_VALUE;
        for (String a : asciis) {
            maxLength = a.length() > maxLength ? a.length() : maxLength;
        }
        String[] sorted = new String[asciis.length];
        System.arraycopy(asciis, 0, sorted, 0, asciis.length);
        for (int d = maxLength - 1; d >= 0; d -= 1) {
            sortHelperLSD(sorted, d);
        }
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        int[] counts = new int[RADIX];
        for (String s : asciis) {
            if (s.length() - 1 < index) {
                // When s should be padded on the left
                counts[0] += 1;
            } else {
                counts[s.charAt(index)] += 1;
            }
        }

        int[] starts = new int[RADIX];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        String[] sorted = new String[asciis.length];
        for (String s : asciis) {
            int place;
            if (s.length() - 1 < index) {
                place = starts[0];
                starts[0] += 1;
            } else {
                place = starts[s.charAt(index)];
                starts[s.charAt(index)] += 1;
            }
            sorted[place] = s;
        }
        for (int i = 0; i < sorted.length; i++) {
            asciis[i] = sorted[i];
        }
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    /**
     * MSD code is from:
     * https://github.com/airbust/CS61B/blob/4e709a7dd26a8d6c27e83be67874e0cd35e294cb/lab13/RadixSort.java
     */
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        if (end - start <= 1) {
            return;
        }
        int maxLength = Integer.MIN_VALUE;
        for (int i = start; i < end; i += 1) {
            maxLength = maxLength > asciis[i].length()
                    ? maxLength : asciis[i].length();
        }
        int[] counts = new int[RADIX];
        for (int i = start; i < end; i += 1) {
            if (asciis[i].length() - 1 < index) {
                counts[0] += 1;
            } else {
                counts[asciis[i].charAt(index)] += 1;
            }
        }

        int[] starts = new int[RADIX];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        String[] sorted = new String[asciis.length];
        for (int i = start; i < end; i += 1) {
            int place;
            if (asciis[i].length() - 1 < index) {
                place = starts[0];
                starts[0] += 1;
            } else {
                place = starts[asciis[i].charAt(index)];
                starts[asciis[i].charAt(index)] += 1;
            }
            sorted[place] = asciis[i];
        }
        for (int i = start; i < end; i += 1) {
            asciis[i] = sorted[i - start];
        }

        int i = start;
        while (i < end) {
            int j = i + 1;
            while (j < asciis.length &&
                    asciis[i].charAt(index) == asciis[j].charAt(index)) {
                j += 1;
            }
            sortHelperMSD(asciis, i, j, index + 1);
            i = j;
        }
    }

    public static String[] sortMSD(String[] asciis) {
        String[] sorted = new String[asciis.length];
        System.arraycopy(asciis, 0, sorted, 0, asciis.length);
        sortHelperMSD(sorted, 0, asciis.length, 0);
        return sorted;
    }

    public static void main(String[] args) {
        String[] str = new String[]{"hello", "darkness", "my",
                "old", "friend"};
        str = sort(str);
        //    str = sortMSD(str);
        for (String s : str) {
            System.out.println(s);
        }
    }
}

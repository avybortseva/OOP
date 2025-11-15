package ru.nsu.g.a.vybortseva.substring;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for searching substrings in large files using the Rabin-Karp algorithm.
 */
public class SubstringSearcher {
    private static final int PRIME = 101;
    private static final int BASE = 256;
    private static final int BUFFER_SIZE = 8192;

    /**
     * Finds all occurrences of a substring in a file using Rabin-Karp algorithm.
     */
    public static List<Integer> find(String filename, String substring) {
        List<Integer> indices = new ArrayList<>();

        if (substring.isEmpty()) {
            System.out.print("Substring is empty: ");
            return indices;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Files.newInputStream(Paths.get(filename)),
                        StandardCharsets.UTF_8), BUFFER_SIZE
        )) {

            return rabinKarpSearch(reader, substring);

        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }

    private static List<Integer> rabinKarpSearch(BufferedReader reader, String pattern)
            throws IOException {
        List<Integer> indices = new ArrayList<>();
        int m = pattern.length();

        if (m == 0) {
            return indices;
        }

        long patternHash = computeHash(pattern, 0, m);
        long h = computePower(m);

        char[] window = new char[m];
        int totalCharsRead = 0;
        int charsInWindow = 0;
        long windowHash = 0;

        char[] readBuffer = new char[BUFFER_SIZE];
        int bufferPos = 0;
        int bufferLength = 0;

        while (true) {
            if (bufferPos >= bufferLength) {
                bufferLength = reader.read(readBuffer);
                if (bufferLength == -1) {
                    break;
                }
                bufferPos = 0;
            }

            char currentChar = readBuffer[bufferPos++];

            if (charsInWindow < m) {
                window[charsInWindow] = currentChar;
                charsInWindow++;

                if (charsInWindow == m) {
                    windowHash = computeHash(new String(window), 0, m);
                }
            } else {
                char oldChar = window[0];

                System.arraycopy(window, 1, window, 0, m - 1);
                window[m - 1] = currentChar;

                windowHash = updateHash(windowHash, oldChar, currentChar, h, m);
            }

            if (charsInWindow == m) {
                if (windowHash == patternHash) {
                    if (arrayEquals(window, pattern)) {
                        indices.add(totalCharsRead - m + 1);
                    }
                }
            }

            totalCharsRead++;
        }

        return indices;
    }

    private static long computeHash(String str, int start, int length) {
        long hash = 0;
        for (int i = 0; i < length; i++) {
            hash = (BASE * hash + str.charAt(start + i)) % PRIME;
        }
        return hash;
    }

    private static long computePower(int m) {
        long h = 1;
        for (int i = 0; i < m - 1; i++) {
            h = (h * BASE) % PRIME;
        }
        return h;
    }

    private static long updateHash(long oldHash, char oldChar, char newChar, long h, int m) {
        long hash = (oldHash - oldChar * h) % PRIME;
        hash = (hash * BASE + newChar) % PRIME;

        if (hash < 0) {
            hash += PRIME;
        }
        return hash;
    }

    private static boolean arrayEquals(char[] window, String pattern) {
        for (int i = 0; i < pattern.length(); i++) {
            if (window[i] != pattern.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Main method for testing the substring search functionality.
     */
    public static void main(String[] args) {
        List<Integer> result = SubstringSearcher.find("input.txt", "бра");
        System.out.println(result);

        System.out.println(SubstringSearcher.find("input.txt", "абра"));
        System.out.println(SubstringSearcher.find("input.txt", "xyz"));
        System.out.println(SubstringSearcher.find("input.txt", ""));
    }
}
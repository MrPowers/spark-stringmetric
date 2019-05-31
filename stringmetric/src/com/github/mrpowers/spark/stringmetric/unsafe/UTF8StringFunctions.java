package com.github.mrpowers.spark.stringmetric.unsafe;

import org.apache.spark.unsafe.types.UTF8String;

public class UTF8StringFunctions {
    // Wish these methods weren't private in UTF8String:
    // - bytesOfCodePointInUTF8
    // - numBytesForFirstByte
    // https://github.com/apache/spark/blob/master/common/unsafe/src/main/java/org/apache/spark/unsafe/types/UTF8String.java
    private static byte[] bytesOfCodePointInUTF8 = {
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // 0x00..0x0F
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // 0x10..0x1F
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // 0x20..0x2F
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // 0x30..0x3F
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // 0x40..0x4F
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // 0x50..0x5F
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // 0x60..0x6F
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // 0x70..0x7F
        // Continuation bytes cannot appear as the first byte
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 0x80..0x8F
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 0x90..0x9F
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 0xA0..0xAF
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 0xB0..0xBF
        0, 0, // 0xC0..0xC1 - disallowed in UTF-8
        2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, // 0xC2..0xCF
        2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, // 0xD0..0xDF
        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, // 0xE0..0xEF
        4, 4, 4, 4, 4, // 0xF0..0xF4
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 // 0xF5..0xFF - disallowed in UTF-8
    };

    /**
     * Returns the number of bytes for a code point with the first byte as `b`
     * @param b The first byte of a code point
     */
    private static int numBytesForFirstByte(final byte b) {
        final int offset = b & 0xFF;
        byte numBytes = bytesOfCodePointInUTF8[offset];
        return (numBytes == 0) ? 1: numBytes; // Skip the first byte disallowed in UTF-8
    }

    // Adapted from org.apache.commons.text.similarity.HammingDistance
    public static int hammingDistance(UTF8String left, UTF8String right) {
        int n = left.numChars();
        if (n != right.numChars()) {
            throw new java.lang.IllegalArgumentException(
                "Hamming distance is only defined for strings of same length!"
            );
        }

        int distance = 0;
        byte[] leftBytes = left.getBytes();
        byte[] rightBytes = right.getBytes();
        
        int leftIdx = 0;
        int rightIdx = 0;
        int c = 0;
        while (c < n) {
            int leftCharSize = numBytesForFirstByte(leftBytes[leftIdx]);
            int rightCharSize = numBytesForFirstByte(rightBytes[rightIdx]);

            if (leftCharSize != rightCharSize) {
                distance++;
            } else {
                for (int i = 0; i < leftCharSize; i++) {
                    if (leftBytes[i + leftIdx] != rightBytes[i + rightIdx]) {
                        distance++;
                        break;
                    }
                }
            }

            leftIdx += leftCharSize;
            rightIdx += rightCharSize;
            c++; // would rather be writing this than Java!
        }
        return distance;
    }
}

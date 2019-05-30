package com.github.mrpowers.spark.stringmetric.unsafe;

import org.apache.spark.unsafe.types.UTF8String;

public class UTF8StringFunctions {
    // Adopted from org.apache.commons.text.similarity.HammingDistance
    public static int hammingDistance(UTF8String left, UTF8String right) {
        if (left.numChars() != right.numChars()) {
            throw new java.lang.IllegalArgumentException(
                "Hamming distance is only defined for strings of same length!"
            )
        }

        int distance = 0;
        byte[] leftBytes = left.getBytes();
        byte[] rightBytes = right.getBytes();

        for (int i = 0; i < left.numBytes(); i++) {
            if (leftBytes[i] != rightBytes[i]) {
                distance++;
            }
        }
        return distance;
    }
}

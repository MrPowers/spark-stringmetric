package com.github.mrpowers.spark.stringmetric.unsafe;

import org.apache.spark.unsafe.types.UTF8String;

public class UTF8StringFunctions {

    public static String funifyString(UTF8String str) {
        return str + " is fun!";
    }

}

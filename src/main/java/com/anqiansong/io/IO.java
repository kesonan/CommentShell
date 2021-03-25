/*
 * MIT License
 *
 * Copyright (c) 2021 anqiansong
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

package com.anqiansong.io;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;

public class IO {
    public static String read(InputStream in) {
        try {
            InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
            LineNumberReader line = new LineNumberReader(reader);
            StringBuilder buffer = new StringBuilder();
            String str;
            while ((str = line.readLine()) != null) {
                buffer.append(str).append("\r\n");
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}

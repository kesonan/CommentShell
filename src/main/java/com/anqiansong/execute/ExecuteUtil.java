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

package com.anqiansong.execute;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.anqiansong.notification.Notification;

import java.util.Arrays;
import java.util.List;

public class ExecuteUtil {

    public static List<String> wrapCmd(Project project, String cmd) {
        if (SystemInfo.isWindows) {
            return Arrays.asList("cmd.exe", "/c", cmd);
        } else if (SystemInfo.isMac || SystemInfo.isLinux || SystemInfo.isUnix) {
            String shell = System.getenv("SHELL");
            if (StringUtil.isEmptyOrSpaces(shell)) {
                shell = "sh";
            }
            return Arrays.asList(shell, "-c", cmd);
        } else {
            Notification.getInstance().error(project, "unsupported os: " + SystemInfo.OS_NAME);
            return null;
        }
    }
}

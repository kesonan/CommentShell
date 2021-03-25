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

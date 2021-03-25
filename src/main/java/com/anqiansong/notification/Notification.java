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

package com.anqiansong.notification;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

public class Notification {
    private static Notification notification;
    private final NotificationGroup POP = new NotificationGroup("x-pop", NotificationDisplayType.BALLOON, true);
    private final NotificationGroup LOG = new NotificationGroup("goctl-log", NotificationDisplayType.NONE, true);

    private Notification() {
    }

    public static Notification getInstance() {
        if (notification == null) {
            notification = new Notification();
        }
        return notification;
    }

    public void notify(Project project, String content) {
        if (project == null) {
            return;
        }
        final com.intellij.notification.Notification notification = POP.createNotification(unWrapMsg(content), NotificationType.INFORMATION);
        notification.notify(project);
    }

    public void log(Project project, String content) {
        if (project == null) {
            System.out.println(content);
            return;
        }
        final com.intellij.notification.Notification notification = LOG.createNotification(unWrapMsg(content), NotificationType.INFORMATION);
        notification.notify(project);
    }

    public void error(Project project, String content) {
        if (project == null) {
            System.err.println(content);
            return;
        }
        final com.intellij.notification.Notification notification = POP.createNotification(unWrapMsg(content), NotificationType.ERROR);
        notification.notify(project);
    }

    public void warning(Project project, String content) {
        if (project == null) {
            System.out.println(content);
            return;
        }
        final com.intellij.notification.Notification notification = LOG.createNotification(unWrapMsg(content), NotificationType.WARNING);
        notification.notify(project);
    }

    private String unWrapMsg(String msg) {
        if (msg.startsWith("info-")) {
            return msg.replaceFirst("info-", "");
        }
        if (msg.startsWith("error-")) {
            return msg.replaceFirst("error-", "");
        }
        if (msg.startsWith("warning-")) {
            return msg.replaceFirst("warning-", "");
        }
        return msg;
    }
}

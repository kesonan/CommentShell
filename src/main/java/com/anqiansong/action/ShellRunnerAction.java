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
package com.anqiansong.action;

import com.intellij.execution.RunContentExecutor;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.BaseOSProcessHandler;
import com.intellij.execution.process.KillableColoredProcessHandler;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.anqiansong.env.Env;
import com.anqiansong.execute.ExecuteUtil;
import com.anqiansong.notification.Notification;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShellRunnerAction extends AnAction {
    /**
     * $DIR describes the path of the file, eg: /foo/bar is the dir of /foo/bar/x.txt on linux,
     * its end without file separator
     */
    public static final String $DIR = "%DIR%";

    /**
     * $FILENAME describes a plain text of the file,
     * such as /foo/bar/x.txt
     */
    public static final String $FILENAME = "%FILENAME%";

    /**
     * $DOLLAR describes a dollar sign -> $
     */
    public static final String $PERCENT_SIGN = "%PERCENT_SIGN%";

    /**
     * $BASE describes the base name of the file,
     * eg: x.txt is the base name of /foo/bar/x.txt
     */
    public static final String $BASE = "%BASE%";

    private static final String PERCENT_SIGN = "%";
    private final Env env;

    public ShellRunnerAction(Env env) {
        super("Run " + (env.getCmd().length() > 20 ? env.getCmd().substring(0, 20)+"..." : env.getCmd()), "Run x command", AllIcons.RunConfigurations.TestState.Run);
        this.env = env;
    }


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }
        try {
            PsiElement element = this.env.getElement();
            VirtualFile virtualFile = element.getContainingFile().getVirtualFile();
            String filename = virtualFile.getPath();
            String dir = virtualFile.getParent().getPath();
            String base = virtualFile.getName();
            String cmd = this.env.getCmd().replaceAll($DIR, dir).replaceAll($FILENAME, filename).replaceAll($BASE, base).replaceAll($PERCENT_SIGN, PERCENT_SIGN).trim();
            List<String> wrapCmd = ExecuteUtil.wrapCmd(project, cmd);
            if (wrapCmd == null) {
                return;
            }

            GeneralCommandLine commandLine = new GeneralCommandLine(wrapCmd);
            commandLine.withCharset(CharsetToolkit.UTF8_CHARSET);
            Notification.getInstance().log(project, commandLine.getCommandLineString());
            commandLine.setWorkDirectory(dir);
            BaseOSProcessHandler outputHandler = new KillableColoredProcessHandler(commandLine);
            RunContentExecutor runContentExecutor = new RunContentExecutor(project, outputHandler)
                    .withTitle(base)
                    .withAfterCompletion(() -> LocalFileSystem.getInstance().refreshAndFindFileByPath(dir))
                    .withActivateToolWindow(true);
            Disposer.register(project, runContentExecutor);
            runContentExecutor.run();
        } catch (Exception ex) {
            Notification.getInstance().error(project, ex.getMessage());
        }
    }

}

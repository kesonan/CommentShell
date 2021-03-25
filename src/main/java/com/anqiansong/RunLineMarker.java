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

package com.anqiansong;

import com.anqiansong.action.ShellRunnerAction;
import com.anqiansong.env.Env;
import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.impl.source.tree.PsiCommentImpl;
import com.intellij.psi.impl.source.xml.XmlCommentImpl;
import com.intellij.psi.impl.source.xml.XmlTokenImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * RunLineMarker supports to create a marker at line on left side,
 * and you can click it to run the specified command
 */
public class RunLineMarker extends RunLineMarkerContributor {
    public static final String X_FLAG = "x:generate";
    private static final String X_FLAG_SLASH = "//x:generate";
    private static final String X_FLAG_SHARP = "#x:generate";
    private static final String X_FLAG_TAG_START = "<!--x:generate";
    private static final String X_FLAG_TAG_END = "-->";
    private static final String X_FLAG_SQL = "--x:generate";

    private Env prepare(PsiElement e, String content) {
        Env env = new Env();
        env.setCmd(content);
        env.setElement(e);
        return env;
    }

    @Override
    public @Nullable Info getInfo(@NotNull PsiElement e) {
        if (!e.isValid()) {
            return null;
        }

        String cmd = getXCommand(e);
        if (cmd == null) {
            return null;
        }

        Env env = prepare(e, cmd);
        ShellRunnerAction generateAction = new ShellRunnerAction(env);
        return new Info(AllIcons.RunConfigurations.TestState.Run, psiElement -> "generate", generateAction);
    }

    private static String getExecuteContent(String text) {
        if (text.contains("\n") || text.contains("\r")) {
            return null;
        }

        if (text.startsWith(X_FLAG_SLASH)) {
            return text.replaceFirst(X_FLAG_SLASH, "");
        } else if (text.startsWith(X_FLAG_SHARP)) {
            return text.replaceFirst(X_FLAG_SHARP, "");
        } else if (text.startsWith(X_FLAG_TAG_START) && text.endsWith(X_FLAG_TAG_END)) {
            text = text.replaceFirst(X_FLAG_TAG_START, "");
            int index = text.lastIndexOf(X_FLAG_TAG_END);
            return text.substring(0, index);
        } else if (text.startsWith(X_FLAG_SQL)) {
            return text.replaceFirst(X_FLAG_SQL, "");
        } else if (text.startsWith(X_FLAG)) {
            return text.replaceFirst(X_FLAG, "");
        } else {
            return null;
        }
    }

    public static String getXCommand(PsiElement e) {
        String text;
        if (e instanceof PsiCommentImpl) {
            text = e.getText();
        } else if (e instanceof XmlTokenImpl) {
            PsiElement parent = e.getParent();
            if (parent == null) {
                return null;
            }

            if (!(parent instanceof XmlCommentImpl)) {
                return null;
            }

            text = e.getText();
            if (!text.startsWith(X_FLAG)) {
                return null;
            }
        } else if (e instanceof LeafPsiElement) {
            text = e.getText();
        } else {
            return null;
        }

        if (text == null) {
            return null;
        }

        PsiElement parent = e.getParent();
        if (!parent.isValid()) {
            return null;
        }

        String cmd = getExecuteContent(text);
        if (StringUtil.isEmpty(cmd)) {
            return null;
        }

        char c = cmd.charAt(0);
        if (!StringUtil.isWhiteSpace(c)) {
            return null;
        }

        return cmd.trim();
    }
}


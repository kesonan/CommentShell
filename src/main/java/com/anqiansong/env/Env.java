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

package com.anqiansong.env;

import com.intellij.psi.PsiElement;

public class Env {
    private PsiElement element;
    private String cmd;

    public PsiElement getElement() {
        return element;
    }

    public void setElement(PsiElement element) {
        this.element = element;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}

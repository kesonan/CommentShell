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

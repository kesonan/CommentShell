package com.anqiansong.annotator;

import com.anqiansong.RunLineMarker;
import com.intellij.lang.annotation.AnnotationBuilder;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

// XAnnotator overrides the highlight color of the keyword x:generate
public class CommentShellAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        String cmd = RunLineMarker.getXCommand(element);
        if (cmd == null) {
            return;
        }

        int index = element.getText().indexOf(RunLineMarker.X_FLAG);
        TextRange r = element.getTextRange();
        int start = r.getStartOffset() + index;
        int end = start + RunLineMarker.X_FLAG.length();
        if (element.getTextRange().containsRange(start, end)) {
            TextRange range = new TextRange(start, end);
            AnnotationBuilder builder = holder.newSilentAnnotation(HighlightSeverity.INFORMATION);
            builder.textAttributes(DefaultLanguageHighlighterColors.KEYWORD)
                    .range(range)
                    .create();
        }
    }

}

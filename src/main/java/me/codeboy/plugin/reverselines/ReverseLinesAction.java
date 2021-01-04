package me.codeboy.plugin.reverselines;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;

/**
 * reverse lines
 * Created by yuedong.li on 2018/4/5.
 */
public class ReverseLinesAction extends AnAction {

    public ReverseLinesAction() {
        super("Reverse Lines");
    }

    @Override
    public void update(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor != null) {
            e.getPresentation().setEnabled(true);
        } else {
            e.getPresentation().setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        Project project = event.getData(PlatformDataKeys.PROJECT);
        if (editor != null) {
            SelectionModel selectionModel = editor.getSelectionModel();
            Document document = editor.getDocument();
            String selectText = selectionModel.getSelectedText();
            if (selectText == null || !selectText.contains("\n")) {
                return;
            }

            int startLine = -1;
            int endLine = -1;
            int startOffset = selectionModel.getSelectionStart();
            int endOffset = selectionModel.getSelectionEnd();

            String source = document.getText();
            String[] lines = source.split("\\n");
            int len = 0;
            for (int i = 0; i < lines.length; i++) {
                if (startOffset >= len) {
                    startLine = i;
                }
                if (endOffset >= len) {
                    endLine = i;
                }
                if (len > startOffset && len > endOffset) {
                    break;
                }
                len = len + lines[i].length() + 1;
            }

            //only one line was selected
            if (startLine == endLine) {
                return;
            }

            int middleLine = (startLine + endLine) / 2;
            for (int i = startLine; i <= middleLine; i++) {
                int matchPosition = startLine + endLine - i;
                String tmp = lines[i];
                lines[i] = lines[matchPosition];
                lines[matchPosition] = tmp;
            }

            StringBuilder result = new StringBuilder(source.length());
            for (String line : lines) {
                result.append("\n");
                result.append(line);
            }

            String resultTmp = result.toString();
            if (resultTmp.length() > 0) {
                resultTmp = resultTmp.substring(1);
            }

            final String resultStr = resultTmp;
            WriteCommandAction.runWriteCommandAction(project, () ->
                    document.replaceString(0, source.length(), resultStr)
            );
            selectionModel.removeSelection();
        }
    }
}
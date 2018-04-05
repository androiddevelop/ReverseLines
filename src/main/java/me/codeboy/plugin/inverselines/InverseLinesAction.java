package me.codeboy.plugin.inverselines;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.project.Project;

/**
 * inverse lines
 * Created by yuedong.li on 2018/4/5.
 */
public class InverseLinesAction extends AnAction {

    public InverseLinesAction() {
        super("InverseLines");
    }

    @Override
    public void update(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor != null)
            e.getPresentation().setEnabled(true);
        else
            e.getPresentation().setEnabled(false);

    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        Project project = event.getData(PlatformDataKeys.PROJECT);
        if (editor != null) {
            SelectionModel selectionModel = editor.getSelectionModel();
            Document document = editor.getDocument();
            VisualPosition startVisualPosition = selectionModel.getSelectionStartPosition();
            VisualPosition endVisualPosition = selectionModel.getSelectionEndPosition();
            //only one row selected
            if (startVisualPosition == null || endVisualPosition == null || startVisualPosition.line == endVisualPosition.line) {
                return;
            }

            //
            int startLine = startVisualPosition.line;
            int endLine = endVisualPosition.line;
            String source = document.getText();
            String[] lines = source.split("\\n");
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
        }
    }
}
import java.util.Stack;

public class StackTextEditor {
    private StringBuilder currentText;
    private Stack<String> undoStack;
    private Stack<String> redoStack;

    public StackTextEditor() {
        currentText = new StringBuilder();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }
    public void insertText(String text) {
        undoStack.push(currentText.toString());
        currentText.append(text);
        redoStack.clear();
    }
    public void deleteCharacters(int count) {
        if (count <= 0 || count > currentText.length())
            return;
        undoStack.push(currentText.toString());
        int newLength = currentText.length() - count;
        currentText.setLength(newLength);
        redoStack.clear();
    }
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    public void undo() {
        if (canUndo()) {
            redoStack.push(currentText.toString());
            String previousState = undoStack.pop();
            currentText = new StringBuilder(previousState);
        }
    }
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    public void redo() {
        if (canRedo()) {
            undoStack.push(currentText.toString());
            String nextState = redoStack.pop();
            currentText = new StringBuilder(nextState);
        }
    }
    public String getCurrentText() {
        return currentText.toString();
    }
    public static void main(String[] args) {
        StackTextEditor editor = new StackTextEditor();
        editor.insertText("Hello ");
        editor.insertText("World!");
        System.out.println(editor.getCurrentText()); 
        editor.deleteCharacters(6);
        System.out.println(editor.getCurrentText());
        editor.undo();
        System.out.println(editor.getCurrentText()); 
        editor.redo();
        System.out.println(editor.getCurrentText()); 
    }
}

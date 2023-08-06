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
        // Save the current state for undo
        undoStack.push(currentText.toString());

        // Perform the insert operation
        currentText.append(text);

        // Clear the redo stack since the redo history is invalid after new edits
        redoStack.clear();
    }

    public void deleteCharacters(int count) {
        if (count <= 0 || count > currentText.length())
            return;

        // Save the current state for undo
        undoStack.push(currentText.toString());

        // Perform the delete operation
        int newLength = currentText.length() - count;
        currentText.setLength(newLength);

        // Clear the redo stack since the redo history is invalid after new edits
        redoStack.clear();
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public void undo() {
        if (canUndo()) {
            // Save the current state for redo
            redoStack.push(currentText.toString());

            // Retrieve the previous state and set it as the current text
            String previousState = undoStack.pop();
            currentText = new StringBuilder(previousState);
        }
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public void redo() {
        if (canRedo()) {
            // Save the current state for undo
            undoStack.push(currentText.toString());

            // Retrieve the next state and set it as the current text
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

        System.out.println(editor.getCurrentText()); // Output: "Hello World!"

        editor.deleteCharacters(6);

        System.out.println(editor.getCurrentText()); // Output: "Hello "

        editor.undo();

        System.out.println(editor.getCurrentText()); // Output: "Hello World!"

        editor.redo();

        System.out.println(editor.getCurrentText()); // Output: "Hello "
    }
}

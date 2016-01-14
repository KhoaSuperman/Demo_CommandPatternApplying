package composite.khoaha.com.demo_commandpatternapplying.invoker;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import composite.khoaha.com.demo_commandpatternapplying.command.Command;

/**
 * Created by HoangAnhKhoa on 1/14/16.
 */
public class Invoker {
    Deque<Command> queueUndo = new LinkedList<>();
    Deque<Command> queueRedo = new LinkedList<>();

    public void addCommand(Command command) {
        queueUndo.addFirst(command);
        command.execute();
    }

    public void undo() {
        if (queueUndo.size() > 0) {
            Command lastCommand = queueUndo.pollFirst();
            lastCommand.undo();

            //add to redo list
            queueRedo.addFirst(lastCommand);
        }
    }

    public void redo() {
        if(queueRedo.size() > 0){
            Command command = queueRedo.pollFirst();
            command.redo();

            //add back to undo list
            queueUndo.addFirst(command);
        }
    }
}

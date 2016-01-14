package composite.khoaha.com.demo_commandpatternapplying.invoker;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import composite.khoaha.com.demo_commandpatternapplying.command.Command;

/**
 * Created by HoangAnhKhoa on 1/14/16.
 */
public class Invoker {
    private Deque<Command> queueUndo = new LinkedList<>();
    private Deque<Command> queueRedo = new LinkedList<>();
    //for display
    ArrayList<String> historyList = new ArrayList<>();
    //for create macro
    ArrayList<Command> commands =  new ArrayList<>();

    private InvokerListener invokerListener;

    public Invoker() {
        invokerListener = new InvokerListener() {
            @Override
            public void commandAdded() {

            }

            @Override
            public void commandRemoved() {

            }
        };
    }

    public void addCommand(Command command) {
        queueUndo.addFirst(command);
        command.execute();

        //add to history text
        historyList.add(0, command.toString());

        //add to command list
        commands.add(command);

        //raise callback
        invokerListener.commandAdded();
    }

    public void undo() {
        if (queueUndo.size() > 0) {
            Command lastCommand = queueUndo.pollFirst();
            lastCommand.undo();

            //add to redo list
            queueRedo.addFirst(lastCommand);

            //remove history
            historyList.remove(0);

            //remove from command list
            commands.remove(commands.size() - 1);

            //raise callback
            invokerListener.commandRemoved();
        }
    }

    public void redo() {
        if (queueRedo.size() > 0) {
            Command command = queueRedo.pollFirst();
            command.redo();

            //add back to undo list
            queueUndo.addFirst(command);

            //re-add to history
            historyList.add(0, command.toString());

            //re-add to command list
            commands.add(command);

            //raise callback
            invokerListener.commandAdded();
        }
    }

    public void setInvokerListener(InvokerListener invokerListener) {
        this.invokerListener = invokerListener;
    }

    public ArrayList<String> getHistoryList() {
        return historyList;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public interface InvokerListener {
        void commandAdded();

        void commandRemoved();
    }
}

package composite.khoaha.com.demo_commandpatternapplying.invoker;

import java.util.ArrayList;

import composite.khoaha.com.demo_commandpatternapplying.command.Command;

/**
 * Created by HoangAnhKhoa on 1/14/16.
 */
public class Invoker {
    ArrayList<Command> commands = new ArrayList<>();

    public void addCommand(Command command){
        commands.add(command);
        command.execute();
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}

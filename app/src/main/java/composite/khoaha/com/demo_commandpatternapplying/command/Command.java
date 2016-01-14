package composite.khoaha.com.demo_commandpatternapplying.command;

import composite.khoaha.com.demo_commandpatternapplying.receiver.Receiver;

/**
 * Created by HoangAnhKhoa on 1/14/16.
 */
public interface Command {
    void execute();
    void setReceiver(Receiver receiver);
}

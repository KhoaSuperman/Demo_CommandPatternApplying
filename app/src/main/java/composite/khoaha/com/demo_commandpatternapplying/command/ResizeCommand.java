package composite.khoaha.com.demo_commandpatternapplying.command;

import java.io.Serializable;

import composite.khoaha.com.demo_commandpatternapplying.receiver.ImageReceiver;
import composite.khoaha.com.demo_commandpatternapplying.receiver.Receiver;

/**
 * Created by HoangAnhKhoa on 1/14/16.
 */
public class ResizeCommand implements Command, Serializable {

    transient ImageReceiver imageReceiver;
    int size;
    int previousSize;

    public ResizeCommand(ImageReceiver imageReceiver, int size, int prevSize) {
        this.imageReceiver = imageReceiver;
        this.size = size;
        this.previousSize = prevSize;
    }

    @Override
    public void execute() {
        imageReceiver.resize(size);
    }

    @Override
    public void undo() {
        imageReceiver.resize(previousSize);
    }

    @Override
    public void redo() {
        imageReceiver.resize(size);
    }

    @Override
    public void setReceiver(Receiver receiver) {
        this.imageReceiver = (ImageReceiver) receiver;
    }

    @Override
    public String toString() {
        return "Resize image to " + size;
    }
}

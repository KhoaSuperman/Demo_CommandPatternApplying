package composite.khoaha.com.demo_commandpatternapplying.command;

import java.io.Serializable;

import composite.khoaha.com.demo_commandpatternapplying.receiver.ImageReceiver;

/**
 * Created by HoangAnhKhoa on 1/14/16.
 */
public class ChangeAlphaCommand implements Command, Serializable {

    ImageReceiver imageReceiver;
    float alpha;

    public ChangeAlphaCommand(ImageReceiver imageReceiver, float alpha) {
        this.imageReceiver = imageReceiver;
        this.alpha = alpha;
    }

    @Override
    public void execute() {
        imageReceiver.changeAlpha(alpha);
    }
}

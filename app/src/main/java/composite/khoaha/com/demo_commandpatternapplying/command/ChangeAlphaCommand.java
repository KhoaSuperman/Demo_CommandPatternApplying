package composite.khoaha.com.demo_commandpatternapplying.command;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import composite.khoaha.com.demo_commandpatternapplying.receiver.ImageReceiver;
import composite.khoaha.com.demo_commandpatternapplying.receiver.Receiver;

/**
 * Created by HoangAnhKhoa on 1/14/16.
 */
public class ChangeAlphaCommand implements Command,
        Parcelable,//for transfer command between 2 activities
        Serializable //for persistent command to file
{

    transient ImageReceiver imageReceiver;
    float alpha;

    public ChangeAlphaCommand(ImageReceiver imageReceiver, float alpha) {
        this.imageReceiver = imageReceiver;
        this.alpha = alpha;
    }

    protected ChangeAlphaCommand(Parcel in) {
        alpha = in.readFloat();
    }

    public static final Creator<ChangeAlphaCommand> CREATOR = new Creator<ChangeAlphaCommand>() {
        @Override
        public ChangeAlphaCommand createFromParcel(Parcel in) {
            return new ChangeAlphaCommand(in);
        }

        @Override
        public ChangeAlphaCommand[] newArray(int size) {
            return new ChangeAlphaCommand[size];
        }
    };

    @Override
    public void execute() {
        imageReceiver.changeAlpha(alpha);
    }

    @Override
    public void setReceiver(Receiver receiver) {
        this.imageReceiver = (ImageReceiver) receiver;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(alpha);
    }
}

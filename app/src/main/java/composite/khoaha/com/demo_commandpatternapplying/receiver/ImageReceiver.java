package composite.khoaha.com.demo_commandpatternapplying.receiver;

import android.util.Log;
import android.widget.ImageView;

import composite.khoaha.com.demo_commandpatternapplying.MyCons;

/**
 * Created by HoangAnhKhoa on 1/14/16.
 */
public class ImageReceiver extends Receiver {

    ImageView imageView;

    public ImageReceiver(ImageView imageView) {
        this.imageView = imageView;
    }

    public void changeAlpha(float alpha) {
        Log.d(MyCons.LOG, "ImageReceiver.changeAlpha: " + alpha);
        float currentAlpha = imageView.getAlpha();
        currentAlpha += alpha;
        imageView.setAlpha(currentAlpha);
    }
}

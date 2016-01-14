package composite.khoaha.com.demo_commandpatternapplying.receiver;

import android.widget.ImageView;

/**
 * Created by HoangAnhKhoa on 1/14/16.
 */
public class ImageReceiver {

    ImageView imageView;

    public ImageReceiver(ImageView imageView) {
        this.imageView = imageView;
    }

    public void changeAlpha(float alpha) {
        float currentAlpha = imageView.getAlpha();
        currentAlpha += alpha;
        imageView.setAlpha(currentAlpha);
    }
}

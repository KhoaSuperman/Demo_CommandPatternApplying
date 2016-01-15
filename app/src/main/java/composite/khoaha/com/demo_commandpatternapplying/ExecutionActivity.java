package composite.khoaha.com.demo_commandpatternapplying;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import composite.khoaha.com.demo_commandpatternapplying.command.Command;
import composite.khoaha.com.demo_commandpatternapplying.invoker.Invoker;
import composite.khoaha.com.demo_commandpatternapplying.receiver.ImageReceiver;

public class ExecutionActivity extends AppCompatActivity {

    @Bind(R.id.ivImage)
    ImageView ivImage;
    @Bind(R.id.tvAlpha)
    TextView tvAlpha;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ExecutionActivity.class);
        return intent;
    }

    ImageReceiver imageReceiver;
    Invoker invoker;

    ArrayList<Command> commands = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution);
        ButterKnife.bind(this);

        imageReceiver = new ImageReceiver(ivImage);
        invoker = new Invoker();

        //get macro from file
        try {
            FileInputStream fis = new FileInputStream(MyCons.path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            commands = (ArrayList<Command>) ois.readObject();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (final Command cmd : commands) {
                        cmd.setReceiver(imageReceiver);

                        //sleep 1s
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //execute command
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cmd.execute();
                                tvAlpha.setText(String.format("%.1f", ivImage.getAlpha()) + " | Size " + imageReceiver.getSize() );
                            }
                        });
                    }
                }
            }).start();
        } catch (Exception e) {
            Log.e(MyCons.LOG, "MainActivity.sayHi" + e.getMessage());
        }
    }
}

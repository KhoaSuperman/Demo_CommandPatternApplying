package composite.khoaha.com.demo_commandpatternapplying;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import composite.khoaha.com.demo_commandpatternapplying.command.ChangeAlphaCommand;
import composite.khoaha.com.demo_commandpatternapplying.invoker.Invoker;
import composite.khoaha.com.demo_commandpatternapplying.receiver.ImageReceiver;

public class ExecutionActivity extends AppCompatActivity {

    static final String COMMAND = "command";
    @Bind(R.id.ivImage)
    ImageView ivImage;

    public static Intent createIntent(Context context, ChangeAlphaCommand command) {
        Intent intent = new Intent(context, ExecutionActivity.class);
        Bundle params = new Bundle();
        params.putParcelable(COMMAND, command);
        intent.putExtras(params);
        return intent;
    }

    ImageReceiver imageReceiver;
    Invoker invoker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution);
        ButterKnife.bind(this);

        final ChangeAlphaCommand command = getIntent().getExtras().getParcelable(COMMAND);

        if (command != null) {
            imageReceiver = new ImageReceiver(ivImage);
            command.setReceiver(imageReceiver);

            invoker = new Invoker();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                invoker.addCommand(command);
            }
        }, 1000);
    }
}

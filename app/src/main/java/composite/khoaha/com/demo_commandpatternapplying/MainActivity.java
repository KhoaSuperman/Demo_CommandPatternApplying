package composite.khoaha.com.demo_commandpatternapplying;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import composite.khoaha.com.demo_commandpatternapplying.command.ChangeAlphaCommand;
import composite.khoaha.com.demo_commandpatternapplying.command.ResizeCommand;
import composite.khoaha.com.demo_commandpatternapplying.invoker.Invoker;
import composite.khoaha.com.demo_commandpatternapplying.receiver.ImageReceiver;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.ivImage)
    ImageView ivImage;
    @Bind(R.id.btnAlphaDown)
    Button btnAlphaDown;
    @Bind(R.id.btnAlphaUp)
    Button btnAlphaUp;
    @Bind(R.id.section_left)
    RelativeLayout sectionLeft;
    @Bind(R.id.btnRedo)
    Button btnRedo;
    @Bind(R.id.btnUndo)
    Button btnUndo;
    @Bind(R.id.section_do)
    LinearLayout sectionDo;
    @Bind(R.id.rvHistory)
    RecyclerView rvHistory;
    @Bind(R.id.container)
    LinearLayout container;
    @Bind(R.id.tvAlpha)
    TextView tvAlpha;
    @Bind(R.id.section_change_alpha)
    LinearLayout sectionChangeAlpha;
    @Bind(R.id.btnSaveMacro)
    Button btnSaveMacro;
    @Bind(R.id.btnTestMacro)
    Button btnTestMacro;
    @Bind(R.id.section_macro)
    LinearLayout sectionMacro;
    @Bind(R.id.btnResize)
    Button btnResize;

    static final float ALPHA_STEP = 0.1f;

    HistoryAdapter adapter;
    ArrayList<String> textHistorys;

    ImageReceiver imageReceiver;
    Invoker invoker = new Invoker();
    int originalSize = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //list history
        rvHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        textHistorys = invoker.getHistoryList();
        adapter = new HistoryAdapter(textHistorys);
        rvHistory.setAdapter(adapter);

        //image receiver
        imageReceiver = new ImageReceiver(ivImage);

        //callback when history changed
        invoker.setInvokerListener(new Invoker.InvokerListener() {
            @Override
            public void commandAdded() {
                adapter.notifyItemInserted(0);
            }

            @Override
            public void commandRemoved() {
                adapter.notifyItemRemoved(0);
            }
        });

        //original size of image
        invoker.addCommand(new ResizeCommand(imageReceiver, originalSize, originalSize));
    }

    @OnClick({R.id.btnAlphaUp, R.id.btnAlphaDown, R.id.btnRedo, R.id.btnUndo, R.id.btnSaveMacro, R.id.btnTestMacro, R.id.btnResize})
    void sayHi(View view) {
        switch (view.getId()) {
            case R.id.btnUndo:
                invoker.undo();
                break;
            case R.id.btnRedo:
                invoker.redo();
                break;
            case R.id.btnAlphaDown:
                doChangeAlpha(-ALPHA_STEP);
                break;
            case R.id.btnAlphaUp:
                doChangeAlpha(ALPHA_STEP);
                break;
            case R.id.btnSaveMacro:
                try {
                    FileOutputStream fos = new FileOutputStream(MyCons.path);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(invoker.getCommands());
                    oos.flush();
                    oos.close();

                    Toast.makeText(getBaseContext(), "Save Macro success!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e(MyCons.LOG, "MainActivity.sayHi" + e.getMessage());
                }
                break;
            case R.id.btnTestMacro:
                Intent intent = ExecutionActivity.createIntent(this);
                startActivity(intent);
                break;
            case R.id.btnResize:
                int newSize = (new Random().nextInt(5) + 1 ) * 100;
                invoker.addCommand(new ResizeCommand(imageReceiver, newSize, imageReceiver.getSize()));
                break;
        }

        tvAlpha.setText(String.format("%.1f", ivImage.getAlpha()) +" | Size: "+ imageReceiver.getSize());
    }

    void doChangeAlpha(float alpha) {
        ChangeAlphaCommand command = new ChangeAlphaCommand(imageReceiver, alpha);
        invoker.addCommand(command);
    }
}

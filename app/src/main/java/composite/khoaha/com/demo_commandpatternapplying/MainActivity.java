package composite.khoaha.com.demo_commandpatternapplying;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import composite.khoaha.com.demo_commandpatternapplying.command.ChangeAlphaCommand;
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

    static final float ALPHA_STEP = 0.1f;

    HistoryAdapter adapter;
    ArrayList<String> textHistorys = new ArrayList<>();

    ImageReceiver imageReceiver;
    Invoker invoker;
    @Bind(R.id.tvAlpha)
    TextView tvAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rvHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new HistoryAdapter(textHistorys);
        rvHistory.setAdapter(adapter);

        imageReceiver = new ImageReceiver(ivImage);
        invoker = new Invoker();
    }

    @OnClick({R.id.btnAlphaUp, R.id.btnAlphaDown, R.id.btnRedo, R.id.btnUndo})
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
        }

        tvAlpha.setText(String.format("%.1f", ivImage.getAlpha()));
    }

    void doChangeAlpha(float alpha) {
        ChangeAlphaCommand command = new ChangeAlphaCommand(imageReceiver, alpha);
        invoker.addCommand(command);

//        Intent intent = ExecutionActivity.createIntent(this, command);
//        startActivity(intent);

        //add action to list history
        String textHistory = alpha > 0 ? "Alpha Up" : "Alpha Down";
        textHistorys.add(textHistory);
        adapter.notifyItemInserted(textHistorys.size() - 1);
    }
}

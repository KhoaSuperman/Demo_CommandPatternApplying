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

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rvHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new HistoryAdapter(textHistorys);
        rvHistory.setAdapter(adapter);
    }

    @OnClick({R.id.btnAlphaUp, R.id.btnAlphaDown, R.id.btnRedo, R.id.btnUndo})
    void sayHi(View view) {
        switch (view.getId()) {
            case R.id.btnUndo:
                break;
            case R.id.btnRedo:
                break;
            case R.id.btnAlphaDown:
                doChangeAlpha(-ALPHA_STEP);
                break;
            case R.id.btnAlphaUp:
                doChangeAlpha(ALPHA_STEP);
                break;
        }
    }

    void doChangeAlpha(float alpha) {
        float currentAlpha = ivImage.getAlpha();
        currentAlpha += alpha;
        ivImage.setAlpha(currentAlpha);

        //add action to list history
        String textHistory = alpha > 0 ? "Alpha Up" : "Alpha Down";
        textHistorys.add(textHistory);
        adapter.notifyItemInserted(textHistorys.size() - 1);
    }
}

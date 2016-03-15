package tm.davidwang.dwtoast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import tm.davidwang.toastactivity.ToastActivity;

public class MainActivity extends BaseActivity {

    private ArrayList<Button> fromarray;
    private int formid[] = {R.id.from_one, R.id.from_two, R.id.from_three, R.id.from_four};
    private int form_num = 0;
    private ArrayList<Button> toarray;
    private int toid[] = {R.id.to_one, R.id.to_two, R.id.to_three, R.id.to_four};
    private int to_num = 0;
    private ArrayList<Button> coverarray;
    private int coverid[] = {R.id.statusBtn, R.id.navBtn, R.id.statusAndNav};

    private SeekBar seekBar,seekBarTension,seekBarFriction;
    private TextView timeLab,qcTensionText,qcFrictionText;
    private float time = 0.5f;
    private int coverType = 0;
    private int qcTension = 100,qcFriction = 6;

    private EditText editView;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddToolbar();
        findID();
        Listener();
    }

    private void findID() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        timeLab = (TextView) findViewById(R.id.timeLab);
        seekBarTension = (SeekBar) findViewById(R.id.seekBarTension);
        seekBarFriction = (SeekBar) findViewById(R.id.seekBarFriction);
        qcTensionText = (TextView) findViewById(R.id.qcTension);
        qcFrictionText = (TextView) findViewById(R.id.qcFriction);
        editView = (EditText) findViewById(R.id.editView);

        fromarray = new ArrayList<Button>();
        for (int i = 0; i < formid.length; i++) {
            fromarray.add((Button) findViewById(formid[i]));
        }
        toarray = new ArrayList<Button>();
        for (int i = 0; i < toid.length; i++) {
            toarray.add((Button) findViewById(toid[i]));
        }
        coverarray = new ArrayList<Button>();
        for (int i = 0; i < coverid.length; i++) {
            coverarray.add((Button) findViewById(coverid[i]));
        }
    }

    private void Listener() {
        for (int i = 0; i < fromarray.size(); i++) {
            Button button = fromarray.get(i);
            button.setTag(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    form_num = tag;
                    for (int i = 0; i < fromarray.size(); i++) {
                        Button button = fromarray.get(i);
                        if (i == tag) {
                            button.setBackgroundResource(R.color.btn_background_press);
                        } else {
                            button.setBackgroundResource(R.color.btn_background);
                        }
                    }
                }
            });
        }

        for (int i = 0; i < toarray.size(); i++) {
            Button button = toarray.get(i);
            button.setTag(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    to_num = tag;
                    for (int i = 0; i < toarray.size(); i++) {
                        Button button = toarray.get(i);
                        if (i == tag) {
                            button.setBackgroundResource(R.color.btn_background_press);
                        } else {
                            button.setBackgroundResource(R.color.btn_background);
                        }
                    }
                }
            });
        }

        for (int i = 0; i < coverarray.size(); i++) {
            Button button = coverarray.get(i);
            button.setTag(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    coverType = tag;
                    for (int i = 0; i < coverarray.size(); i++) {
                        Button button = coverarray.get(i);
                        if (i == tag) {
                            button.setBackgroundResource(R.color.btn_background_press);
                        } else {
                            button.setBackgroundResource(R.color.btn_background);
                        }
                    }
                }
            });
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                time = ((float)progress/2);
                timeLab.setText(time+" seconds");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarTension.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                qcTension = progress;
                qcTensionText.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarFriction.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                qcFriction = progress;
                qcFrictionText.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void showOnclick(View view){
        description = editView.getText().toString();
        Intent intent = new Intent(this,ToastActivity.class);
        intent.putExtra("form_num",form_num);
        intent.putExtra("to_num",to_num);
        intent.putExtra("time",time);
        intent.putExtra("coverType",coverType);
        intent.putExtra("qcTension",qcTension);
        intent.putExtra("qcFriction",qcFriction);
        intent.putExtra("description",description);
        startActivity(intent);
    }

}

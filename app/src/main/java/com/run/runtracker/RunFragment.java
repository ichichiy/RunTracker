package com.run.runtracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RunFragment extends Fragment {

    private Button startButton;
    private Button stopButton;
    private TextView startText;
    private TextView laText;
    private TextView loText;
    private TextView alText;
    private TextView elText;

    private RunManager mRunManager;

    public RunFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mRunManager=RunManager.getRunManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_run, container, false);
        this.startText = (TextView) v.findViewById(R.id.startedText);
        this.startButton = (Button) v.findViewById(R.id.StartButton);
        this.stopButton = (Button) v.findViewById(R.id.StopButton);
        this.laText = (TextView) v.findViewById(R.id.laText);
        this.loText = (TextView) v.findViewById(R.id.loText);
        this.alText = (TextView) v.findViewById(R.id.alText);
        this.elText = (TextView) v.findViewById(R.id.elText);

        this.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRunManager.startLoactionUpdates();
                updateUI();
            }
        });

        this.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRunManager.stopLocationUpdates();
                updateUI();
            }
        });
        return v;
    }

    private void updateUI() {
        boolean started=mRunManager.isTrackingRun();
        this.startButton.setEnabled(!started);
        this.stopButton.setEnabled(started);
    }
}

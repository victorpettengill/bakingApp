package br.com.victorpettengill.bakingapp.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.victorpettengill.bakingapp.R;
import br.com.victorpettengill.bakingapp.beans.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {

    public static final String STEP_ARG = "step";
    private Step recipeStep;

    @BindView(R.id.instructions) TextView instructions;

    public StepDetailFragment() {
    }

    public static StepDetailFragment newInstance(Step recipeStep) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(STEP_ARG, recipeStep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            recipeStep = getArguments().getParcelable(STEP_ARG);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, rootView);

        instructions.setText(recipeStep.getDescription());

        return rootView;
    }

}

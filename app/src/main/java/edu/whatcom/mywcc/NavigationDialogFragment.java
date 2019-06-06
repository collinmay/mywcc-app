package edu.whatcom.mywcc;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import androidx.fragment.app.DialogFragment;
import edu.whatcom.mywcc.models.Building;
import edu.whatcom.mywcc.models.path.CampusMap;

public class NavigationDialogFragment extends DialogFragment {
    private final NavDialogListener listener;
    private final CampusMap map;

    public interface NavDialogListener {
        public void onNavigate(Building from, Building to);
    }

    public NavigationDialogFragment(NavDialogListener l, CampusMap map) {
        listener = l;
        this.map = map;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View r = inflater.inflate(R.layout.fragment_navigation_dialog, container, false);
        Button b = r.findViewById(R.id.nav_button);
        Spinner spinnerFrom = r.findViewById(R.id.nav_from);
        Spinner spinnerTo = r.findViewById(R.id.nav_to);

        final ArrayAdapter<Building> adapter = new ArrayAdapter<>(
                r.getContext(),
                android.R.layout.simple_spinner_item,
                new ArrayList<>(map.buildings.keySet()));
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        b.setOnClickListener((v) -> listener.onNavigate(
                adapter.getItem(spinnerFrom.getSelectedItemPosition()),
                adapter.getItem(spinnerTo.getSelectedItemPosition())));
        return r;
    }
}

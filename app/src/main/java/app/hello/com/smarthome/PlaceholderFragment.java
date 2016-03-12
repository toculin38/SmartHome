package app.hello.com.smarthome;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    protected View rootView;
    private static final String ARG_SECTION_NUMBER = "section_number";
    protected CommandManager commandManager ;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = CreateFragmentBySectionNumber(sectionNumber);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private static PlaceholderFragment CreateFragmentBySectionNumber(int sectionNumber) {
        PlaceholderFragment fragment;
        switch (sectionNumber) {
            case 1:
                fragment = new AppliancesFragment();
                break;
            case 2:
                fragment = new ConnectionFragment();
                break;
            case 3:
                fragment = new LocationFragment();
                break;
            default:
                fragment = new PlaceholderFragment();
        }
        return fragment;
    }

    public PlaceholderFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commandManager = ((MainActivity)getActivity()).getCommandManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1:
                rootView = inflater.inflate(R.layout.fragment_appliances, container, false);
                break;
            case 2:
                rootView = inflater.inflate(R.layout.fragment_connection, container, false);
                break;
            case 3:
                rootView = inflater.inflate(R.layout.fragment_location, container, false);
                break;
            default:
                rootView = inflater.inflate(R.layout.fragment_smart_home, container, false);
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}

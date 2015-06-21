package com.indyzalab.achiever;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.indyzalab.achiever.baseadapter.EventItem;
import com.indyzalab.achiever.baseadapter.ListImageAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestLogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestLogFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static ListView listView;
    private static ListImageAdapter listAdapter;
    private static ArrayList<EventItem> listArray = new ArrayList<EventItem>();


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestLogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestLogFragment newInstance(String param1, String param2) {
        QuestLogFragment fragment = new QuestLogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public QuestLogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quest_log, container, false);
        listArray = new ArrayList<EventItem>();
        listView = (ListView) view.findViewById(R.id.listView_noti);
        listArray.add(new EventItem(EventItem.TYPE_PRIVATE, null,EventItem.CATEGORY_SCHOOL,null,null,"Learn Ruby","Learn basic Ruby on Rails this month", 0));
        listArray.add(new EventItem(EventItem.TYPE_PRIVATE, null,EventItem.CATEGORY_FITNESS,null,null,"Defeat Vegeta","Beat Vegeta up", 0));
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        // some code #3 (that needs to be ran in UI thread)
//                        dataSource.open();
//                        listArray = dataSource.getAllNotificationItems();
//
//                        dataSource.close();
                        Log.i("QuestLogFragment","No of ");
                        listAdapter = new ListImageAdapter(getActivity(), R.layout.quest_list_element, listArray);
                        listView.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                    }
                });


            }
        };
        thread.run();
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void showFragment(int fragmentIndex, boolean addToBackStack);
    }

    /**
     * Using showfragment from MainActivity
     * @param fragmentIndex indicate what fragment to show
     * @param addToBackStack
     */
    public void showFragment(int fragmentIndex, boolean addToBackStack) {
        if (mListener != null) {
            mListener.showFragment(fragmentIndex, addToBackStack);
        }
    }
}

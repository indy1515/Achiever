package com.indyzalab.achiever;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.ThemeSingleton;
import com.indyzalab.achiever.baseadapter.EventItem;
import com.indyzalab.achiever.baseadapter.RecyclerListImageAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;


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
    private int layout_height;
    private Context mContext;
    ParseUser currentUser;
    private static ArrayList<EventItem> listArray = new ArrayList<EventItem>();

    private Bitmap default_icon;
    private RecyclerView mRecyclerView;
    private RecyclerListImageAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

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
        mContext = view.getContext();
        default_icon = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.test_btn);
        Bitmap[] bit_arr = {default_icon};
        listArray = new ArrayList<EventItem>();
//        listView = (ListView) view.findViewById(R.id.listView_noti);
        listArray.add(new EventItem(EventItem.TYPE_PRIVATE, null, EventItem.CATEGORY_SCHOOL, bit_arr, null, "Learn Ruby", "Learn basic Ruby on Rails this month", 0));
        listArray.add(new EventItem(EventItem.TYPE_PRIVATE, null, EventItem.CATEGORY_FITNESS, bit_arr, null, "Defeat Vegeta", "Beat Vegeta up", 0));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_listview);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setSmoothScrollbarEnabled(true);

        mRecyclerView.setLayoutManager(mLayoutManager);

        layout_height = mRecyclerView.getLayoutManager().getHeight();
        Log.i("QuestLogFragment", "Layout Height: " + layout_height);
        // specify an adapter (see also next example)
        mAdapter = new RecyclerListImageAdapter(mContext,listArray);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new SlideInRightAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(200);
        mRecyclerView.getItemAnimator().setRemoveDuration(200);

        FloatingActionButton addButton = getFloatingActionButton();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomView();

            }
        });

//        //FrameLayout rootLayout;
//        CoordinatorLayout coordinatorLayout;
//
////rootLayout = (FrameLayout) findViewById(R.id.rootLayout);
//        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.quest_log_coordinatorLayout);
//        currentUser = ParseUser.getCurrentUser();
//        String name = "";
//        if(currentUser != null){
//            name = currentUser.getString("name");
//        }
//        Snackbar.make(coordinatorLayout, "Hi! "+name, Snackbar.LENGTH_SHORT)
//                .setAction("Undo", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                })
//                .show();

//        showFragment(QUEST_LOG_FRAGMENT, false);
        return view;
    }

    private void addItem(final EventItem item) {
        final Handler h = new Handler();
        Runnable r1 = new Runnable() {

            @Override
            public void run() {
                int before_position = mLayoutManager.findFirstVisibleItemPosition();
                if(before_position == 0)
                    mLayoutManager.smoothScrollToPosition(mRecyclerView, null, 0);
                mAdapter.add(0, item);
                int after_position = mLayoutManager.findFirstVisibleItemPosition();
                Log.i("QuestLogFragment","Visible Position First:"+after_position);
                if(after_position > 0){
                    // TODO:Create a notice on top somehow
                }
                // do first thing

            }
        };
        h.postDelayed(r1,300);

    }

    private EditText passwordInput;
    private EditText questET;
    private View positiveAction;
    private TextInputLayout questInput;
    private void showCustomView() {
        MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                .title(R.string.add_quest)
                .customView(R.layout.dialog_customview, true)
                .positiveText(R.string.create)
                .negativeText(android.R.string.cancel)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
//                        showToast("Item added");
                        Bitmap[] bit_arr = {default_icon};
                        questET.clearFocus();

                        addItem(new EventItem(EventItem.TYPE_PRIVATE, null, EventItem.CATEGORY_FITNESS, bit_arr, null, "Defeat Vegeta", "Beat Vegeta up", 0));
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                    }
                }).build();
        questInput = (TextInputLayout) dialog.getCustomView().findViewById(R.id.dialog_quest_nameTIL);
        questET = questInput.getEditText();


        questInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                Log.i("QuestLogFragment","Quest TIL Focus: "+hasFocus);
                if(hasFocus){
//                    Log.i("QuestLogFragment","Quest ET Focus: "+hasFocus);
                }else{

                }
            }
        });
        questInput.setHint(getResources().getString(R.string.quest_name) + " " + getResources().getString(R.string.quest_content));

        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        //noinspection ConstantConditions
//        passwordInput = (EditText) dialog.getCustomView().findViewById(R.id.password);
        questET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                positiveAction.setEnabled(s.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Toggling the show password CheckBox will mask or unmask the password input EditText
//        CheckBox checkbox = (CheckBox) dialog.getCustomView().findViewById(R.id.showPassword);
//        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                passwordInput.setInputType(!isChecked ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
//                passwordInput.setTransformationMethod(!isChecked ? PasswordTransformationMethod.getInstance() : null);
//            }
//        });

        int widgetColor = ThemeSingleton.get().widgetColor;
//        MDTintHelper.setTint(checkbox,
//                widgetColor == 0 ? getResources().getColor(R.color.material_teal_500) : widgetColor);

//        MDTintHelper.setTint(passwordInput,
//                widgetColor == 0 ? getResources().getColor(R.color.material_teal_500) : widgetColor);

        dialog.show();
        positiveAction.setEnabled(false); // disabled by default
    }

    private Toast mToast;
    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        mToast.show();
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
        public FloatingActionButton getFloatingActionButton();
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

    public FloatingActionButton getFloatingActionButton() {
        if (mListener != null) {
            return mListener.getFloatingActionButton();
        }
        return null;
    }


}

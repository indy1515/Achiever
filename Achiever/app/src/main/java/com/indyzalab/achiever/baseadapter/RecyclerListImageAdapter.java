package com.indyzalab.achiever.baseadapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.indyzalab.achiever.R;

import java.util.ArrayList;

/**
 * Created by apple on 6/22/15 AD.
 */
public class RecyclerListImageAdapter extends RecyclerView.Adapter<RecyclerListImageAdapter.ViewHolder> {
    ArrayList<EventItem> data = new ArrayList<EventItem>();
    Context context;
    private int lastPosition = -1;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txtTitle;
        TextView txtDescription;
        TextView txtDescription2;
        TextView txtDescription3;
        ImageView imageItem;
        View stateView;
        View container;
        public ViewHolder(View view) {
            super(view);
            this.container = view;
            txtTitle = (TextView) view.findViewById(R.id.textTitleList1);
            txtDescription = (TextView) view.findViewById(R.id.textDescList1);
            txtDescription2 = (TextView) view.findViewById(R.id.textDescList2);
            txtDescription3 = (TextView) view.findViewById(R.id.textDescList3);
            imageItem = (ImageView) view.findViewById(R.id.imgList1);
            stateView = view.findViewById(R.id.state_open);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerListImageAdapter(Context context,ArrayList<EventItem> data) {
        this.data = data;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerListImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quest_list_element, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final EventItem item = data.get(position);
        holder.txtTitle.setText(item.getTitle());
//    	Date date = DateHelper.timestampToDate("" + item.getTimestamp());
//    	String fDate = new SimpleDateFormat("dd MMM yyyy, kk:mm").format(date);
//    	String description = fDate+" " + item.getCourseTitle().toUpperCase()+"("+item.getCourseNo()+")";
        holder.txtDescription.setText(item.getDescription());
        if(item.isPrivate()){
            holder.txtDescription2.setText("");
            holder.txtDescription3.setText("Private");
        }else if(item.isPublic()){
            holder.txtDescription2.setText(item.getNumber_attend()+"");
            holder.txtDescription3.setText("Public");
        }
        holder.imageItem.setImageBitmap(item.getCategory_icon());

        holder.stateView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));


        //set Type
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto_Regular.ttf");
        holder.txtTitle.setTypeface(tf);
        holder.txtTitle.setTextColor(context.getResources().getColor(R.color.black));
        holder.txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        holder.txtDescription.setTypeface(tf);
        holder.txtDescription.setTextColor(context.getResources().getColor(R.color.black));
        holder.txtDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        holder.txtDescription2.setTypeface(tf);
        holder.txtDescription2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        holder.txtDescription3.setTypeface(tf);
        holder.txtDescription3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        Log.i("RecyclerAdapter","Position: "+position);

        setAnimation(holder.container, position);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }
}
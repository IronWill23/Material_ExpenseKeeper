package com.library.ironwill.expensekeeper.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.library.ironwill.expensekeeper.R;
import com.library.ironwill.expensekeeper.model.ItemCategory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Comment and decouple the item listener and adapter
 * Solved the conflicts between the Item onTouch and onClick
 * @param <T>
 */

public class RvCategoryAdapter<T> extends RecyclerView.Adapter<RvCategoryAdapter<T>.ViewHolder> {
    private List<T> items = Collections.emptyList();
    private SparseBooleanArray selectedItems;
    Activity mActivity;

    //    private OnItemClickListener<T> onItemClickListener;

    @Override
    public RvCategoryAdapter<T>.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RvCategoryAdapter<T>.ViewHolder holder, int position) {
        final ItemCategory category = (ItemCategory) items.get(position);
        holder.mName.setText(category.getCategoryName());
        holder.mNum.setText(category.getMoneyNum());
        if (category.getColor() == 0) {
            holder.mColor.setBackgroundColor(mActivity.getResources().getColor(R.color.lightRed));
            holder.mNum.setTextColor(mActivity.getResources().getColor(R.color.lightRed));
        } else {
            holder.mColor.setBackgroundColor(mActivity.getResources().getColor(R.color.forestGreen));
            holder.mNum.setTextColor(mActivity.getResources().getColor(R.color.forestGreen));
        }
        holder.mPic.setImageResource(category.getCategoryPic());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateList(List<T> thingList, Activity mActivity) {
        this.items = thingList;
        this.mActivity = mActivity;
        notifyDataSetChanged();
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

/*    public interface OnItemClickListener<T> {
        void onItemClick(View view, T item, boolean isLongClick);
    }

    public void setOnItemClickListener(final OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }*/

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
    //add data
    public void addItem(ArrayList<T> newData) {
        newData.addAll(items);
        items.removeAll(items);
        items.addAll(newData);
        notifyDataSetChanged();
    }

    //remove data
    public void removeRecycle(int position) {
        items.remove(position);
        notifyDataSetChanged();
        if (items.size() == 0) {
            Toast.makeText(mActivity, "No more Data", Toast.LENGTH_SHORT).show();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mPic;
        public TextView mName;
        public TextView mNum;
        public ImageView mColor;
        public LinearLayout mContainer;
        public ImageView mDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mNum = (TextView) itemView.findViewById(R.id.category_num);
            mName = (TextView) itemView.findViewById(R.id.category_name);
            mColor = (ImageView) itemView.findViewById(R.id.category_color);
            mPic = (ImageView) itemView.findViewById(R.id.category_icon);
            mContainer = (LinearLayout) itemView.findViewById(R.id.ll_container);
            mDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
            /*itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);*/
        }

        /*@Override
        public void onClick(View v) {
            handleClick(v, false);
        }

        @Override
        public boolean onLongClick(View v) {
            return handleClick(v, true);
        }

        private boolean handleClick(View v, boolean isLongClick) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, items.get(getAdapterPosition()), isLongClick);
                return true;
            }
            return false;
        }*/
    }
}

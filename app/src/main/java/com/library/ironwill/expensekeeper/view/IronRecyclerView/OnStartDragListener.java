
package com.library.ironwill.expensekeeper.view.IronRecyclerView;

import com.library.ironwill.expensekeeper.adapter.RvCategoryAdapter;

/**
 * Listener for manual initiation of a drag.
 */
public interface OnStartDragListener {

    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RvCategoryAdapter.ViewHolder viewHolder);

}

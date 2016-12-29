package com.library.ironwill.expensekeeper.view.IronRecyclerView;

/**
 * @author mabeijianxi
 */
public interface PullCallback {

    void onLoadMore();

    void onRefresh();

    boolean isLoading();

    boolean hasLoadedAllItems();

}

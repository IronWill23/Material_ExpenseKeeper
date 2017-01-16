package com.library.ironwill.expensekeeper.util;


import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.library.ironwill.expensekeeper.R;
import com.library.ironwill.expensekeeper.activity.MainActivity;
import com.library.ironwill.expensekeeper.helper.TransitionHelper;

public class Navigator {

    public static int ANIM_DURATION = 350;

    public static void launchDetail(MainActivity fromActivity, View fromView, View backgroundView) {
        ViewCompat.setTransitionName(fromView, "detail_element");
//        ViewCompat.setTransitionName(fromActivity.findViewById(R.id.fab_add), "fab");
        ActivityOptionsCompat options =
                TransitionHelper.makeOptionsCompat(
                        fromActivity,
                        Pair.create(fromView, "detail_element")
//                        Pair.create(fromActivity.findViewById(R.id.fab_add), "fab")
                );
        Intent intent = new Intent(fromActivity, MainActivity.class);
//        intent.putExtra("item_text", item.getCategoryName());
        if (backgroundView instanceof LinearLayout){
            intent.putExtra("fragment_resource_id", R.layout.fragment_statistic_detail);
        }else{
            intent.putExtra("fragment_resource_id", R.layout.fragment_card_detail);
        }

        if (backgroundView != null){
            BitmapUtil.storeBitmapInIntent(BitmapUtil.createBitmap(backgroundView), intent);
        }

        ActivityCompat.startActivity(fromActivity, intent, options.toBundle());

        fromActivity.overridePendingTransition(R.anim.slide_up, R.anim.scale_down);
    }

    public static void launchOverlay(MainActivity fromActivity, View fromView, View backgroundView) {
        ActivityOptionsCompat options =
                TransitionHelper.makeOptionsCompat(
                        fromActivity
                );
        Intent intent = new Intent(fromActivity, MainActivity.class);
//*        intent.putExtra("fragment_resource_id", R.layout.fragment_overlay);

        if (backgroundView != null)
            BitmapUtil.storeBitmapInIntent(BitmapUtil.createBitmap(backgroundView), intent);

        ActivityCompat.startActivity(fromActivity, intent, options.toBundle());

        fromActivity.overridePendingTransition(R.anim.slide_up, R.anim.scale_down);
    }

}

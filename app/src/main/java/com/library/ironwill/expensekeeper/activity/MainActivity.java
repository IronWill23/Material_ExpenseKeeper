package com.library.ironwill.expensekeeper.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;
import com.jaeger.library.StatusBarUtil;
import com.library.ironwill.expensekeeper.R;
import com.library.ironwill.expensekeeper.fragment.CardDetailFragment;
import com.library.ironwill.expensekeeper.fragment.CardListFragment;
import com.library.ironwill.expensekeeper.helper.TransitionHelper;
import com.library.ironwill.expensekeeper.util.BitmapUtil;
import com.library.ironwill.expensekeeper.view.ArcProgress.ArcProgress;
import com.library.ironwill.expensekeeper.view.DrawerItems.CustomPrimaryDrawerItem;
import com.library.ironwill.expensekeeper.view.DrawerItems.OverflowMenuDrawerItem;
import com.library.ironwill.expensekeeper.view.MaterialSpinner.MaterialSpinner;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.ColorShowMethod;

public class MainActivity extends TransitionHelper.BaseActivity {

    protected static String BASE_FRAGMENT = "base_fragment";
    private MaterialMenuDrawable.IconState currentIconState;
    private ArcProgress mArcProgress;
    private TextView numIncome, numExpense;
    private MaterialSpinner mSpinner;

    private MaterialMenuView homeButton;
    public View fragmentBackground;

    //Material DrawerLayout
    private IProfile profile, profile2, profile3, profile4, profile5;
    private AccountHeader headerResult = null;
    private static final int PROFILE_SETTING = 1;
    private Drawer drawer = null;

    private long exitTime = 0;
    private static Boolean isFirstLogin = true;

    private static final String[] dateList = {
            "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        if (isFirstLogin) {
            TransitionsHeleper.getInstance()
                    .setShowMethod(new ColorShowMethod(R.color.white, R.color.endRed) {
                        @Override
                        public void loadCopyView(InfoBean bean, ImageView copyView) {
                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(
                                    ObjectAnimator.ofFloat(copyView, "rotation", 0, 180),
                                    ObjectAnimator.ofFloat(copyView, "scaleX", 1, 0),
                                    ObjectAnimator.ofFloat(copyView, "scaleY", 1, 0)
                            );
                            set.setInterpolator(new AccelerateInterpolator());
                            set.setDuration(duration / 4 * 5).start();
                        }

                        @Override
                        public void loadTargetView(InfoBean bean, ImageView targetView) {

                        }
                    })
                    .show(this, null);
            isFirstLogin = false;
        }
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTransparent(this);
        initView();
        initToolbar();
        initDrawer(savedInstanceState);

        int income = Integer.parseInt(numIncome.getText().toString().substring(1));
        int expense = Integer.parseInt(numExpense.getText().toString().substring(1));
        int per = expense / income;
//        mArcProgress.setProgress(100 * per);
        mArcProgress.setProgress(42);
        initBaseFragment(savedInstanceState);
    }

    private void initDrawer(Bundle savedInstanceState) {
        profile = new ProfileDrawerItem().withName("Batman").withEmail("Bruce.Bat@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile));
        profile2 = new ProfileDrawerItem().withName("IronMan").withEmail("Stark.Iron@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile2)).withIdentifier(2);
        profile3 = new ProfileDrawerItem().withName("WonderWomen").withEmail("Diana.WW@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile3));
        profile4 = new ProfileDrawerItem().withName("Captain").withEmail("Steve.Cap@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile6)).withIdentifier(4);
        profile5 = new ProfileDrawerItem().withName("SuperMan").withEmail("Clark.Super@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));

        // Create the AccountHeader
        buildHeader(false, savedInstanceState);
        // Create the Drawer
        drawer = new DrawerBuilder()
                .withActivity(this)
//                .withToolbar(mToolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withTextColor(getResources().getColor(R.color.almost_black)),
                        new OverflowMenuDrawerItem().withName(R.string.drawer_item_menu_drawer_item).withMenu(R.menu.fragment_menu).withOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        }).withIcon(GoogleMaterial.Icon.gmd_filter_center_focus).withTextColor(getResources().getColor(R.color.almost_black)),
                        new CustomPrimaryDrawerItem().withName(R.string.drawer_item_manage).withIcon(FontAwesome.Icon.faw_amazon).withTextColor(getResources().getColor(R.color.almost_black)),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye).withTextColor(getResources().getColor(R.color.almost_black)),
//                        new CustomUrlPrimaryDrawerItem().withName(R.string.drawer_item_fragment_drawer).withDescription(R.string.drawer_item_fragment_drawer_desc).withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460"),
//                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cart_plus).withTextColor(getResources().getColor(R.color.almost_black)),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github).withTextColor(getResources().getColor(R.color.almost_black)),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withSelectedIconColor(Color.RED).withIconTintingEnabled(true).withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withTag("Bullhorn").withTextColor(getResources().getColor(R.color.almost_black)),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).withEnabled(true).withTextColor(getResources().getColor(R.color.almost_black))
                ) // add the items we want to use with our Drawer
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                        MainActivity.this.finish();
                        return true;
                    }
                })
                /*.addStickyDrawerItems(
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(10),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github)
                )*/
                .withSavedInstance(savedInstanceState)
                .build();
    }

    private void buildHeader(boolean compact, Bundle savedInstanceState) {
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.top_background)
                .withCompactStyle(compact)
                .addProfiles(
                        profile,
                        profile2,
                        profile3,
                        profile4,
                        profile5,
                        //don't ask but google uses 14dp for the add account icon in Gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        if (profile instanceof IDrawerItem && profile.getIdentifier() == PROFILE_SETTING) {
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Growlithe").withEmail("Growlithe@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }
                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = drawer.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    private void initToolbar() {
        //setup the Action for Material Menu
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!drawer.isDrawerOpen()) {
                    if (homeButton.getState() == MaterialMenuDrawable.IconState.BURGER) {
                        drawer.openDrawer();
                    } else if (homeButton.getState() == MaterialMenuDrawable.IconState.X) {
                        homeButton.animateState(MaterialMenuDrawable.IconState.BURGER);
                    } else if (homeButton.getState() == MaterialMenuDrawable.IconState.ARROW) {
                        onBackPressed();
                    }
                } else {
                    drawer.closeDrawer();
                }
            }
        });

        //set up the spinner in toolbar
        //TODO Add a spinner animation
        mSpinner.setBackgroundColor(getResources().getColor(R.color.middleRed));
        mSpinner.setTextColor(getResources().getColor(R.color.white));
        mSpinner.setArrowColor(getResources().getColor(R.color.white));
        mSpinner.setItems(dateList);
        mSpinner.setSelectedIndex(11);
    }

    private void initBaseFragment(Bundle savedInstanceState) {
        //apply background bitmap if we have one
        if (getIntent().hasExtra("bitmap_id")) {
            fragmentBackground.setBackground(new BitmapDrawable(getResources(), BitmapUtil.fetchBitmapFromIntent(getIntent())));
        }

        Fragment fragment = null;
        if (savedInstanceState != null) {
            fragment = getFragmentManager().findFragmentByTag(BASE_FRAGMENT);
        }
        if (fragment == null) fragment = getBaseFragment();
        setBaseFragment(fragment);
    }

    protected Fragment getBaseFragment() {
        int fragmentResourceId = getIntent().getIntExtra("fragment_resource_id", R.layout.fragment_card_list);
        switch (fragmentResourceId) {
            case R.layout.fragment_card_list:
            default:
                return new CardListFragment();
            case R.layout.fragment_card_detail:
                return CardDetailFragment.create();
        }
    }

    public void setBaseFragment(Fragment fragment) {
        if (fragment == null) return;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.base_fragment, fragment, BASE_FRAGMENT);
        transaction.commit();
    }

    private void initView() {
        mArcProgress = (ArcProgress) findViewById(R.id.arc_progress);
        numIncome = (TextView) findViewById(R.id.num_income);
        numExpense = (TextView) findViewById(R.id.num_expense);
        homeButton = (MaterialMenuView) findViewById(R.id.material_menu_button);
        fragmentBackground = findViewById(R.id.base_fragment_background);
        mSpinner = (MaterialSpinner) findViewById(R.id.spinner_date);
    }

    public boolean animateHomeIcon(MaterialMenuDrawable.IconState iconState) {
        if (currentIconState == iconState) return false;
        currentIconState = iconState;
        homeButton.animateState(currentIconState);
        return true;
    }

    public void setHomeIcon(MaterialMenuDrawable.IconState iconState) {
        if (currentIconState == iconState) return;
        currentIconState = iconState;
        homeButton.setState(currentIconState);

    }

    public static MainActivity of(Activity activity) {
        return (MainActivity) activity;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                this.exitApp();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(MainActivity.this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}

package com.library.ironwill.expensekeeper.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;
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
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class MainActivity extends TransitionHelper.BaseActivity {

    protected static String BASE_FRAGMENT = "base_fragment";
    private MaterialMenuDrawable.IconState currentIconState;
    private ArcProgress mArcProgress;
    private TextView numIncome, numExpense;
    private MaterialSpinner mSpinner;

    private MaterialMenuView homeButton;
    public View fragmentBackground;

    public FloatingActionButton addFABtn, doneFABtn;
    private BottomSheetBehavior mBehavior;
    private View mBottomSheet;
    private EditText titleText, contentText;

    //Material DrawerLayout
    private IProfile profile, profile2, profile3, profile4, profile5;
    private AccountHeader headerResult = null;
    private static final int PROFILE_SETTING = 1;
    private Drawer drawer = null;

    private static final String[] dateList = {
            "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolbar();
        initDrawer(savedInstanceState);

        int income = Integer.parseInt(numIncome.getText().toString().substring(1));
        int expense = Integer.parseInt(numExpense.getText().toString().substring(1));
        int per = expense / income;
//        mArcProgress.setProgress(100 * per);
        mArcProgress.setProgress(42);
        initBaseFragment(savedInstanceState);
        initClickAction();

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
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home),
                        //here we use a customPrimaryDrawerItem we defined in our sample app
                        //this custom DrawerItem extends the PrimaryDrawerItem so it just overwrites some methods
                        new OverflowMenuDrawerItem().withName(R.string.drawer_item_menu_drawer_item).withMenu(R.menu.fragment_menu).withOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        }).withIcon(GoogleMaterial.Icon.gmd_filter_center_focus),
                        new CustomPrimaryDrawerItem().withName(R.string.drawer_item_manage).withIcon(FontAwesome.Icon.faw_amazon),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withDescription("Try different skins").withIcon(FontAwesome.Icon.faw_eye),
//                        new CustomUrlPrimaryDrawerItem().withName(R.string.drawer_item_fragment_drawer).withDescription(R.string.drawer_item_fragment_drawer_desc).withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460"),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cart_plus),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withSelectedIconColor(Color.RED).withIconTintingEnabled(true).withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withTag("Bullhorn"),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).withEnabled(false)
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

    private void initClickAction() {
        /**
         * BottomSheet Behavior programming
         */
        if (mBottomSheet != null) {
            mBehavior = BottomSheetBehavior.from(mBottomSheet);
        }

        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (mBehavior.getState()==BottomSheetBehavior.STATE_DRAGGING){
                    mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    doneFABtn.setVisibility(View.VISIBLE);
                } else {
                    doneFABtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        addFABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    openMenu(view);
                } else {
                    mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    closeMenu(view);
                }
            }

            private void openMenu(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 65, 135);
                animator.setDuration(600);
                animator.start();
            }

            private void closeMenu(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 135, 65, 0);
                animator.setDuration(600);
                animator.start();
            }
        });
        doneFABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!titleText.getText().toString().equals("") && !contentText.getText().toString().equals("")) {

                    mList.add(new ItemCategory(
                            R.mipmap.ic_launcher,
                            titleText.getText().toString(),
                            contentText.getText().toString(),
                            0
                    ));
                    mAdapter = new RvCategoryAdapter(MainActivity.this, mList);
                    mRecyclerView.setAdapter(mAdapter);
                    mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    titleText.setText("");
                    contentText.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Title and comment can not be null!",
                            Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    private void initToolbar() {
        //setup the Action for Material Menu
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!drawer.isDrawerOpen()) {
                    if (homeButton.getState() == MaterialMenuDrawable.IconState.BURGER){
                        drawer.openDrawer();
                    }else if (homeButton.getState() == MaterialMenuDrawable.IconState.X){
                        homeButton.animateState(MaterialMenuDrawable.IconState.BURGER);
                    }else if (homeButton.getState() == MaterialMenuDrawable.IconState.ARROW){
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
        addFABtn = (FloatingActionButton) findViewById(R.id.fab_add);
        doneFABtn = (FloatingActionButton) findViewById(R.id.fab_done);
        mBottomSheet = findViewById(R.id.id_rl_bottomSheet);
        titleText = (EditText) findViewById(R.id.id_et_new_name);
        contentText = (EditText) findViewById(R.id.id_et_new_num);
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
}

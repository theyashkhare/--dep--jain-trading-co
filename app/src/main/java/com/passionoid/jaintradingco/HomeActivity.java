package com.passionoid.jaintradingco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.passionoid.jaintradingco.Model.Model;

import java.util.HashMap;
import java.util.Objects;


public class HomeActivity extends AppCompatActivity  implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener
{

    public static final String CHAT_PREFS = "ChatPrefs";
    public static final String DISPLAY_NAME_KEY = "username", DISPLAY_EMAIL_KEY = "email";
    Toolbar toolbar;
    Drawer result;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;
    String mDisplayName, mDisplayEmail;
    Handler mHandler;
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
    SliderLayout sliderLayout, slideShow ;
    HashMap<String, Integer> HashMapForLocalRes ;
    HashMap<String, String> HashMapForURL ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        sliderLayout = findViewById(R.id.slider);
        slideShow = findViewById(R.id.slider1);
        recyclerView = findViewById(R.id.list);
        toolbar = findViewById(R.id.nav_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        AddImageUrlFormLocalRes();
        AddImagesUrlOnline();

        setupDisplayName();
        inflateNavBar();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();
        mHandler = new Handler();


        for(String name :  HashMapForURL.keySet()){

            TextSliderView textSliderView = new TextSliderView(HomeActivity.this);
            textSliderView.description(name).image( HashMapForURL.get(name)).setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);
            sliderLayout.addSlider(textSliderView);

        }


        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(HomeActivity.this);
        sliderLayout.stopAutoCycle();

        for(String name :  HashMapForLocalRes.keySet()){

            TextSliderView textSliderView = new TextSliderView(HomeActivity.this);
            textSliderView.description(name).image( HashMapForLocalRes.get(name)).setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);
            slideShow.addSlider(textSliderView);

        }
        slideShow.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        slideShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slideShow.setCustomAnimation(new DescriptionAnimation());
        slideShow.setDuration(3000);
        slideShow.addOnPageChangeListener(HomeActivity.this);
        slideShow.stopAutoCycle();

    }


    private void setupDisplayName(){

        SharedPreferences prefs = getSharedPreferences(RegisterActivity.CHAT_PREFS, MODE_PRIVATE);
         mDisplayName = prefs.getString(RegisterActivity.DISPLAY_NAME_KEY, null);
         mDisplayEmail = prefs.getString(RegisterActivity.DISPLAY_EMAIL_KEY, null);
        if(mDisplayName == null) mDisplayName = "Anonymous";
        if(mDisplayEmail == null) mDisplayEmail = "anonymous@anonymail.com";

    }



    public void AddImagesUrlOnline(){

        HashMapForURL = new HashMap<>();
        HashMapForURL.put("CupCake", "https://cdn.pixabay.com/photo/2018/01/14/23/12/nature-3082832__340.jpg");
        HashMapForURL.put("Donut", "http://androidblog.esy.es/images/donut-2.png");
        HashMapForURL.put("Eclair", "http://androidblog.esy.es/images/eclair-3.png");
        HashMapForURL.put("Froyo", "http://androidblog.esy.es/images/froyo-4.png");
        HashMapForURL.put("GingerBread", "http://androidblog.esy.es/images/gingerbread-5.png");

    }

    public void AddImageUrlFormLocalRes(){

        HashMapForLocalRes = new HashMap<>();
        HashMapForLocalRes.put("CupCake", R.drawable.ic_cart);
        HashMapForLocalRes.put("Donut", R.drawable.ic_cart);
        HashMapForLocalRes.put("Eclair", R.drawable.ic_cart);
        HashMapForLocalRes.put("Froyo", R.drawable.ic_cart);
        HashMapForLocalRes.put("GingerBread", R.drawable.ic_cart);

    }



    private void fetch() {

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("posts");

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(query, snapshot -> new Model(Objects.requireNonNull(snapshot.child("id").getValue()).toString(),
                                Objects.requireNonNull(snapshot.child("title").getValue()).toString(),
                                Objects.requireNonNull(snapshot.child("desc").getValue()).toString()))
                        .build();

        adapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);

                return new ViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(ViewHolder holder, final int position, Model model) {
                holder.setTxtTitle(model.getmTitle());
                holder.setTxtDesc(model.getmDesc());

                holder.root.setOnClickListener(view -> Toast.makeText(HomeActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show());
            }

        };
        recyclerView.setAdapter(adapter);

    }


    private void inflateNavBar() {

        AccountHeader accountheader = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withSelectionListEnabledForSingleProfile(false)
                .withAlternativeProfileHeaderSwitching(false)
                .addProfiles(
                        new ProfileDrawerItem().withName(mDisplayName).withEmail(mDisplayEmail).withIcon(getResources().getDrawable(R.drawable.account_m))
                )
                .withHeaderBackground(R.drawable.nav)
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .build();

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.menu_home);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.menu_categories);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.menu_cart);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.menu_notif);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.menu_terms);
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.menu_offers);
        PrimaryDrawerItem item7 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.menu_help);
        PrimaryDrawerItem item8 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.menu_settings);
        PrimaryDrawerItem item9 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.menu_rate);
        PrimaryDrawerItem item10 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.menu_share);
        PrimaryDrawerItem item11 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.support);

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountheader)
                .addDrawerItems(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    switch (position) {
                        case 1:
                            if (result != null && result.isDrawerOpen()) {
                                result.closeDrawer();
                            }
                            Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_LONG).show();
                            break;
                        case 2:
                            Toast.makeText(HomeActivity.this, "Categories", Toast.LENGTH_LONG).show();
                            break;
                        case 3:
                            Toast.makeText(HomeActivity.this, "Cart", Toast.LENGTH_LONG).show();
                            break;
                        case 4:
                            Toast.makeText(HomeActivity.this, "Notifications", Toast.LENGTH_LONG).show();
                            break;
                        case 5:
                            Toast.makeText(HomeActivity.this, "Terms and Conditions", Toast.LENGTH_LONG).show();
                            break;
                        case 6:
                            Toast.makeText(HomeActivity.this, "Offers", Toast.LENGTH_LONG).show();
                            break;
                        case 7:
                            Toast.makeText(HomeActivity.this, "Help", Toast.LENGTH_LONG).show();
                            break;
                        case 8:
                            startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                            Toast.makeText(HomeActivity.this, "Settings", Toast.LENGTH_LONG).show();
                            break;
                        case 9:
                            Toast.makeText(HomeActivity.this, "Rate us", Toast.LENGTH_LONG).show();
                            break;
                        case 10:
                            Toast.makeText(HomeActivity.this, "Refer Us", Toast.LENGTH_LONG).show();
                            break;
                        case 11:
                            startActivity(new Intent(HomeActivity.this, SupportActivity.class));
                            Toast.makeText(HomeActivity.this, "Support", Toast.LENGTH_LONG).show();
                            break;
                    }

                    return true;
                }).build();




    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sliderLayout.stopAutoCycle();
        adapter.stopListening();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout root;
        public TextView txtTitle;
        public TextView txtDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_root);
            txtTitle = itemView.findViewById(R.id.list_title);
            txtDesc = itemView.findViewById(R.id.list_desc);
        }

        public void setTxtTitle(String string) {
            txtTitle.setText(string);
        }


        public void setTxtDesc(String string) {
            txtDesc.setText(string);
        }
    }


}
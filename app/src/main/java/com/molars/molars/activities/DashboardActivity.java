package com.molars.molars.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.molars.molars.R;
import com.molars.molars.adapters.MainMenuAdapter;
import com.molars.molars.models.Menu;
import com.molars.molars.views.BtnSemiBold;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Menu> menus;
    private BtnSemiBold btnBookAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initViews();

        initListeners();

        initMenuItems();

        initMenu();
    }

    private void initViews() {
        btnBookAppointment = (BtnSemiBold) findViewById(R.id.btnBookAppointment);
    }

    private void initListeners() {
        btnBookAppointment.setOnClickListener(this);
    }

    private void initMenuItems() {
        menus = new ArrayList<>();
        menus.add(new Menu(R.drawable.icon_about_us, "About Us"));
        menus.add(new Menu(R.drawable.icon_services, "Services"));
        menus.add(new Menu(R.drawable.icon_success_stories, "Success Stories"));
        menus.add(new Menu(R.drawable.icon_videos, "Videos"));
        menus.add(new Menu(R.drawable.icon_contact_us, "Contact Us"));
        menus.add(new Menu(R.drawable.icon_location, "Our Location"));
        menus.add(new Menu(R.drawable.icon_our_team, "Our Team"));
    }

    private void initMenu() {
        RecyclerView listMenu = (RecyclerView) findViewById(R.id.listMenu);
        MainMenuAdapter menuAdapter = new MainMenuAdapter(this, menus);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listMenu.setLayoutManager(layoutManager);
        listMenu.setItemAnimator(new DefaultItemAnimator());

        listMenu.addOnItemTouchListener(new RecyclerTouchListener(this, listMenu, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                switch (position) {
                    case 0:
                        startActivity(AboutUsActivity.class);
                        break;
                    case 1:
                        startActivity(ServicesActivity.class);
                        break;
                    case 2:
                        startActivity(SuccessStoriesActivity.class);
                        break;
                    case 3:
                        startActivity(VideosActivity.class);
                        break;
                    case 4:
                        startActivity(ContactUsActivity.class);
                        break;
                    case 5:
                        startActivity(LocationActivity.class);
                        break;
                    case 6:
                        startActivity(TeamActivity.class);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        listMenu.setAdapter(menuAdapter);
    }

    private void startActivity(Class<?> destination_class) {
        Intent intent = new Intent(DashboardActivity.this, destination_class);
        startActivity(intent);
    }

    /**
     * RecyclerView: Implementing single item click and long press (Part-II)
     * <p>
     * - creating an Interface for single tap and long press
     * - Parameters are its respective view and its position
     */

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    /**
     * RecyclerView: Implementing single item click and long press (Part-II)
     * <p>
     * - creating an innerclass implementing RevyvlerView.OnItemTouchListener
     * - Pass clickListener interface as parameter
     */

    private class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBookAppointment:
                startActivity(BookAppointmentActivity.class);
                break;
            default:
                break;
        }
    }
}

package com.krp.android.viewvisiblewithindevicebounds;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toast toast;

    private Rect scrollBounds;
    private int previousScrollValue;

    private ScrollView scrollView;
    private TextView tvBtn1, tvBtn2, tvBtn3, tvBtn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        tvBtn1 = (TextView) scrollView.findViewById(R.id.btn_1);
        tvBtn2 = (TextView) scrollView.findViewById(R.id.btn_2);
        tvBtn3 = (TextView) scrollView.findViewById(R.id.btn_3);
        tvBtn4 = (TextView) scrollView.findViewById(R.id.btn_4);

        // Creats a DEVICE_VISIBLE_SCREEN   :   BOUNDS
        // to check whether onScroll a certian view is visible or not
        scrollBounds = new Rect();
        scrollView.getHitRect(scrollBounds);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY(); //for verticalScroll
                boolean isVisible = isVisibleToUser(tvBtn1);    // TODO check BUTTON_VISIBILITY w.r.t. VIEW_BOUNDS

                if(previousScrollValue == 0 && scrollY > 0 && !isVisible) {
                    previousScrollValue = scrollY;
                    alert("Button 1 is NOT VISIBLE");

                } else {
                    if(scrollY - previousScrollValue > 0 && !isVisible) { // scrolling down
                        alert("Button 1 is NOT VISIBLE");
                    } else if(scrollY - previousScrollValue < 0 && !isVisible) { // scrolling up
                        alert("Button 1 is VISIBLE");
                    }
                }
            }
        });
    }

    /**
     * Finds whether the current view is visible to the user within the device screen limits
     * @param v
     * @return
     */
    private boolean isVisibleToUser(View v) {
        return v.getLocalVisibleRect(scrollBounds);
    }

    public void alert(String message) {
        if(toast == null) {
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        }
        if(toast.getView().isShown()) {
            toast.cancel();
        }
        toast.setText(message);
        toast.show();
    }
}

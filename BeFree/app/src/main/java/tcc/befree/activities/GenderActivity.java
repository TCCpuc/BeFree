package tcc.befree.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import tcc.befree.R;
/**
 * Created by guilherme.leme on 8/17/17.
 */

public class GenderActivity extends AppCompatActivity {

    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        calendarView = (CalendarView) findViewById(R.id.calendarView);

        calendarView.requestFocus();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
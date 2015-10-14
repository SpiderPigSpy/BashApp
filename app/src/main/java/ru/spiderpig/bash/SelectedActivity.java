package ru.spiderpig.bash;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class SelectedActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);
        //View backButton = findViewById(R.id.back_content_button);
        //backButton.setOnClickListener(this);

        String rating = getIntent().getStringExtra(MainActivity.INTENT_RATING);
        String date = getIntent().getStringExtra(MainActivity.INTENT_DATE);
        String content = getIntent().getStringExtra(MainActivity.INTENT_CONTENT);
        setQuote(rating, date, content);

        View view = findViewById(R.id.selected_activity_view);
        view.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            @Override
            public void onSwipeRight() {
                // Whatever
                System.out.println("onSwipeRight");
                onBackPressed();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selected, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setQuote(String rating, String date, String content) {
        try {
            TextView ratingView = (TextView) findViewById(R.id.textView_rating);
            TextView dateView = (TextView) findViewById(R.id.textView_date);
            TextView contentView = (TextView) findViewById(R.id.textView_content);
            contentView.setMovementMethod(new ScrollingMovementMethod());
            ratingView.setText("Рейтинг: " + rating);
            dateView.setText("Дата: " + date);
            contentView.setText(content);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

package ru.spiderpig.bash;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ru.spiderpig.bashlib.QuoteInterface;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    public static final String INTENT_RATING = "rating";
    public static final String INTENT_DATE = "date";
    public static final String INTENT_CONTENT = "content";

    private ArrayAdapter<String> adapter;
    private BashDownloaderTask loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        ArrayList<String> quoteList = new ArrayList<>(1);
        quoteList.add("Downloading...");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quoteList);

        loader = new BashDownloaderTask(adapter);
        loader.execute();


        ListView listView = (ListView) findViewById(R.id.mainQuotesView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            List<QuoteInterface> indexQuotes = loader.getIndexQuotes();
            QuoteInterface selectedQuote = indexQuotes.get(position);
            Intent intent = new Intent(MainActivity.this, SelectedActivity.class);
            intent.putExtra(INTENT_RATING, String.valueOf(selectedQuote.getRating()));
            intent.putExtra(INTENT_DATE, selectedQuote.getDate().toString());
            intent.putExtra(INTENT_CONTENT, selectedQuote.getText());
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

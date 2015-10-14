package ru.spiderpig.bash;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.spiderpig.bashlib.Bash;
import ru.spiderpig.bashlib.QuoteInterface;

/**
 * Created by alex on 13.12.14.
 */
public class BashDownloaderTask extends AsyncTask<Void, Void, String[]> {

    private static final String[] errorResult = {"Error"};
    private ArrayAdapter<String> adapter;
    private String[] result;
    private List<QuoteInterface> indexQuotes;

    public BashDownloaderTask(ArrayAdapter<String> adapter) {
        this.adapter = adapter;
    }

    public List<QuoteInterface> getIndexQuotes() {
        if (indexQuotes == null) {
            indexQuotes = new ArrayList<>();
        }
        return indexQuotes;
    }

    @Override
    protected String[] doInBackground(Void... params) {
        try {
            String[] result = downloadQuotes();
            return result;
        } catch (Exception ex){
            ex.printStackTrace();
            return errorResult;
        }
    }

    protected String[] downloadQuotes() {
        Bash bash = new Bash();
        String wentToTry = "didn't go to try";
        String errorMessage = "errorMessage";
        try {
            wentToTry = "went To Try";
            indexQuotes = bash.getIndexQuotes();
        } catch (Exception e) {
            indexQuotes = new ArrayList<>();
            errorMessage = e.getMessage() != null ? e.getMessage() : "getMessage=null";
            e.printStackTrace();
        }
        String[] quotes;
        if (indexQuotes.size() > 0) {
            quotes = new String[indexQuotes.size()];
            int i = 0;
            for (QuoteInterface quot : indexQuotes) {
                quotes[i++] = quot.getText().substring(0, 40) + "...";
            }
        } else {
            quotes = new String[4];
            quotes[0] = "Failed to download";
            quotes[1] = "Debug";
            quotes[2] = wentToTry;
            quotes[3] = errorMessage;
        }
        return quotes;
    }

    protected void onPostExecute(String[] result) {
        try {
            adapter.clear();
            adapter.addAll(result);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

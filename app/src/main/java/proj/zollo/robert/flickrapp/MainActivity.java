package proj.zollo.robert.flickrapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GetPhotoList gpl = new GetPhotoList();
        gpl.execute();
    }

    private class GetPhotoList extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            String result = "";
            ArrayList<String> urls = new ArrayList<String>();
            try {
                URL url = new URL("https://api.flickr.com/services/rest/?&method=flickr.photos." +
                        "getRecent&api_key=58db690eeb49d28af5d058b82ab24260&per_page=30" +
                        "&format=json&nojsoncallback=1");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    result = convertStreamToString(in);
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //--------------JSON-----------
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject photos = jsonObject.getJSONObject("photos");
                JSONArray photoArray = photos.getJSONArray("photo");
                int length = photoArray.length();


                for (int i = 0; i < length; i++) {
                    JSONObject out = photoArray.getJSONObject(i);
                    String photoURL = makePhotoUrl(out.getString("farm"), out.getString("server"),
                            out.getString("id"), out.getString("secret"));
                    urls.add(photoURL);
                }
            } catch (JSONException f) {
                f.printStackTrace();
                System.out.println("JSONE " + f.getMessage());
            }
            return urls;
        }

        @Override
        protected void onPostExecute(ArrayList<String> theUrls) {
            final ArrayList<String> urlList = theUrls;
            GridView gv = (GridView) findViewById(R.id.gridView);
            CustomGirdAdapter adapter = new CustomGirdAdapter(MainActivity.this, urlList);
            gv.setAdapter(adapter);

            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    String item = urlList.get(position);
                    Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                    intent.putExtra("image", item);
                    startActivity(intent);
                }
            });
        }
    }


    public static String convertStreamToString(java.io.InputStream is)
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public String makePhotoUrl(String farm, String server, String id, String secret)
    {
        return "https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+".jpg";
    }

//--------------Auto Generated Methods----------

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
        if (id == R.id.refresh) {
            GetPhotoList gpl = new GetPhotoList();
            gpl.execute();
        }

        return super.onOptionsItemSelected(item);
    }
}

package vn.edu.hust.dinhhuyhung.bongda24h;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvGiaidau = (ListView) findViewById(R.id.lvGiaidau);
        GiaidauAdapter adapter = new GiaidauAdapter();
        lvGiaidau.setAdapter(adapter);

        if (isNetworkAvailable()) {
            Toast.makeText(MainActivity.this, "Kết nối internet sẵn sàng", Toast.LENGTH_SHORT).show();
            lvGiaidau.setOnItemClickListener(onListGiaiDauClick);
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("Internet");
            dialog.setMessage("Kiểm tra lại kết nối internet của bạn");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            });
            dialog.show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class GiaidauAdapter extends ArrayAdapter<String> {
        GiaidauAdapter() {
            super(MainActivity.this, android.R.layout.simple_list_item_1, Variables.GIAIDAU);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.clv_main, null);
            }
            ImageView iv = (ImageView) convertView.findViewById(R.id.ivIcon);
            TextView tv = (TextView) convertView.findViewById(R.id.tvGiaidau);
            iv.setImageResource(Variables.ICONS[position]);
            tv.setText(Variables.GIAIDAU[position]);
            return convertView;
        }
    }

    AdapterView.OnItemClickListener onListGiaiDauClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            int key = i;
            if (Variables.newsMap.get(key) == null) {
                dialog = ProgressDialog.show(MainActivity.this, "", "Loading " + Variables.GIAIDAU[i]);
                dialog.setCancelable(true);
                new MainTask().execute(i);
            } else {
                Intent intent = new Intent(MainActivity.this, GiaiDau.class);
                intent.putExtra("magiai", i);
                startActivity(intent);
            }
        }
    };

    class MainTask extends AsyncTask<Integer, Void, Void> {
        private int position;

        @Override
        protected Void doInBackground(Integer... params) {
            position = params[0];
            int key = position;
            RssParser parser = new RssParser();
            List<RssItem> items = parser.getNewsList(Variables.LINKS[position]);
            //Log.d("myLog", "Size: " + items.size());
            Variables.newsMap.put(key, items);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (dialog != null) {
                dialog.dismiss();
            }
            Intent intent = new Intent(MainActivity.this, GiaiDau.class);
            intent.putExtra("magiai", position);
            startActivity(intent);
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("About");
                dialog.setMessage("Bóng Đá 24h version 1.0"
                                + "\n\nFootball News from bongda24h.vn"
                                + "\n\nMe: "
                                + "\t huyhung5577@gmail.com"
                );
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                dialog.show();
                return true;
            case R.id.action_reload:
                Variables.newsMap = new HashMap<Integer, List<RssItem>>();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

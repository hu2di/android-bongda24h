package vn.edu.hust.dinhhuyhung.bongda24h;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dinh Huy Hung on 5/16/2015.
 */
public class GiaiDau extends ActionBarActivity {
    private int giaidau;
    private List<RssItem> items = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giaidau);
        Bundle bundle = getIntent().getExtras();
        giaidau = bundle.getInt("magiai");
        setTitle(Variables.GIAIDAU[giaidau]);

        int key = giaidau;
        items = Variables.newsMap.get(key);

        ListView lvTintuc = (ListView) findViewById(R.id.lvTintuc);
        TintucAdapter adapter = new TintucAdapter();
        lvTintuc.setAdapter(adapter);
        lvTintuc.setOnItemClickListener(onListTinTucClick);
    }

    AdapterView.OnItemClickListener onListTinTucClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(GiaiDau.this, DocTin.class);
            intent.putExtra("link", items.get(i).getLink());
            intent.putExtra("magiai", giaidau);
            startActivity(intent);

        }
    };

    public class TintucAdapter extends ArrayAdapter<RssItem> {
        TintucAdapter() {
            super(GiaiDau.this, android.R.layout.simple_list_item_1, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = GiaiDau.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.clv_giaidau, null);
            }
            ImageView iv = (ImageView) convertView.findViewById(R.id.ivImage);
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            iv.setImageResource(Variables.ICONS[giaidau]);
            tvTitle.setText(items.get(position).getTitle().toString());
            tvDate.setText(items.get(position).getDate().toString());
            return convertView;
        }
    }
}

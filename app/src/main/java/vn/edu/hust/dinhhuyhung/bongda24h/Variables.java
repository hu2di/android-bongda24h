package vn.edu.hust.dinhhuyhung.bongda24h;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dinh Huy Hung on 5/16/2015.
 */
public class Variables {
    public static final String[] GIAIDAU = {"Hot News", "Champions League", "Premier League", "V-League", "La Liga", "Serie A", "Ligue 1"};

    public static final int[] ICONS = {R.drawable.ic_tinnong,
            R.drawable.ic_uefa, R.drawable.ic_epl, R.drawable.ic_vleague, R.drawable.ic_laliga,
            R.drawable.ic_seriea, R.drawable.ic_ligue1};

    public static final String[] LINKS = {
            "http://bongda24h.vn/RSS/279.rss",
            "http://bongda24h.vn/RSS/184.rss",
            "http://bongda24h.vn/RSS/172.rss",
            "http://bongda24h.vn/RSS/168.rss",
            "http://bongda24h.vn/RSS/193.rss",
            "http://bongda24h.vn/RSS/176.rss",
            "http://bongda24h.vn/RSS/197.rss"
    };

    public static HashMap<Integer, List<RssItem>> newsMap = new HashMap<Integer, List<RssItem>>();
}

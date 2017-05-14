package scot.wildcamping.wildswap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import java.util.ArrayList;
import scot.wildcamping.wildswap.Adapters.TradeOwnedSitesAdapter;
import scot.wildcamping.wildswap.Adapters.TradeUnknownSitesAdapter;
import scot.wildcamping.wildswap.AsyncTask.AsyncResponse;
import scot.wildcamping.wildswap.AsyncTask.CreateNotification;
import scot.wildcamping.wildswap.AsyncTask.FetchUsers;
import scot.wildcamping.wildswap.AsyncTask.UpdateTrade;
import scot.wildcamping.wildswap.Objects.Site;
import scot.wildcamping.wildswap.Objects.StoredData;
import scot.wildcamping.wildswap.Objects.User;

public class TradeView extends AppCompatActivity {

    PagerAdapter ownedSites;
    PagerAdapter unknownSites;

    ViewPager unknownPage;
    ViewPager ownedPage;

    StoredData inst = new StoredData();
    SparseArray<Site> ownedMap = inst.getOwnedSitesMap();
    SparseArray<Site> unknownMap = inst.getUnknownSitesMap();
    SparseArray<Site> ownedSite = new SparseArray<>();
    SparseArray<Site> unknownSite = new SparseArray<>();
    SparseArray<Site> knownSite = new SparseArray<>();
    SparseArray<User> dealers = new SparseArray<>();
    ArrayList<String> emails = new ArrayList<>();
    User trader;

    String recieve_token;

    Site recieveSite;
    String send_cid;
    String recieve_cid;
    String unique_tid;
    int status;
    String date;
    int negativeTradeStatus = 1;
    int PositiveTradeStatus = 2;
    Boolean sent = false;
    Boolean received = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_view_sent);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            sent = extras.getBoolean("sent");
            received = extras.getBoolean("received");
            unique_tid = extras.getString("unique_tid");
            send_cid = extras.getString("send_cid");
            recieve_cid = extras.getString("recieve_cid");
            date = extras.getString("date");
            status = extras.getInt("status");
        }

        if(status == 2){
            //findKnownSite();
            unknownPage = (ViewPager)findViewById(R.id.unknownSiteViewPager);
            ownedPage = (ViewPager)findViewById(R.id.ownedSiteViewPager);

            findOwnedSite();
            findKnownSite();

            new FetchUsers(this, emails, new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    dealers = inst.getDealers();
                    ownedSites = new TradeOwnedSitesAdapter(getApplicationContext(), ownedSite);
                    ownedPage.setAdapter(ownedSites);

                    unknownSites = new TradeUnknownSitesAdapter(getApplicationContext(), knownSite, dealers);
                    unknownPage.setAdapter(unknownSites);
                }
            }).execute();

        } else {

            unknownPage = (ViewPager)findViewById(R.id.unknownSiteViewPager);
            ownedPage = (ViewPager)findViewById(R.id.ownedSiteViewPager);

            findOwnedSite();
            findUnknownSite();

            new FetchUsers(this, emails, new AsyncResponse() {
                @Override
                public void processFinish(String output) {

                    dealers = inst.getDealers();
                    ownedSites = new TradeOwnedSitesAdapter(getApplicationContext(), ownedSite);
                    ownedPage.setAdapter(ownedSites);

                    unknownSites = new TradeUnknownSitesAdapter(getApplicationContext(), unknownSite, dealers);
                    unknownPage.setAdapter(unknownSites);
                }
            }).execute();

            System.out.println("dealers size "+dealers.size());

            recieve_token = unknownSite.get(0).getToken();
        }

        //trader = dealers.get(0);
        //recieve_token = trader.getToken();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        if(status == 2 || status == 3){
            inflater.inflate(R.menu.inactive_trade, menu);
        } else {

            if (sent) {
                inflater.inflate(R.menu.sent_trade, menu);
            } else if (received) {
                inflater.inflate(R.menu.received_trade, menu);
            }
        }
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:

                finish();
                return true;

            case R.id.action_cancel:

                //update trade record in db
                if(isNetworkAvailable()) {
                    final Intent intent = new Intent(getApplicationContext(),
                            MainActivity_Spinner.class);
                    new UpdateTrade(this, unique_tid, negativeTradeStatus, new AsyncResponse() {
                        @Override
                        public void processFinish(String output) {
                            startActivity(intent);
                            finish();
                        }
                    }).execute();
                }


                break;

            case R.id.action_accept:
                if(isNetworkAvailable()) {
                    final Intent intent = new Intent(getApplicationContext(),
                            MainActivity_Spinner.class);

                    //update trade record in db positively
                    //create new entry in user_has_trades with relat 45
                    new UpdateTrade(this, unique_tid, PositiveTradeStatus, new AsyncResponse() {
                        @Override
                        public void processFinish(String output) {

                            BadgeManager bM = new BadgeManager(TradeView.this);
                            bM.checkTradeBadges();

                            new CreateNotification(TradeView.this, recieve_token).execute();
                            startActivity(intent);
                            finish();
                        }
                    }).execute();
                }

                break;

            case R.id.action_contact:

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("mailto:"));
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{recieveSite.getSiteAdmin()});
                i.putExtra(Intent.EXTRA_SUBJECT, "Wild Swap - Trade");
                i.putExtra(Intent.EXTRA_TEXT, "Hello fellow wild camper, I am contacting you because...");
                startActivity(i);
                break;

            case R.id.profile:
                //open that user's profile
                if(isNetworkAvailable()) {
                    //new FetchQuestions(this, emails.get(0)).execute();
                }

                Intent in = new Intent(getApplicationContext(), ProfileActivity.class);
                in.putExtra("email", emails.get(0));
                in.putExtra("this_user", false);
                startActivity(in);
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    public void findUnknownSite(){
        if(sent) {
            //loop through all unknown sites and find the site that has been selected to trade
            for (int i = 0; i < unknownMap.size(); i++) {
                if (recieve_cid.equals(unknownMap.get(i).getCid())) {
                    unknownSite.put(0, unknownMap.get(i));
                    emails.add(0, unknownSite.get(0).getSiteAdmin());
                    System.out.println("unknown site found");
                    break;
                }
            }
        } else if (received) {

            //loop through all unknown sites and find the site that has been selected to trade
            for (int i = 0; i < unknownMap.size(); i++) {
                if (send_cid.equals(unknownMap.get(i).getCid())) {
                    unknownSite.put(0, unknownMap.get(i));
                    emails.add(0, unknownSite.get(0).getSiteAdmin());
                    System.out.println("unknown site found");
                    break;
                }
            }
        }

        emails.add(0, unknownSite.get(0).getSiteAdmin());

    }

    public void findOwnedSite(){
        if(sent) {

            //loop through all known sites and find the site that has been offered for trading
            for (int i = 0; i < ownedMap.size(); i++) {
                if (send_cid.equals(ownedMap.get(i).getCid())) {
                    ownedSite.put(0, ownedMap.get(i));
                    System.out.println("owned site found");
                    break;
                }
            }
        } else if(received) {

            //loop through all known sites and find the site that has been offered for trading
            for (int i = 0; i < ownedMap.size(); i++) {
                if (recieve_cid.equals(ownedMap.get(i).getCid())) {
                    ownedSite.put(0, ownedMap.get(i));
                    System.out.println("owned site found");
                    break;
                }
            }
        }
    }

    public void findKnownSite(){

        if(sent) {

            //loop through all known sites and find the site that was offered for trading
            for (int i = 0; i < ownedMap.size(); i++) {
                if (send_cid.equals(ownedMap.get(i).getCid())) {
                    knownSite.put(0, ownedMap.get(i));
                    System.out.println("known site found");
                    break;
                }
            }
        } else if(received) {

            //loop through all known sites and find the site that was offered for trading
            for (int i = 0; i < ownedMap.size(); i++) {
                if (recieve_cid.equals(ownedMap.get(i).getCid())) {
                    knownSite.put(0, ownedMap.get(i));
                    System.out.println("known site found");
                    break;
                }
            }
        }

        emails.add(0, knownSite.get(0).getSiteAdmin());
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
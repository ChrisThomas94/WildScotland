package scot.wildcamping.wildswap;

import android.content.Context;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import scot.wildcamping.wildswap.AsyncTask.AsyncResponse;
import scot.wildcamping.wildswap.AsyncTask.UpdateBadges;
import scot.wildcamping.wildswap.Objects.Site;
import scot.wildcamping.wildswap.Objects.StoredData;
import scot.wildcamping.wildswap.Objects.StoredTrades;
import scot.wildcamping.wildswap.Objects.User;

/**
 * Created by Chris on 01-May-17.
 *
 */

public class BadgeManager {

    private Context context;
    StoredData inst = new StoredData();
    private StoredTrades trading = new StoredTrades();
    private User thisUser = inst.getLoggedInUser();
    private ArrayList<Integer> badges = thisUser.getBadges();
    private ArrayList<Integer> existingBadges = thisUser.getBadges();
    Boolean update = false;

    public BadgeManager(Context context){
        this.context = context;
        System.out.println("im in badge manager");

        badges.set(0, 1);
    }


    public void checkSiteBadges(){
        int ownedSize = inst.getOwnedSiteSize();

        if(ownedSize >= 1 && ownedSize < 2){
            //unlock badge_2
            badges.set(1,1);
            update = true;
        } else if (ownedSize >= 2 && ownedSize < 5){
            //unlock badge_3
            badges.set(1,1);
            badges.set(2,1);
            update = true;

        } else if (ownedSize >= 5 && ownedSize < 10){
            //unlock badge_4
            badges.set(1,1);
            badges.set(2,1);
            badges.set(3,1);
            update = true;

        } else if (ownedSize >= 10){
            //unlock badge_5
            badges.set(1,1);
            badges.set(2,1);
            badges.set(3,1);
            badges.set(4,1);
            update = true;
        } else {
            badges.set(1,0);
            badges.set(2,0);
            badges.set(3,0);
            badges.set(4,0);
            update = true;
        }

        thisUser.setBadges(badges);

        if(update && !badges.equals(existingBadges)){
            updateBadges();
        }
    }

    public void checkTradeBadges(){
        int trades = trading.getAcceptedTradesSize();

        if(trades >= 1 && trades <5){
            //unlock badge_6
            badges.set(5,1);
            update = true;

        } else if (trades >= 5 && trades <10 ){
            //unlock badge_7
            badges.set(5,1);
            badges.set(6,1);
            update = true;

        } else if (trades >=10 && trades < 20){
            //unlock badge_8
            badges.set(5,1);
            badges.set(6,1);
            badges.set(7,1);
            update = true;

        } else if (trades >= 20){
            //unlock badge_9
            badges.set(5,1);
            badges.set(6,1);
            badges.set(7,1);
            badges.set(8,1);
            update = true;
        } else {
            badges.set(5,0);
            badges.set(6,0);
            badges.set(7,0);
            badges.set(8,0);
            update = true;
        }

        thisUser.setBadges(badges);
        if(update && !badges.equals(existingBadges)){
            updateBadges();
        }
    }

    public void checkGiftedBadges(){
        int gifted = thisUser.getNumGifted();

        if(gifted >= 1 && gifted < 5){
            // unlock badge 10
            badges.set(9, 1);
            update = true;

        } else if(gifted >= 5 && gifted < 10){
            // unlock badge 11
            badges.set(9,1);
            badges.set(10,1);
            update = true;

        } else if(gifted >= 10 && gifted < 20){
            // unlock badge 12
            badges.set(9,1);
            badges.set(10,1);
            badges.set(11,1);
            update = true;

        } else if(gifted >= 20){
            // unlock badge 13
            badges.set(9,1);
            badges.set(10,1);
            badges.set(11,1);
            badges.set(12,1);
            update = true;
        } else {
            badges.set(9,0);
            badges.set(10,0);
            badges.set(11,0);
            badges.set(12,0);
            update = true;
        }

        thisUser.setBadges(badges);
        if(update && !badges.equals(existingBadges)){
            updateBadges();
        }
    }

    public void checkReportedBadges(){
        int reports = thisUser.getNumReported();

        if(reports >= 1 && reports < 5){
            //unlock badge 14
            badges.set(13,1);
            update = true;

        } else if(reports >= 5 && reports <10){
            //unlock badge 15
            badges.set(13,1);
            badges.set(14,1);
            update = true;

        } else if(reports >= 10 && reports < 20){
            //unlock badge 16
            badges.set(13,1);
            badges.set(14,1);
            badges.set(15,1);
            update = true;

        } else if(reports >= 20){
            //unlock badge 17
            badges.set(13,1);
            badges.set(14,1);
            badges.set(15,1);
            badges.set(16,1);
            update = true;
        } else {
            badges.set(13,0);
            badges.set(14,0);
            badges.set(15,0);
            badges.set(16,0);
            update = true;
        }

        thisUser.setBadges(badges);
        if(update && !badges.equals(existingBadges)){
            updateBadges();
        }
    }

    public void checkContributorBadges(){

        thisUser.setBadges(badges);

    }

    public void checkCountryBadges(){
        SparseArray<Site> ownedSites = inst.getOwnedSitesMap();
        List<String> countries = new ArrayList<>();

        for(int i= 0; i<ownedSites.size(); i++){
            countries.add(i, ownedSites.get(i).getAddress().get(0).getCountryName());
        }

        Set<String> uniqueCountries = new HashSet<>(countries);

        int unique = uniqueCountries.size();

        if(unique >= 1 && unique <= 2){
            //unlock badge 22
            badges.set(21,1);
            update = true;

        } else if(unique >= 5 && unique < 10){
            //unlock badge 23
            badges.set(21,1);
            badges.set(22,1);
            update = true;

        } else if (unique >= 10 && unique < 15){
            //unlock badge 24
            badges.set(21,1);
            badges.set(22,1);
            badges.set(23,1);
            update = true;

        } else if(unique >= 15){
            //unlock badge 25
            badges.set(21,1);
            badges.set(22,1);
            badges.set(23,1);
            badges.set(24,1);
            update = true;
        } else {
            badges.set(21,0);
            badges.set(22,0);
            badges.set(23,0);
            badges.set(24,0);
            update = true;
        }

        thisUser.setBadges(badges);
        if(update && !badges.equals(existingBadges)){
            updateBadges();
        }
    }

    private void updateBadges(){
        /*new UpdateBadges(context, true, new AsyncResponse() {
            @Override
            public void processFinish(String output) {

            }
        }).execute();*/

    }
}
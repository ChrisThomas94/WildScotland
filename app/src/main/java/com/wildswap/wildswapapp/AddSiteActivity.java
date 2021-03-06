package com.wildswap.wildswapapp;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.wildswap.wildswapapp.Adapters.ImageGridAdapter;
import com.wildswap.wildswapapp.Adapters.ImageUriGridAdapter;
import com.wildswap.wildswapapp.AsyncTask.AsyncResponse;
import com.wildswap.wildswapapp.AsyncTask.AddSite;
import com.wildswap.wildswapapp.AsyncTask.UpdateSite;
import com.wildswap.wildswapapp.Objects.Gallery;
import com.wildswap.wildswapapp.Objects.Site;
import com.wildswap.wildswapapp.Objects.StoredData;

public class AddSiteActivity extends AppCompatActivity implements View.OnClickListener {

    EditText Lat;
    EditText Lon;
    EditText title;
    EditText description;
    RelativeLayout addImage;
    RelativeLayout siteBuilder;
    RelativeLayout addFeature;
    RelativeLayout addedImages;
    TextView or;
    ImageView distantTerrainFeatures;
    ImageView nearbyTerrainFeatures;
    ImageView immediateTerrainFeatures;
    TextView classificationA;
    TextView classificationC;
    TextView classificationE;
    ImageView tent;
    ImageView camper;
    ImageView bothy;
    FrameLayout tentFrame;
    FrameLayout camperFrame;
    FrameLayout bothyFrame;
    TextView suitedDescription;
    RatingBar ratingBar;
    CheckBox imagePermission;
    ViewGroup.LayoutParams layoutParams;
    GridView gridView;
    GridView updateGridView;
    FrameLayout classA;
    FrameLayout classC;
    FrameLayout classE;
    TextView classDescription;
    ProgressBar progress;
    TextView progressText;

    String cid;
    double latitude;
    double longitude;
    String latReq;
    String lonReq;
    String titleReq;
    String descReq;
    String ratingReq;
    float rating;
    String distant;
    String nearby;
    String immediate;
    Boolean permission;
    int RESULT_LOAD_IMAGE = 0;
    Boolean feature1;
    Boolean feature2;
    Boolean feature3;
    Boolean feature4;
    Boolean feature5;
    Boolean feature6;
    Boolean feature7;
    Boolean feature8;
    Boolean feature9;
    Boolean feature10;
    String classification;
    String suited;
    String titlePassed;
    String descPassed;
    int relat = 90;
    Boolean imageUpload = false;
    StoredData inst = new StoredData();
    ArrayList imageUris2 = new ArrayList();
    int progressValue;

    Boolean updateClassification = false;
    Boolean updateSuited = false;
    Boolean updateTitle = false;
    Boolean updateDesc = false;
    Boolean updateImage = false;
    Boolean showDialog = false;
    Boolean updateLatLon = false;
    Boolean update = false;
    Boolean manual = false;
    int arrayPos;

    Geocoder geocoder;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());


        //initializing views
        Lat =(EditText)findViewById(R.id.Lat);
        Lon = (EditText)findViewById(R.id.Long);
        title = (EditText)findViewById(R.id.title);
        description = (EditText)findViewById(R.id.description);
        addImage = (RelativeLayout)findViewById(R.id.addPhotoRel);
        siteBuilder = (RelativeLayout)findViewById(R.id.siteBuilder);
        addFeature = (RelativeLayout)findViewById(R.id.addFeaturesRel);
        imagePermission = (CheckBox)findViewById(R.id.imagePermission);
        addedImages = (RelativeLayout)findViewById(R.id.addedImages);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        progress = (ProgressBar)findViewById(R.id.progressBar);
        progressText = (TextView) findViewById(R.id.progressText);
        updateGridView = (GridView) findViewById(R.id.updateGridView);
        gridView = (GridView) findViewById(R.id.gridView);
        or = (TextView)findViewById(R.id.or);
        distantTerrainFeatures = (ImageView)findViewById(R.id.distantTerrainFeatures);
        nearbyTerrainFeatures = (ImageView)findViewById(R.id.nearbyTerrainFeatures);
        immediateTerrainFeatures = (ImageView)findViewById(R.id.immediateTerrainFeatures);
        classificationA = (TextView) findViewById(R.id.classificationA);
        classificationC = (TextView) findViewById(R.id.classificationC);
        classificationE = (TextView) findViewById(R.id.classificationE);
        classA = (FrameLayout) findViewById(R.id.classificationAFrame);
        classC = (FrameLayout) findViewById(R.id.classificationCFrame);
        classE = (FrameLayout) findViewById(R.id.classificationEFrame);
        classDescription = (TextView)findViewById(R.id.classificationDescription);
        tentFrame = (FrameLayout) findViewById(R.id.tentFrame);
        camperFrame = (FrameLayout) findViewById(R.id.camperFrame);
        bothyFrame = (FrameLayout) findViewById(R.id.bothyFrame);
        suitedDescription = (TextView) findViewById(R.id.suitedDescription);

        Lat.setEnabled(false);
        Lon.setEnabled(false);

        distantTerrainFeatures.setVisibility(View.GONE);
        nearbyTerrainFeatures.setVisibility(View.GONE);
        immediateTerrainFeatures.setVisibility(View.GONE);

        classification = classificationA.getText().toString();
        suited = "tent";

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            update = extras.getBoolean("update");
            manual = extras.getBoolean("manual");
            arrayPos = extras.getInt("arrayPosition");

            latitude = extras.getDouble("latitude");
            longitude = extras.getDouble("longitude");

            String lat = Double.toString(latitude);
            String lon = Double.toString(longitude);

            Lat.setText(lat);
            Lon.setText(lon);

            titlePassed = extras.getString("title");
            descPassed = extras.getString("description");
            imageUpload = extras.getBoolean("image");

            if(imageUpload && !update) {
                //imageUris = extras.getStringArray("images");
                imageUris2 = extras.getStringArrayList("images");
                addedImages.setVisibility(View.VISIBLE);
            }

            //rating = extras.getFloat("rating");

            //float ratingFloat = (float)rating;
            //ratingBar.setRating(rating);

            //distant = extras.getString("distantTerrain");
            //nearby = extras.getString("nearbyTerrain");
            //immediate = extras.getString("immediateTerrain");

            /*if(distant != null && nearby != null && immediate != null){
                updateProgress();
                int distantID = this.getResources().getIdentifier("drawable/"+ distant, null, this.getPackageName());
                int nearbyID = this.getResources().getIdentifier("drawable/"+ nearby, null, this.getPackageName());
                int immediateID = this.getResources().getIdentifier("drawable/"+ immediate, null, this.getPackageName());

                distantTerrainFeatures.setImageResource(distantID);
                nearbyTerrainFeatures.setImageResource(nearbyID);
                immediateTerrainFeatures.setImageResource(immediateID);

                distantTerrainFeatures.setVisibility(View.VISIBLE);
                nearbyTerrainFeatures.setVisibility(View.VISIBLE);
                immediateTerrainFeatures.setVisibility(View.VISIBLE);

                addImage.setVisibility(View.GONE);
                or.setVisibility(View.GONE);
            }*/

            /*feature1 = extras.getBoolean("feature1");
            feature2 = extras.getBoolean("feature2");
            feature3 = extras.getBoolean("feature3");
            feature4 = extras.getBoolean("feature4");
            feature5 = extras.getBoolean("feature5");
            feature6 = extras.getBoolean("feature6");
            feature7 = extras.getBoolean("feature7");
            feature8 = extras.getBoolean("feature8");
            feature9 = extras.getBoolean("feature9");
            feature10 = extras.getBoolean("feature10");

            if (feature1 || feature2 || feature3 || feature4 || feature5 || feature6 || feature7 || feature8 || feature9 || feature10) {
                addFeature.setBackgroundResource(R.drawable.rounded_green_button);
            } else {
                addFeature.setBackgroundResource(R.drawable.rounded_grey_button);
            }*/
        }

        if(update){

            getSupportActionBar().setTitle("Update Site");

            Site focused = inst.getOwnedSitesMap().get(arrayPos);
            title.setText(focused.getTitle());
            updateProgress();

            description.setText(focused.getDescription());
            updateProgress();

            SparseArray<Gallery> images = inst.getImages();
            cid = focused.getCid();
            String id = cid.substring(cid.length()-8);
            int cidEnd = Integer.parseInt(id);
            Gallery gallery = images.get(cidEnd);
            ArrayList<String> imagesList = gallery.getGallery();

            if(imageUpload){

                updateProgress();

                System.out.println("images list 0 "+imagesList.get(0));

                ImageGridAdapter adapter = new ImageGridAdapter(this, R.layout.grid_item_layout, imagesList);

                int imagesNum = imagesList.size();

                System.out.println("clip data: " + imagesList.size());
                layoutParams = updateGridView.getLayoutParams();
                int row = 3*(Math.round(imagesNum/3));
                System.out.println("row: " + row);
                int dif = (imagesNum%3);

                System.out.println("dif" + dif);

                if (dif ==1){
                    row = row+3;
                } else if (dif ==2){
                    row = row+3;
                }

                layoutParams.height = (row*105);

                System.out.println("height" + layoutParams.height);

                updateGridView.setLayoutParams(layoutParams);
                updateGridView.setAdapter(adapter);

                updateImage = true;
                addedImages.setVisibility(View.VISIBLE);

                siteBuilder.setVisibility(View.GONE);
                or.setVisibility(View.GONE);
            }

            classification = focused.getClassification();
            updateProgress();

            if(classification.equals(classificationA.getText())){
                classA.getForeground().setAlpha(0);
                classC.getForeground().setAlpha(150);
                classE.getForeground().setAlpha(150);
                classDescription.setText(R.string.amateurClassification);

            } else if(classification.equals(classificationC.getText())){
                classC.getForeground().setAlpha(0);
                classA.getForeground().setAlpha(150);
                classE.getForeground().setAlpha(150);
                classDescription.setText(R.string.casualClassification);

            } else if(classification.equals(classificationE.getText())){
                classE.getForeground().setAlpha(0);
                classC.getForeground().setAlpha(150);
                classA.getForeground().setAlpha(150);
                classDescription.setText(R.string.expertClassification);
            }

            suited = focused.getSuited();
            updateProgress();

            switch (suited){
                case "tent":
                    tentFrame.getForeground().setAlpha(0);
                    camperFrame.getForeground().setAlpha(150);
                    bothyFrame.getForeground().setAlpha(150);
                    suitedDescription.setText(R.string.suitedTent);
                    break;

                case "camper":
                    tentFrame.getForeground().setAlpha(150);
                    camperFrame.getForeground().setAlpha(0);
                    bothyFrame.getForeground().setAlpha(150);
                    suitedDescription.setText(R.string.suitedCamper);
                    break;

                case "bothy":
                    tentFrame.getForeground().setAlpha(150);
                    camperFrame.getForeground().setAlpha(150);
                    bothyFrame.getForeground().setAlpha(0);
                    suitedDescription.setText(R.string.suitedBothy);
                    break;
            }

            //position
            latitude = focused.getPosition().latitude;
            longitude = focused.getPosition().longitude;

            String lat = Double.toString(latitude);
            String lon = Double.toString(longitude);

            Lat.setText(lat);
            Lon.setText(lon);

        } else {

            if(manual){
                Lat.setClickable(true);
                Lon.setClickable(true);
                Lat.setEnabled(true);
                Lon.setEnabled(true);
            }

            if(titlePassed != null){
                title.setText(titlePassed);
            }

            if(descPassed != null){
                description.setText(descPassed);
            }

            if(imageUpload) {

                ImageUriGridAdapter adapter = new ImageUriGridAdapter(this, R.layout.grid_item_layout, imageUris2);
                gridView.setAdapter(adapter);

                siteBuilder.setVisibility(View.GONE);
                or.setVisibility(View.GONE);
            }

            classA.getForeground().setAlpha(0);
            classC.getForeground().setAlpha(150);
            classE.getForeground().setAlpha(150);
            classification = classificationA.getText().toString();
            classDescription.setText(R.string.amateurClassification);

            tentFrame.getForeground().setAlpha(0);
            camperFrame.getForeground().setAlpha(150);
            bothyFrame.getForeground().setAlpha(150);
            suitedDescription.setText(R.string.suitedTent);

            if(!updateSuited){
                updateProgress();
            }
            updateSuited = true;

            if(!updateClassification){
                updateProgress();
            }
            updateClassification = true;
        }


        //setting onclick listeners
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!updateTitle) {
                    updateProgress();
                }
                updateTitle = true;
            }
        });

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!updateDesc) {
                    updateProgress();
                }
                updateDesc = true;
            }
        });

        addImage.setOnClickListener(this);
        siteBuilder.setOnClickListener(this);
        addFeature.setOnClickListener(this);
        progress.setOnClickListener(this);
        classificationA.setOnClickListener(this);
        classificationC.setOnClickListener(this);
        classificationE.setOnClickListener(this);
        tentFrame.setOnClickListener(this);
        camperFrame.setOnClickListener(this);
        bothyFrame.setOnClickListener(this);
        Lon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        switch (v.getId())
        {
            case R.id.Long:
                if(!updateLatLon){
                    updateProgress();
                }
                updateLatLon = true;
                break;

            case R.id.classificationA:

                if(!updateClassification){
                    updateProgress();
                }
                updateClassification = true;
                classification = classificationA.getText().toString();
                classA.getForeground().setAlpha(0);
                classC.getForeground().setAlpha(150);
                classE.getForeground().setAlpha(150);
                classDescription.setText(R.string.amateurClassification);
                break;

            case R.id.classificationC:

                if(!updateClassification){
                    updateProgress();
                }
                updateClassification = true;
                classification = classificationC.getText().toString();
                classA.getForeground().setAlpha(150);
                classC.getForeground().setAlpha(0);
                classE.getForeground().setAlpha(150);
                classDescription.setText(R.string.casualClassification);
                break;

            case R.id.classificationE:

                if(!updateClassification){
                    updateProgress();
                }
                updateClassification = true;
                classification = classificationE.getText().toString();
                classA.getForeground().setAlpha(150);
                classC.getForeground().setAlpha(150);
                classE.getForeground().setAlpha(0);
                classDescription.setText(R.string.expertClassification);
                break;

            case R.id.tentFrame:

                if(!updateSuited){
                    updateProgress();
                }

                updateSuited = true;
                suited = "tent";
                tentFrame.getForeground().setAlpha(0);
                camperFrame.getForeground().setAlpha(150);
                bothyFrame.getForeground().setAlpha(150);
                suitedDescription.setText(R.string.suitedTent);

                break;

            case R.id.camperFrame:

                if(!updateSuited){
                    updateProgress();
                }

                updateSuited = true;
                suited = "camper";
                tentFrame.getForeground().setAlpha(150);
                camperFrame.getForeground().setAlpha(0);
                bothyFrame.getForeground().setAlpha(150);
                suitedDescription.setText(R.string.suitedCamper);

                break;

            case R.id.bothyFrame:

                if(!updateSuited){
                    updateProgress();
                }

                updateSuited = true;
                suited = "bothy";
                tentFrame.getForeground().setAlpha(150);
                camperFrame.getForeground().setAlpha(150);
                bothyFrame.getForeground().setAlpha(0);
                suitedDescription.setText(R.string.suitedBothy);

                break;

            case R.id.addPhotoRel:
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

                break;

            case R.id.siteBuilder:

                Intent siteBuilder = new Intent(getApplicationContext(), SiteBuilderActivity.class);
                siteBuilder.putExtra("image", imageUpload);
                siteBuilder.putExtra("latitude", latitude);
                siteBuilder.putExtra("longitude", longitude);
                siteBuilder.putExtra("title", title.getText().toString());
                siteBuilder.putExtra("description", description.getText().toString());
                siteBuilder.putExtra("rating", ratingBar.getRating());
                siteBuilder.putExtra("progress", progressValue);
                startActivity(siteBuilder);
                break;

            case R.id.addFeaturesRel:

                Intent intent = new Intent(getApplicationContext(), FeaturesActivity.class);
                intent.putExtra("image", imageUpload);
                //intent.putExtra("temp", tempLocation);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("title", title.getText().toString());
                intent.putExtra("description", description.getText().toString());
                intent.putExtra("feature1", feature1);
                intent.putExtra("feature2", feature2);
                intent.putExtra("feature3", feature3);
                intent.putExtra("feature4", feature4);
                intent.putExtra("feature5", feature5);
                intent.putExtra("feature6", feature6);
                intent.putExtra("feature7", feature7);
                intent.putExtra("feature8", feature8);
                intent.putExtra("feature9", feature9);
                intent.putExtra("feature10", feature10);
                intent.putExtra("rating", ratingBar.getRating());
                intent.putExtra("images", imageUris2);
                startActivity(intent);
                break;

            case R.id.progressBar:

                if(progressValue > 0) {

                    if(manual){
                        latitude = Double.parseDouble(Lat.getText().toString());
                        longitude = Double.parseDouble(Lon.getText().toString());

                        try {
                            List<Address> address = geocoder.getFromLocation(latitude, longitude, 1);
                            System.out.println("address: " + address);

                            if (address.isEmpty() || (Lat.getText().equals("0.0")) || Lon.getText().equals("0.0")) {
                                Snackbar.make(v, "Please enter a valid latitude and longitude!", Snackbar.LENGTH_LONG).show();
                            } else {
                                submit();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if(update) {
                        update();
                    } else {
                        submit();
                    }


                } else {
                    Snackbar.make(v, "Please make some changes!", Snackbar.LENGTH_LONG).show();
                }

                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {

            ClipData clip = data.getClipData();
            if(!updateImage) {
                updateProgress();
            }
            updateImage = true;
            addedImages.setVisibility(View.VISIBLE);

            if (clip == null) {
                Uri uri = data.getData();
                imageUris2.add(0, uri.toString());

            } else {

                System.out.println("clip data: " + clip.getItemCount());

                layoutParams = gridView.getLayoutParams();
                int row = 3*(Math.round(clip.getItemCount()/3));

                System.out.println("row: " + row);

                int dif = (clip.getItemCount()%3);

                if (dif ==1){
                    row = row+3;
                } else if (dif ==2){
                    row = row+3;
                }

                layoutParams.height = (row*105);

                System.out.println("height" + layoutParams.height);


                gridView.setLayoutParams(layoutParams);

                for (int i = 0; i < clip.getItemCount(); i++) {
                    ClipData.Item item = clip.getItemAt(i);

                    Uri uri = item.getUri();
                    imageUris2.add(i,uri.toString());
                }

            }
        }

        ImageUriGridAdapter adapter = new ImageUriGridAdapter(this, R.layout.grid_item_layout, imageUris2);
        gridView.setAdapter(adapter);

        siteBuilder.setVisibility(View.GONE);
        or.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {

        if(update){
            Toast.makeText(getApplicationContext(), "Update canceled!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Site creation canceled!", Toast.LENGTH_LONG).show();
        }

        intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("add", false);
        intent.putExtra("data", false);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case android.R.id.home:

                if(update){
                    Toast.makeText(getApplicationContext(), "Update canceled!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Site creation canceled!", Toast.LENGTH_LONG).show();
                }

                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("add", false);
                intent.putExtra("data", false);
                startActivity(intent);
                finish();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    public void updateProgress(){

        if(manual){
            progressValue = progress.getProgress()+20;
        } else {
            progressValue = progress.getProgress()+25;
        }

        progress.setProgress(progressValue);

        if(progressValue >= 100){
            progressText.setText("GO");
        } else {
            progressText.setText(progressValue+"% Complete");
        }

    }

    public void update(){

        titleReq = title.getText().toString();
        descReq = description.getText().toString();

        titleReq = titleReq.replace("'", "\'");
        descReq = descReq.replace("'", "\'");

        new UpdateSite(this, true, true, cid, titleReq, descReq, classification, "0", imageUris2, new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("update", true);
                startActivity(intent);
                finish();
            }
        }).execute();
    }

    public void submit(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddSiteActivity.this);
        builder1.setTitle("Attention!");
        builder1.setMessage(R.string.addSiteSubmit);

        builder1.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                latReq = Lat.getText().toString();
                lonReq = Lon.getText().toString();
                titleReq = title.getText().toString();
                descReq = description.getText().toString();
                ratingReq = Float.toString(ratingBar.getRating());
                permission = imagePermission.isChecked();

                if (!latReq.isEmpty() && !lonReq.isEmpty() && !titleReq.isEmpty() && !descReq.isEmpty() && !ratingReq.isEmpty()) {

                    titleReq = titleReq.replace("'", "\'");
                    descReq = descReq.replace("'", "\'");

                    final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    intent.putExtra("add", true);
                    intent.putExtra("data", true);
                    intent.putExtra("image", updateImage);

                    System.out.println("imageupload"+updateImage);

                    new AddSite(AddSiteActivity.this, relat, latReq, lonReq, titleReq, descReq, classification, suited, ratingReq, permission, distant, nearby, immediate, feature1, feature2, feature3, feature4, feature5, feature6, feature7, feature8, feature9, feature10, imageUris2, new AsyncResponse() {
                        @Override
                        public void processFinish(String output) {

                            startActivity(intent);
                            finish();
                        }
                    }).execute();
                }
            }
        });

        builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert1 = builder1.create();
        alert1.show();
    }
}
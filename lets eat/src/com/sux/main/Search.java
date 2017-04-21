package com.sux.main;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;

import android.util.Log;
import android.view.*;
import android.widget.*;
import com.sux.exceptions.ExceptionManager;
import com.sux.utility.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shorty on 5/2/2014.
 */
public class Search extends ListActivity implements SearchBar {

    DataAsync result;
    ListView listView;
    TextView queryView, matchesView ,pageNumView;

    String[] recipeNames;
    ArrayList<Result> recipeResults;
    SearchAdapter adapter;

    String query;

    int currentPage = 1;
    int maxResults = 10;
    int start = ((currentPage - 1) * maxResults);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        handleIntent(getIntent());

        listView = getListView();
        queryView = (TextView)findViewById(R.id.query);
        matchesView = (TextView)findViewById(R.id.matches);
        pageNumView = (TextView)findViewById(R.id.pageNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        configSearchBar(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String recipeID = recipeResults.get(position).getId();
        LetsEat.loadRecipe(Search.this, recipeID);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if(intent.hasExtra("Start")){
                start = intent.getIntExtra("Start", 0);
            }
            doSearch(query, start, maxResults);
        }
    }

    private void doSearch(String queryStr, int start, int maxResults) {
        result = (DataAsync)new DataAsync(this).execute(DataAsync.SEARCH_ASYNC, queryStr, start + "" , maxResults + "");
        query = queryStr;
    }

    public void handleSearchResults(JSONObject result) {
        queryView.setText("Results for '" + query + "'");
        matchesView.setText(result.get(Yummly.TOTAL_MATCHES).toString() + " total results");
        pageNumView.setText("Page "+ currentPage);

        recipeNames = new String[maxResults];
        recipeResults = getResults(result);
        adapter = new SearchAdapter();
        adapter.processPendingImages();
        setListAdapter(adapter);

    }

    public ArrayList<Result> getResults(JSONObject result){
        ArrayList<Result> list = new ArrayList<>();
        JSONArray matches = (JSONArray)result.get(Yummly.MATCHES);
        JSONArray images = (JSONArray)result.get(Yummly.IMAGE_KEY);

        for(int i = 0; i < matches.size(); i++){
            JSONObject json = (JSONObject)matches.get(i);

            String name = json.get(Yummly.RECIPE_NAME).toString();
            String attribution = json.get(Yummly.SOURCE_NAME).toString();
            String id = json.get(Yummly.ID).toString();

            String image = null;
            if(images != null) image = images.get(i).toString();

            list.add(new Result(name, attribution, id, image));
        }
        return list;
    }

    public void next(View view){
        Intent intent = new Intent(Intent.ACTION_SEARCH, null, this, Search.class);
        intent.putExtra(SearchManager.QUERY, query);
        intent.putExtra("Start", (start + maxResults));
        startActivity(intent);
    }

    public void previous(View view){
        finish();
    }

    public class SearchAdapter extends ArrayAdapter<Result>{
        private ArrayList<Bitmap> pendingImages;
        private ArrayList<ImageView> pendingViews;

        public SearchAdapter() {
            super(Search.this, R.layout.search_item, recipeResults);
            pendingImages = new ArrayList<>();
            pendingViews = new ArrayList<>();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) convertView = getLayoutInflater().inflate(R.layout.search_item, parent, false);

            Result currentResult = recipeResults.get(position);

            TextView recipeName = (TextView)convertView.findViewById(R.id.title);
            recipeName.setText(currentResult.getTitle());

            TextView attribution = (TextView)convertView.findViewById(R.id.subTitle);
            attribution.setText("Brought to you by: " + currentResult.getSubTitle() + "!");

            ImageView imageView = (ImageView)findViewById(R.id.recipeImage);
            pendingViews.add(imageView);

            if(currentResult.getImage() != null) {
                Bitmap image = Encoder.decodeBitmap(currentResult.getImage());
                pendingImages.add(image);
            } else {
                pendingImages.add(null);
            }
            return convertView;
        }

        public void processPendingImages(){
            for(int i = 0; i < pendingImages.size(); i++){
                pendingViews.get(i).setImageDrawable(new BitmapDrawable(getResources(), pendingImages.get(i)));

                /*View child = listView.getChildAt(i);
                ImageView childImage = (ImageView)child.findViewById(R.id.recipeImage);
                childImage.setImageDrawable(new BitmapDrawable(getResources(), pendingImages.get(i)));*/
            }

            /*for(ImageView i : pendingImageViews){
                for(Bitmap b: pendingImages){
                    i.setImageDrawable(new BitmapDrawable(getResources(), b));
                }
            }*/
        }
    }
}
package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
RecyclerView recyclerview;
List<Article> articleList = new ArrayList<>();
NeewsRecyclerAdapter adapter;
SearchView searchView;
LinearProgressIndicator progressIndicator;
Button btn1, btn2,btn3,btn4,btn5,btn6,btn7;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = findViewById(R.id.search_viewbar);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4  = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        progressIndicator = findViewById(R.id.progress_bar);
        recyclerview= findViewById(R.id.news_recucyler_view);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               getNews("GENERAL",query);
               return true;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });
        setRecyclerview();
        getNews("GENERAL",null);
    }

    void setRecyclerview(){

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NeewsRecyclerAdapter(articleList);
        recyclerview.setAdapter(adapter);
    }
    void changeInProgress(boolean show ){
        if (show){
            progressIndicator.setVisibility(View.VISIBLE);

        }else{
            progressIndicator.setVisibility(View.VISIBLE);

        }

    }


    void getNews(String category, String query){
        changeInProgress(true);
        NewsApiClient newsApiClient= new NewsApiClient("9202518a3b524cf3a9b94d14d304e2fa");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .category(category)
                        .q(query)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onSuccess(ArticleResponse response) {
                       runOnUiThread(()->{
                           changeInProgress(false);
                       articleList = response.getArticles();
                       adapter.updateData(articleList);
                       adapter.notifyDataSetChanged();
                       });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                      Log.i("GOT FAILURE",throwable.getMessage());
                    }
                }
        );
    }


    @Override
    public void onClick(View v) {
Button btn =(Button ) v;
       String category = btn.getText().toString();
       getNews(category,null);


    }
}
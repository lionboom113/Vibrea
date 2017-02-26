package enquilear.enquilear.com.vibrea;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enquilear.enquilear.com.vibrea.Adapter.ArticleAdapter;
import enquilear.enquilear.com.vibrea.Model.ArticleWrapper;
import enquilear.enquilear.com.vibrea.Model.Doc;
import enquilear.enquilear.com.vibrea.Model.NYTimesAPI;
import enquilear.enquilear.com.vibrea.Model.SortConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleListActivity extends AppCompatActivity {
    @BindView(R.id.gvArticles)
    RecyclerView articles;
    Call<ArticleWrapper> caller;
    NYTimesAPI NYApiService;
    int homePage = 1;
    String searchStr = "";
    List<Doc> mDoc;
    ArticleAdapter adapter;
    EndlessRecyclerViewScrollListener endlessLoad;
    @Override
    protected void onPostResume() {
        super.onPostResume();
        adapter.clearData();
        homePage = 1;
        search(searchStr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);
        homePage = 1;
        Log.d("tuan","begin query - oncreate");
        mDoc = new ArrayList<Doc>();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        NYTimesAPI apiService =
                retrofit.create(NYTimesAPI.class);
        NYApiService = apiService;
        adapter = new ArticleAdapter(getApplicationContext(), mDoc);
        articles.setAdapter(adapter);
        StaggeredGridLayoutManager sgLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        articles.setLayoutManager(sgLayoutManager);
        endlessLoad = new EndlessRecyclerViewScrollListener(sgLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                homePage = homePage + 1;
                Log.d("tuan","page++ :"+ homePage);
                search(searchStr);
            }
        };
        articles.addOnScrollListener(endlessLoad);

    }

    public void ApplySearch(View view) {
        search(searchStr);
    }
    private void search(String text){
        Call<ArticleWrapper> call;
        if (text == null || text.trim().equals("")) {
            call =  NYApiService.findArticleAll(SortConfig.startDate, SortConfig.getHelpdesk(), SortConfig.sortType, homePage);
        } else {
            call = NYApiService.findArticle(text, SortConfig.startDate, SortConfig.getHelpdesk(), SortConfig.sortType, homePage);
        }

        call.enqueue(new Callback<ArticleWrapper>() {
            @Override
            public void onResponse(Call<ArticleWrapper> call, final Response<ArticleWrapper> response) {
                int statusCode = response.code();
                final ArticleWrapper resultWrapper = response.body();
                if (resultWrapper == null){
                    return;
                }
                endlessLoad.resetState();
                if (adapter.currPage < homePage){
                    adapter.addItems(resultWrapper.getDocs());
                }
                Log.d("tuan","tuan page:"+ homePage);
            }
            @Override
            public void onFailure(Call<ArticleWrapper> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_action_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchStr = query;
                searchView.clearFocus();
                homePage = 1;
                Log.d("tuan","begin query");
                adapter.clearData();
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                Intent intent = new Intent(ArticleListActivity.this, SortDefineActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

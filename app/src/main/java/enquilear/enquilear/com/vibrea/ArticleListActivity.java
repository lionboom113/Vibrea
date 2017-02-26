package enquilear.enquilear.com.vibrea;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import enquilear.enquilear.com.vibrea.Adapter.ArticleAdapter;
import enquilear.enquilear.com.vibrea.Model.ArticleWrapper;
import enquilear.enquilear.com.vibrea.Model.Doc;
import enquilear.enquilear.com.vibrea.Model.NYTimesAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleListActivity extends AppCompatActivity {
    @BindView(R.id.gvArticles)
    GridView articles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
//        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        NYTimesAPI apiService =
                retrofit.create(NYTimesAPI.class);
        Call<ArticleWrapper> call = apiService.findArticle("trump");
        call.enqueue(new Callback<ArticleWrapper>() {
            @Override
            public void onResponse(Call<ArticleWrapper> call, Response<ArticleWrapper> response) {
                int statusCode = response.code();
                final ArticleWrapper resultWrapper = response.body();
                ArrayAdapter<Doc> adapter = new ArticleAdapter(getApplicationContext(),R.layout.article_adapter, resultWrapper.getDocs());

                articles.setAdapter(adapter);
                articles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
//                        intent.putExtra("movieId",resultWrapper.getResults().get(i).getId());
//                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ArticleWrapper> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }
}

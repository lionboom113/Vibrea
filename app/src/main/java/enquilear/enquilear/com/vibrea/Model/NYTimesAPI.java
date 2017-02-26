package enquilear.enquilear.com.vibrea.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Tuan on 2017/02/26.
 */

public interface NYTimesAPI {
    @GET("svc/search/v2/articlesearch.json?apikey=7fe086c436c64864822e01f52e029cde")
    Call<ArticleWrapper> findArticleAll(@Query("begin_date") String beginDate,@Query("fq") String categories, @Query("sort")String sortType);
    @GET("svc/search/v2/articlesearch.json?apikey=7fe086c436c64864822e01f52e029cde")
    Call<ArticleWrapper> findArticle(@Query("q") String query,@Query("begin_date") String beginDate,@Query("fq") String categories, @Query("sort")String sortType);
}

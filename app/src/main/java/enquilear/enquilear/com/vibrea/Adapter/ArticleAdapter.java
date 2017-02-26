package enquilear.enquilear.com.vibrea.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enquilear.enquilear.com.vibrea.Model.Doc;
import enquilear.enquilear.com.vibrea.R;
import enquilear.enquilear.com.vibrea.WebViewActivity;

/**
 * Created by Tuan on 2017/02/26.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private List<Doc> mDocs;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public ArticleAdapter(Context context, List<Doc> docs) {
        mDocs = docs;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    private void setContentToViewHolder(ViewHolder vh, Doc article) {
        if (article.getMultimedia().size() > 0) {
            setImageToImageViewFromPath(vh.articleImage, article.getMultimedia().get(0).getUrl());
        }
        vh.snippet.setText(article.getSnippet());
    }

    private void setImageToImageViewFromPath(ImageView iv, String path) {
        Glide.with(getContext()).load("http://www.nytimes.com/" + path).fitCenter().placeholder(R.drawable.placeholder).into(iv);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.article_adapter, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // Get the data model based on position
        Doc doc = mDocs.get(position);

        // Set item views based on your views and data model
        TextView tvSnippet = holder.snippet;
        tvSnippet.setText(doc.getSnippet());
        ImageView ivArticle = holder.articleImage;
        if (doc.getMultimedia().size() > 0){
            setImageToImageViewFromPath(ivArticle,doc.getMultimedia().get(0).getUrl());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();

//                Uri uri = Uri.parse( mDocs.get(position).getWeb_url());
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                getContext().startActivity(intent);

                Uri uri = Uri.parse( mDocs.get(position).getWeb_url());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDocs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvImageView)
        public ImageView articleImage;
        @BindView(R.id.tvArticleTitle)
        public TextView snippet;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

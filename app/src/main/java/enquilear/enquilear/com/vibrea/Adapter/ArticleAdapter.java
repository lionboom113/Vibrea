package enquilear.enquilear.com.vibrea.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enquilear.enquilear.com.vibrea.Model.Doc;
import enquilear.enquilear.com.vibrea.R;

/**
 * Created by Tuan on 2017/02/26.
 */

public class ArticleAdapter extends ArrayAdapter<Doc> {
    @BindView(R.id.tvImageView)
    ImageView articleImage;
    @BindView(R.id.tvArticleTitle)
    TextView snippet;
    public ArticleAdapter(Context context, int resource, List<Doc> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        Doc article = getItem(position);
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.article_adapter,null);
            ButterKnife.bind(this,v);
            ViewHolder vh = new ViewHolder();
            vh.articleImage = articleImage;
            vh.snippet = snippet;
            v.setTag(vh);
            setContentToViewHolder(vh, article);
        } else {
            ViewHolder vh = (ViewHolder) v.getTag();
            setContentToViewHolder(vh, article);
        }
        return v;
    }
    private void setContentToViewHolder(ViewHolder vh, Doc article){
        if (article.getMultimedia().size() > 0){
            setImageToImageViewFromPath(vh.articleImage,article.getMultimedia().get(0).getUrl());
        }
        //vh.snippet.setText(article.getSnippet());
    }
    private void setImageToImageViewFromPath(ImageView iv, String path) {
        Glide.with(getContext()).load("http://www.nytimes.com/" + path).placeholder(R.drawable.placeholder).into(iv);
    }
    private class ViewHolder{
        public ImageView articleImage;
        public TextView snippet;
    }
}

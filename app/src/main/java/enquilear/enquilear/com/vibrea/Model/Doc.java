package enquilear.enquilear.com.vibrea.Model;

import java.util.List;

/**
 * Created by Tuan on 2017/02/25.
 */

public class Doc {
    private String snippet;
    private List<Multimedia> multimedia;
    private String web_url;

    public String getSnippet() {
        return snippet;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }
}

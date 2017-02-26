package enquilear.enquilear.com.vibrea.Model;

import java.util.List;

/**
 * Created by Tuan on 2017/02/26.
 */

public class ArticleWrapper {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
    public List<Doc> getDocs(){
        return response.getDocs();
    }
    public void setDocs(List<Doc> docs){
        response.setDocs(docs);
    }

    public class Response{
        private List<Doc> docs;

        public List<Doc> getDocs() {
            return docs;
        }

        public void setDocs(List<Doc> docs) {
            this.docs = docs;
        }
    }
}

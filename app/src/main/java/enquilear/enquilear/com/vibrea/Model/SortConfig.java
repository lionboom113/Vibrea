package enquilear.enquilear.com.vibrea.Model;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Tuan on 2017/02/26.
 */

public class SortConfig {
    public static String startDate = "20000101";
    public static String sortType = "newest";
    public static List<String> categories = new ArrayList<String>();
    public static String getHelpdesk(){
        if (categories.size() == 0){
            return "null";
        }
        StringBuilder sb = new StringBuilder("news_desk:(");
        for (String category: categories) {
            sb.append("\""+category+"\" ");
        }
        sb.append(")");
        return sb.toString();
    }
}

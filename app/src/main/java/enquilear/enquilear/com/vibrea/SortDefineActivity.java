package enquilear.enquilear.com.vibrea;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enquilear.enquilear.com.vibrea.Fragment.DatePickerFragment;
import enquilear.enquilear.com.vibrea.Model.SortConfig;

public class SortDefineActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    @BindView(R.id.etDatePicker)
    EditText etDatePicker;
    @BindView(R.id.spSortType)
    Spinner spSortType;
    String datePickerResult;
    @BindView(R.id.cbArt)
    CheckBox cbArt;
    @BindView(R.id.cbFashion)
    CheckBox cbFashion;
    @BindView(R.id.cbSport)
    CheckBox cbSport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_define);
        ButterKnife.bind(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSortType.setAdapter(adapter);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        etDatePicker.setText(dateFormat.format(c.getTime()));
        dateFormat = new SimpleDateFormat("yyyyMMdd");
        datePickerResult = dateFormat.format(c.getTime());
    }

    public void showDatePickerDialog(View v){
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");

    }
    public void saveButtonClick(View v){
        SortConfig.startDate = datePickerResult;
        SortConfig.sortType = spSortType.getSelectedItemPosition() == 0 ? "oldest" : "newest";
        List<String> listCategory = new ArrayList<String>();
        if (cbArt.isChecked()){
            listCategory.add("Art");
        }
        if (cbFashion.isChecked()){
            listCategory.add("Fashion");
        }
        if (cbSport.isChecked()){
            listCategory.add("Sport");
        }
        SortConfig.categories = listCategory;
        finish();
    }

}

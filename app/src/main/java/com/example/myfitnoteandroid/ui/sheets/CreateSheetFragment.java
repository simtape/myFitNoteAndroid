package com.example.myfitnoteandroid.ui.sheets;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;
import com.example.myfitnoteandroid.data.sheets_data.Sheet;
import com.example.myfitnoteandroid.data.sheets_data.SheetExercise;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CreateSheetFragment extends Fragment implements View.OnClickListener {

    private CreateSheetViewModel mViewModel;

    private EditText[] exercises = new EditText[4];
    private EditText[] reps = new EditText[4];
    private EditText[] series = new EditText[4];
    private EditText nameSheet;
    private CheckBox[] checkBoxes = new CheckBox[7];
    private Button createSheetBtn;


    private Sheet newSheet;
    ViewGroup root;


    public static CreateSheetFragment newInstance() {
        return new CreateSheetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = (ViewGroup) inflater.inflate(R.layout.create_sheet_fragment, container, false);

        exercises[0] = root.findViewById(R.id.exercises_tv);
        exercises[1] = root.findViewById(R.id.exercises_tv2);
        exercises[2] = root.findViewById(R.id.exercises_tv3);
        exercises[3] = root.findViewById(R.id.exercises_tv4);

        reps[0] = root.findViewById(R.id.editTextReps1);
        reps[1] = root.findViewById(R.id.editTextReps2);
        reps[2] = root.findViewById(R.id.editTextReps3);
        reps[3] = root.findViewById(R.id.editTextReps4);

        series[0] = root.findViewById(R.id.editTextSeries1);
        series[1] = root.findViewById(R.id.editTextSeries2);
        series[2] = root.findViewById(R.id.editTextSeries3);
        series[3] = root.findViewById(R.id.editTextSeries4);

        checkBoxes[0] = root.findViewById(R.id.checkBox1);
        checkBoxes[1] = root.findViewById(R.id.checkBox2);
        checkBoxes[2] = root.findViewById(R.id.checkBox3);
        checkBoxes[3] = root.findViewById(R.id.checkBox4);
        checkBoxes[4] = root.findViewById(R.id.checkBox5);
        checkBoxes[5] = root.findViewById(R.id.checkBox6);
        checkBoxes[6] = root.findViewById(R.id.checkBox7);


        createSheetBtn = root.findViewById(R.id.button_create_sheet);

        nameSheet = root.findViewById(R.id.name_sheet);

        createSheetBtn.setOnClickListener(this);

        return root;


    }

    // @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (v.getId() == createSheetBtn.getId()) {
            //postData = createSheet();
            insertSheet();

        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CreateSheetViewModel.class);
        // TODO: Use the ViewModel
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    public JSONObject createSheet() {
        JSONObject newSheetJO = new JSONObject();
        SessionManager sessionManager = new SessionManager(getContext());

        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //LocalDateTime now = LocalDateTime.now();

        // newSheet.setDate(dtf.format(now));
        // newSheet.setDate("dtf.format(now)");
        List<String> days = new ArrayList<>();
        String name, date, id;
        name = nameSheet.getText().toString().trim();
        date = "prova";
        id = sessionManager.getSession();

        for (int i = 0; i < 7; i++) {
            if (checkBoxes[i].isChecked()) {
                days.add(checkBoxes[i].getText().toString());
//                Log.d("checked", days.get(i));
            }

        }

        List<SheetExercise> exercises = getExercises();
        newSheet = new Sheet(name, id, exercises, days, date);

        getExercises();

        try {
            newSheetJO.put("name_sheet", newSheet.getName());
            newSheetJO.put("days", newSheet.getDays());
            newSheetJO.put("user_id", newSheet.getId());
            newSheetJO.put("exercises", newSheet.getNamesExercises());
            newSheetJO.put("reps", newSheet.getReps());
            newSheetJO.put("series", newSheet.getSeries());
            //newSheetJO.put("date", newSheet.getDate());

        } catch (JSONException e) {
            e.printStackTrace();

        }

        Log.d("json object created", newSheetJO.toString());
        return newSheetJO;
    }


    public List<SheetExercise> getExercises() {
        SheetExercise exercise;
        List<SheetExercise> exerciseList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {

            String nameExercise = exercises[i].getText().toString().trim();
            String seriesExercise = series[i].getText().toString().trim();
            String repsExercise = reps[i].getText().toString().trim();

            exercise = new SheetExercise(nameExercise, seriesExercise, repsExercise);
            exerciseList.add(exercise);

        }
        return exerciseList;

    }

    private void insertSheet() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JSONObject postData;
        postData = createSheet();

        Log.d("dichiarato postdata", "ok");
        String url = "https://myfitnote.herokuapp.com/sheets/create_sheet";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
                    Log.d("onResponse", "ok");

                    if (response.getBoolean("error")) {
                        Toast.makeText(getContext(), "Hai già usato il nome" + newSheet.getName(), Toast.LENGTH_SHORT).show();

                    } else {
                        Log.d("sono qui", "else ");
                        Toast.makeText(getContext(), "Scheda inserita correttamente!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        queue.add(jsonObjectRequest);


    }

}
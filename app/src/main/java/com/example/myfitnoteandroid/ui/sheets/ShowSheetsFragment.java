package com.example.myfitnoteandroid.ui.sheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ShowSheetsFragment extends Fragment {
    private JSONObject postData, sheetsJson;
    private JSONArray sheetsJsonArray;

    @Override
    public void onStart() {
        super.onStart();
        this.getSheets();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.show_sheets_fragment, container, false);
        TextView textView = root.findViewById(R.id.text_fragment);
        textView.setText("Non hai ancora creato nessuna scheda!");

        return root;
    }

    private void getSheets() {

        postData = new JSONObject();
        final SessionManager sessionManager = new SessionManager(getActivity());

        try {
            postData.put("user_id", sessionManager.getSession());
            System.out.print("ora passo l'id dello user loggato" + sessionManager.getSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://myfitnote.herokuapp.com/sheets/get_sheets";

    }
}
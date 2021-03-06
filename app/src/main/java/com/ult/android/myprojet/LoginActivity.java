package com.ult.android.myprojet;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
        private EditText Email, Password;
        private Button btLogin;
        private RequestQueue requestQueue;
        private static final String URL = "http://10.0.3.2/php/user.php";
        private StringRequest request ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = (EditText)findViewById(R.id.edEmail);
        Password = (EditText)findViewById(R.id.edPassword);
        Button breg = (Button) findViewById(R.id.btRegister);
        Button bcnx = (Button) findViewById(R.id.btLogin);
        requestQueue = Volley.newRequestQueue(this);

        bcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try {
                           JSONObject jsonObject = new JSONObject(response);
                           if(jsonObject.names().get(0).equals("success")){
                               Toast.makeText(getApplicationContext(),"SUCCESS"+jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                              startActivity(new Intent(getApplicationContext(),ListActivity.class));
                           }else {
                               Toast.makeText(getApplicationContext(),"Error"+jsonObject.getString("error"),Toast.LENGTH_SHORT).show();
                           }

                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {

                   }
               }){
                   @Override
               protected Map<String, String> getParams() throws AuthFailureError{
                       HashMap<String,String> hashMap = new HashMap<String, String>();
                       hashMap.put("email",Email.getText().toString());
                       hashMap.put("password",Password.getText().toString());
                       return hashMap;
                   }

               };

                requestQueue.add(request);
            }
        });

        breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ireg = new Intent(getApplicationContext(), InscriptionActivity.class);
                startActivity(ireg);
            }
        });


    }
}
package com.example.emrullah.currency_converter_app;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tryText;
    TextView cadText;
    TextView usdText;
    TextView jpyText;
    TextView chfText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tryText=findViewById(R.id.textView);
        cadText=findViewById(R.id.textView2);
        usdText=findViewById(R.id.textView3);
        jpyText=findViewById(R.id.textView4);
        chfText=findViewById(R.id.textView5);
    }

    public void getRates(View view){
        DownloadData downloadData= new DownloadData();

        try {
            String url= "http://data.fixer.io/api/latest?access_key=38a2b94d9b5abf540bb8f8d9977fef1f&format=1";
            downloadData.execute(url);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private class DownloadData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String results ="";
            URL url ;
            HttpURLConnection httpURLConnection;
            try {
                url= new URL(strings[0]);
                httpURLConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader= new InputStreamReader(inputStream);
                int data= inputStreamReader.read();

                while (data > 0) {
                    char character = (char) data;
                    results+=character;
                    data= inputStreamReader.read();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return results;

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //System.out.println("Alınan Data: "+s);
             try{
                 JSONObject jsonObject=new JSONObject(s);
                 String base=jsonObject.getString("base");
                 //System.out.println("Base:"+base);

                 String rates=jsonObject.getString("rates");
                 //System.out.println("Rates:"+rates);

                 JSONObject jsonObject1=new JSONObject(rates);
                 String turkish= jsonObject1.getString("TRY");
                 String canada= jsonObject1.getString("CAD");
                 String usd = jsonObject1.getString("USD");
                 String jpy = jsonObject1.getString("JPY");
                 String chf = jsonObject1.getString("CHF");

                 tryText.setText("TRY :"+turkish);
                 cadText.setText("CAD :"+canada);
                 usdText.setText("USD :"+usd);
                 jpyText.setText("JPY :"+jpy);
                 chfText.setText("CHF :"+chf);

             }catch (Exception e){
                 e.printStackTrace();
             }
        }
    }
}

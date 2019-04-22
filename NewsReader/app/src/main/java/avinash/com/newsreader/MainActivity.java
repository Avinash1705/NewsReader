package avinash.com.newsreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter arrayAdapter;
   public SQLiteDatabase sqLiteDatabase;
   ArrayList<String> title=new ArrayList<>();
    ArrayList<String> content=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listView_structure);
        arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        // getting client id

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),Article.class);
                intent.putExtra("Content",content.get(position));
                startActivity(intent);
            }
        });
        sqLiteDatabase=this.openOrCreateDatabase("Articles Table",MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS articles(id INTEGER PRIMARY KEY ,articleId INTEGER,title VARCHAR,content VARCHAR)");

        updateListView();
        // to run
        DownloadTask task=new DownloadTask();
        try {
            task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateListView(){
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM articles",null);
        int contentIndex=c.getColumnIndex("content");
        int titleIndex=c.getColumnIndex("title");
        if(c.moveToFirst()){
            // if from stating then clear
            content.clear();
            title.clear();

            do{
                //Setting array of ontent and tile
                title.add(c.getString(titleIndex));
                content.add(c.getString(contentIndex));
            }while (c.moveToNext());

            // updating array adapter that data has been changed
            arrayAdapter.notifyDataSetChanged();
        }
    }
    public class DownloadTask extends AsyncTask<String, Void,String>{

        private String TAG="raw";

        @Override
        protected String doInBackground(String... strings) {
            //Default value set
            String result="";
            URL url;
            HttpURLConnection urlConnection=null;

            //getting values
            try {
                url=new URL(strings[0]);
                urlConnection=(HttpURLConnection) url.openConnection();

                // For Entering data

                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);

                //data variable to loop through input reader

                int data=reader.read();
                while (data!=-1){
                    char current=(char)data;
                    result+=current; //appening data to result
                    data=reader.read();//data variable to read next data
                }

                //Loading in Json Array to seperate
                try {
                    JSONArray jsonArray=new JSONArray(result);
                    int noOfItems=20;
                    if(jsonArray.length()<20){
                        noOfItems=jsonArray.length();
                    }

                    sqLiteDatabase.execSQL("DELETE FROM articles");
                    for(int i=0;i<20/*jsonArray.length()*/;i++){
                 //       Log.i(TAG, "JSON ARray "+jsonArray.getString(i));
                        String articleId=jsonArray.getString(i);
                        url=new URL("https://hacker-news.firebaseio.com/v0/item/"+articleId+".json?print=pretty"); //Loading url
                   //setting up connection
                        urlConnection =(HttpURLConnection) url.openConnection();
                        in=urlConnection.getInputStream();
                        reader=new InputStreamReader(in);

                        data=reader.read();
                        String articleInfo="";
                        while (data!=-1){
                            char current=(char)data;
                            articleInfo+=current;
                            data=reader.read();
                            }

                      //  Log.i(TAG, "Article Info "+articleInfo);

                        // arranging in json format
                        JSONObject jsonObject=new JSONObject(articleInfo);

                        if(!jsonObject.isNull(("title"))  && !jsonObject.isNull(("url"))){
                            Log.i(TAG, "Working till article content");
                            String articleTitle=jsonObject.getString(("title"));
                            String articleURl=jsonObject.getString(("url"));
                            Log.i(TAG, "Title and Url "+articleTitle + ""+articleURl);
                            url=new URL(articleURl); //Loading url
                            //setting up connection
                            urlConnection =(HttpURLConnection) url.openConnection();
                            in=urlConnection.getInputStream();
                            reader=new InputStreamReader(in);
                            Log.i(TAG, "data reading");
                            data=reader.read();
                            String articleContent="";
                            while (data!=-1){
                                char current=(char)data;
                                articleContent+=current;
                                data=reader.read();
                            }
                            Log.i(TAG, "Article Content "+articleContent);

                            //Data insertion in Sqlitetable
                            String sql="INSERT INTO articles(articleId, title, content) VALUES(?, ?, ?)";
                            SQLiteStatement sqLiteStatement=sqLiteDatabase.compileStatement(sql);
                            Log.i(TAG, "Data binding");
                            //linking or binding data
                            sqLiteStatement.bindString(1,articleId);
                            sqLiteStatement.bindString(2,articleTitle);
                            sqLiteStatement.bindString(3,articleContent);
                            Log.i(TAG, "data execute");
                            sqLiteStatement.execute();
                        }

                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



               // Log.i(TAG, "Url Content "+result);
                return result; //return answer of strings

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
// Run when process in download task complete
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateListView();
        }
    }
}

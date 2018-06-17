package com.example.ahnch.crawlingpractice01;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class DetailActivity extends AppCompatActivity {

    Button btnSecond;
    TextView Name,Num;

    private String htmlPageUrl = "http://www.kpug.kr/kpugfreeboard"; //파싱할 홈페이지의 URL주소
    private TextView textviewHtmlDocument;
    private String htmlContentInStringFormat="";
    private ArrayList<String> kpug_list;
    private ArrayAdapter<String> kpugAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

         textviewHtmlDocument = (TextView)findViewById(R.id.listdetail);
         textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod()); //스크롤 가능한 텍스트뷰로 만들기

        //Button htmlTitleButton = (Button)findViewById(R.id.button);
//        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println( (cnt+1) +"번째 파싱");
//                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
//                jsoupAsyncTask.execute();
//                cnt++;
//            }
//        });

        //btnSecond = (Button)findViewById(R.id.btnBack);
        //Name = (TextView)findViewById(R.id.charactername) ;
        //Num = (TextView)findViewById(R.id.characternumber);

        Intent it = new Intent(this.getIntent());

        String sListDetailNo = it.getStringExtra("ListDetailNo");

        //Name.setText(it.getStringExtra("Name"));
        //Num.setText(it.getStringExtra("PhoneNo"));

        System.out.println( sListDetailNo +"번째줄");

        htmlPageUrl = "http://www.kpug.kr";

        //htmlPageUrl += "/2346789";

        htmlPageUrl +=  sListDetailNo;

        System.out.println( htmlPageUrl +"사이트");

        kpug_list = new ArrayList<String>();
        kpugAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kpug_list);

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();

        //ArrayList<String> kpug_list = new ArrayList<String>();

        //kpug_list.add("자유게시판");
        //kpug_list.add("한줄메모");

        // 어뎁터 준비
        // ArrayAdapter<String> kpugAdapter;
        // kpugAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kpug_list);

        // 어뎁터 연결
        // ListView list = (ListView)findViewById(R.id.list);
        // list.setAdapter(kpugAdapter);

/*
        Toast.makeText(this, it.getStringExtra("Imgsrc") +it.getIntExtra("id", 0), Toast.LENGTH_SHORT).show();
*/

        //btnSecond.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        finish();
        //    }
        //});

    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc = Jsoup.connect(htmlPageUrl).get();


                //테스트1
                Elements titles= doc.select("div.read_body");

                System.out.println("-------------------------------------------------------------");
                for(Element e: titles){
                    System.out.println("title: " + e.text());
                    htmlContentInStringFormat += e.text().trim() + "\n";
                    kpug_list.add(e.text().trim());
                }

                //테스트2
//                titles= doc.select("div.news-con h2.tit-news");
//
//                System.out.println("-------------------------------------------------------------");
//                for(Element e: titles){
//                    System.out.println("title: " + e.text());
//                    htmlContentInStringFormat += e.text().trim() + "\n";
//                }

                //테스트3
//                titles= doc.select("li.section02 div.con h2.news-tl");
//
//                System.out.println("-------------------------------------------------------------");
//                for(Element e: titles){
//                    System.out.println("title: " + e.text());
//                    htmlContentInStringFormat += e.text().trim() + "\n";
//                }
                System.out.println("-------------------------------------------------------------");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textviewHtmlDocument.setText(htmlContentInStringFormat);
            // ListView list = (ListView)findViewById(R.id.list);
            // list.setAdapter(kpugAdapter);
        }
    }
}
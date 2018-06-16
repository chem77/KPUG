package com.example.ahnch.crawlingpractice01;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {

    Button btnSecond;
    TextView Name,Num;

    private String htmlPageUrl = "http://www.kpug.kr/kpugfreeboard"; //파싱할 홈페이지의 URL주소
    private TextView textviewHtmlDocument;
    private String htmlContentInStringFormat="";
   // private ArrayList<String> kpug_list;
   private ArrayList<ItemObject> kpug_list = new ArrayList();

    private ArrayAdapter<String> kpugAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // textviewHtmlDocument = (TextView)findViewById(R.id.textView);
        // textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod()); //스크롤 가능한 텍스트뷰로 만들기

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

        String sListNo = it.getStringExtra("ListNo");

        //Name.setText(it.getStringExtra("Name"));
        //Num.setText(it.getStringExtra("PhoneNo"));

        System.out.println( sListNo +"번째줄");

        switch (sListNo) {
            case "0": // 자유게시판
                htmlPageUrl = "http://www.kpug.kr/kpugfreeboard";
                break;
            case "1": // 한줄메모
                htmlPageUrl = "http://www.kpug.kr/line_2";
                break;
            case "2": // 만능문답
                htmlPageUrl = "http://www.kpug.kr/kpugknow";
                break;
            case "3": // 사용기및구매후기
                htmlPageUrl = "http://www.kpug.kr/reviews";
                break;
        }

        System.out.println( htmlPageUrl +"사이트");

        //kpug_list = new ArrayList<ItemObject>();
        kpugAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kpug_list);

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();

        //ArrayList<String> kpug_list = new ArrayList<String>();

        //kpug_list.add("자유게시판");
        //kpug_list.add("한줄메모");

        // 어뎁터 준비
        ArrayAdapter<CharSequence> kpugAdapter;
        kpugAdapter = ArrayAdapter.createFromResource(this, R.array.kpug_list, android.R.layout.simple_list_item_1);

        // 어뎁터 연결
        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(kpugAdapter);

        list.setOnItemClickListener(mItemClickListener);

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

    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        //@Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Resources res = getResources();
            String[] titles = res.getStringArray(R.array.kpug_list);

            String mes;
            mes = "Select Item = " + titles[position];

            //System.out.println(position);

            Context context = getApplicationContext();

            // Toast.makeText(context, mes, Toast.LENGTH_SHORT).show();

            //Intent it = new Intent(MainActivity.this, ListActivity.class);
            Intent it = new Intent(context, DetailActivity.class);
            it.putExtra("ListDetailNo", String.valueOf(position));
            //it.putExtra("PhoneNo", data.get(position).getPhoneNo());
            //it.putExtra("Imgsrc", data.get(position).getImgId());
            startActivity(it);



        }
    };

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
                Elements titles= doc.select("td.title");

                System.out.println("-------------------------------------------------------------");
                for(Element e: titles){

                    String listDetailNo = doc.select("td.title").attr("href");

                    System.out.println("title: " + e.text());
                    String title = e.text().trim();
                   // kpug_list.add(htmlContentInStringFormat);
                    kpug_list.add(new ItemObject(title, listDetailNo));
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
            //textviewHtmlDocument.setText(htmlContentInStringFormat);
             ListView list = (ListView)findViewById(R.id.list);
             list.setAdapter(kpugAdapter);
        }
    }

    public class ItemObject {
        private String title;
        private String listDetailNo;


        public ItemObject(String title, String listDetailNo){
            this.title = title;
            this.listDetailNo = listDetailNo;
        }


        public String getTitle() {
            return title;
        }

        public String getListDetailNo() {
            return listDetailNo;
        }

    }

}
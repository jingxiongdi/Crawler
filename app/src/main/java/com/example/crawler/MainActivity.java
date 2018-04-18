package com.example.crawler;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.crawler.adapter.ZhiLianAdapter;
import com.example.crawler.bean.ZhiLianBean;
import com.example.crawler.utils.ExcelUtils;
import com.example.crawler.utils.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends BaseActivity {
    private TextView textView = null;
    private ListView listView = null;
    private Button button = null;
    private Handler handler = new Handler();
    ExecutorService executorService;
    String url = "http://sou.zhaopin.com/jobs/searchresult.ashx?jl=长沙&kw=注册会计师&sm=0&sg=cd73d4b7281248829da3fa422ce9f43f&p=";
    private ArrayList<ZhiLianBean> zhiLianBeanArrayList = new ArrayList<>();
    private int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        executorService = Executors.newFixedThreadPool(1);

        executorService.execute(new Runnable() {
            public void run() {
                for(int i = 0;i<6;i++){
                    parseWEBHtml();
                    position++;
                }

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

    private void parseHTML() {
        /**
         * 解析HTML标签
         */
        String html = "<html><head><title>First parse</title></head>"
                + "<body><p id='aaa'>Parsed HTML into a doc.</p></body></html>";
        Document doc = Jsoup.parse(html);
       // textView.setText(doc.getElementById("aaa").text());
       // textView.setText(doc.getElementsByTag("p").text());
        textView.setText(doc.body().text());
    }

    private void setViews() {
        textView = (TextView) findViewById(R.id.text);
        textView.setText("正在加载");
        listView = (ListView) findViewById(R.id.listview);
        button=(Button) findViewById(R.id.create_excel);
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setEnabled(false);
                new AsyncTask<Void,Void,Void>(){

                    @Override
                    protected Void doInBackground(Void... voids) {
                        createExcel();
                        return null;
                    }
                }.execute();
            }
        });
        //parseHTML();

    }

    private void createExcel() {
        final String path = Environment.getExternalStorageDirectory().getPath()+"/注册会计师.xls";
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
        ExcelUtils.writeExcel(path,"智联招聘",zhiLianBeanArrayList);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.getInstance(getApplicationContext()).showLongToast("生成excel成功，存储路径 ："+path);
                button.setEnabled(true);
            }
        });
    }

    private void parseWEBHtml() {
        /**
         * 1.公司名称标签：gsmc
         * 2.薪水标签：  zwyx
         * 3.岗位职责标签：  newlist_deatil_last
         */
        try {
            final Document doc = Jsoup.connect(url+position).get();
            final Elements eJob = doc.getElementsByClass("zwmc");
            final Elements eCompany = doc.getElementsByClass("gsmc");
            final Elements eSalary = doc.getElementsByClass("zwyx");
           final Elements eRes = doc.getElementsByClass("newlist_deatil_last");
            Log.d("jxd","aaa : "+eCompany.text());
           for(int i = 0; i<eCompany.size()&&i<eSalary.size()&&i<eRes.size();i++){
               ZhiLianBean z = new ZhiLianBean();
               z.setJobs(eJob.get(i+1).text().trim());
               z.setCompanyName(eCompany.get(i+1).text().trim());
               z.setSalary(eSalary.get(i+1).text().trim());
               z.setResponsibilities(eRes.get(i).text().trim());
               zhiLianBeanArrayList.add(z);
           }
           if(position == 5){
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       /**
                        * 网页标题  doc.title()
                        * 网页内容  doc.body().text()
                        */
                       button.setEnabled(true);
                       textView.setVisibility(View.GONE);
                       ZhiLianAdapter zhiLianAdapter = new ZhiLianAdapter(MainActivity.this,zhiLianBeanArrayList);
                       listView.setAdapter(zhiLianAdapter);

//                    textView.setText(elements.text());
                   }
               });
           }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
//       new Thread(new Runnable() {
//           @Override
//           public void run() {
//               try {
//                   //还是一样先从一个URL加载一个Document对象。
//                   Document doc = Jsoup.connect("http://home.meishichina.com/recipe.html").get();
//
//                   Elements elements = doc.getElementsByTag("top-bar");
//                   for(Element e : elements) {
//                       System.out.println(e.text());
//                   }
//
//
//               }catch(Exception e) {
//                   Log.i("mytag", e.toString());
//               }
//           }
//       }).start();
    }

}

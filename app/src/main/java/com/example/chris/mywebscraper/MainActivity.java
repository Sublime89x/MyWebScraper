package com.example.chris.mywebscraper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    private Button getBtn;
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        getBtn = (Button) findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWebsite();
            }
        });
    }

    private void getWebsite() {

        Document doc = null;






        new Thread(new Runnable() {
            @Override
            public void run() {

                //TextView et = (TextView) findViewById(R.id.etSource);

                final StringBuilder builder = new StringBuilder();


                try {

                    //Document doc = Jsoup.connect("https://www.battlemetrics.com/servers/rust?q=Duo&sort=-details.rust_last_wipe").get();
                    Document doc = Jsoup.connect("https://www.battlemetrics.com/servers/rust?countries=US&minPlayers=35&q=Duo&sort=-details.rust_last_wipe").get();



                    //Document doc = Jsoup.connect("https://www.battlemetrics.com/servers/rust?q=" + et + "&sort=-details.rust_last_wipe").get();
                    //StringBuilder builder = new StringBuilder();
                    String title = doc.title();
                    //Elements links = doc.select("a[href]");

                    // FIRST METHOD
                    //Elements serverName = doc.select("a[title]");

                    // SECOND METHOD
                    Elements serverList = doc.select("tr.server");

                    //Elements lastWipe = doc.select("time");



                    builder.append(title).append("\n");

                    for (Element serverDetailsRow : serverList) {
                        serverDetailsRow.toString();


                        // Server Name
                        Element serverNameContainer = serverDetailsRow.selectFirst("td.server-name");
                        Element serverWipe = serverDetailsRow.selectFirst("td.server-wipe");

                        Element serverNameBag = serverNameContainer.selectFirst("a");
                        String serverName = serverNameBag.attr("title");
                        String testStr;
                        Element timeElement = serverWipe.selectFirst("time[title]");
                        testStr = timeElement.attr("title");
                        String serverNameStr = serverNameContainer.toString();
                        String serverWipeStr = serverWipe.toString();

                        builder.append("\n")
                                .append("Server Name : " + serverName)


                         // Server Wipe Time

                        .append("\n").append("Wipe Date/Time : " + testStr).append("\n");

                        // Server Player Count

                        Element serverPlayers = serverDetailsRow.selectFirst("td.server-players");
                        String data = serverPlayers.text();
                        builder.append("Players : " + data + "\n");








                                //  This line is for First Method
                         //       .append("Server Name : ").append(link.attr("title")).append("\n")/*.append("Last Wipe : ").append(lastWipe)*/.append("\n")/*.append("Label : ").append(link.attr("time"))/*.append(link.text())*/;
                      //  for (Element link1 : lastWipe) {
                        //    builder.append("\n")
                          //          .append("Last Wipe : ").append(link1.attr("datetime"));
                        //}


                                //  This line is for Second Method
                             //   .append("Server Name : ").append(link.attr("title")).append("\n").append("Last Wipe : ").append(link.attr("time"));
                        //builder.append("\n")
                          //      .append("Last Wipe : ").append(link.attr("datetime"));
                    }

                  //  for (Element link : lastWipe) {
                  //      builder.append("\n")
                  //              .append("Last Wipe : ").append(link.attr("time"))/*.append(link.text())*/;
                  //  }


                } catch (Exception e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                    }
                });

            }
        }).start();

        result.setMovementMethod(new ScrollingMovementMethod());



    }

}

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    List<Article> articles = new ArrayList<>();

    Main() {
        Document document = null;
        Document document1 = null;
        Document document2 = null;
        Document document3 = null;
        Document document4 = null;
        Document document5 = null;
        try {
            document = Jsoup.connect("http://trudbox.com.ua/lg/jobs-in-lisichansk").userAgent("Google").get();
            document1 = Jsoup.connect("http://trudbox.com.ua/lg/jobs-in-lisichansk/page/2").userAgent("Google").get();
            document2 = Jsoup.connect("http://trudbox.com.ua/lg/jobs-in-lisichansk/page/3").userAgent("Google").get();
            document3 = Jsoup.connect("http://trudbox.com.ua/lg/jobs-in-lisichansk/page/4").userAgent("Google").get();
            document4 = Jsoup.connect("http://trudbox.com.ua/lg/jobs-in-lisichansk/page/5").userAgent("Google").get();
            document5 = Jsoup.connect("http://board24.lg.ua/work/offer/").userAgent("Google").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Document> documentList = new ArrayList<>();
        documentList.add(document);
        documentList.add(document1);
        documentList.add(document2);
        documentList.add(document3);
        documentList.add(document4);

        for (int i = 0; i < documentList.size(); i++) {
            if (documentList.get(i) != null) {
                AtomicInteger count = new AtomicInteger(0);

                Elements elements = documentList.get(i).getElementsByClass("desc");
                elements.forEach((a)->{
                    Element element = a.child(0);

                    String url = element.attr("data-href");
                    if(url.length()<1)url = element.attr("href");

                    String text = a.ownText();
                    articles.add(new Article(url,text));
                    count.getAndIncrement();
                });

                count.set(articles.size() - count.get());

                Elements elements1 = documentList.get(i).getElementsByClass("hline_3 title job-title");
                elements1.forEach((a)->{
                    try{
                        articles.get(count.get()).setArticleName(a.text());
                        count.getAndIncrement();
                    }catch (NullPointerException e){}
                });

            }
        }

//        articles.forEach((a)->{
//            System.out.println(a);
//        });

        File file = new File("txttile.doc");
        try(PrintWriter printWriter = new PrintWriter(file);){
            AtomicInteger count1 = new AtomicInteger(0);
            articles.forEach((a)->{
                if(!a.getInformation().contains("раскрепоженная") &&
                        !a.getInformation().contains("18+") &&
                        !a.getInformation().contains("Одес") &&
                        !a.getInformation().contains("эскорт")) {
                    printWriter.print("----------------" + count1.get() + "----------------" + "\n");
                    printWriter.print(a.getArticleName() + "\n");
                    printWriter.print(a.getInformation() + "\n");
                    printWriter.print(a.getUrl() + "\n");
                    printWriter.print("\n");
                    count1.getAndIncrement();
                }
            });
            printWriter.close();
        }catch (IOException e){}
    }

    public static void main(String[] args) {
        new Main();
    }
}

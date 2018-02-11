public class Article {
    private String url;
    private String information;
    private String articleName;

    public Article(String url,String information){
        this.url = url;
        this.information = information;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getInformation() {
        return information;
    }

    public String getUrl() {
        return url;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return articleName+"\n"+information+"\n"+url;
    }
}

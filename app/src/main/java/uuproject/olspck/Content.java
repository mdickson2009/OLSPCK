package uuproject.olspck;

public class Content {

    private String Title;
    private String desc;
    private String image;


    public Content(){

    }



    public Content(String title, String desc, String image) {
        Title = title;
        this.desc = desc;
        this.image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

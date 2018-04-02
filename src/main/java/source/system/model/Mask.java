package source.system.model;

/**
 * Created by кирюха on 29.03.2018.
 */
public class Mask {
    String Title;
    String Mask;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMask() {

        return Mask;
    }

    public void setMask(String mask) {
        Mask = mask;
    }

    public String getTitle() {

        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}

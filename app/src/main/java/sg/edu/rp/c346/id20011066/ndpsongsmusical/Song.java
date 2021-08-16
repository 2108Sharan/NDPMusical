package sg.edu.rp.c346.id20011066.ndpsongsmusical;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Song implements Serializable {
    private int id;
    private String title;
    private String singers;
    private int years;
    private int stars;
    private String URL;

    public Song(int id, String title, String singers, int years, int stars, String URL) {
        this.id = id;
        this.title = title;
        this.singers = singers;
        this.years = years;
        this.stars = stars;
        this.URL = URL;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSingers(String singers) {
        this.singers = singers;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setURL(String URL) { this.URL = URL; }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getSingers() {
        return singers;
    }
    public int getYears() {
        return years;
    }
    public int getStars() {
        return stars;
    }
    public String getURL() { return URL; }

    @NonNull
    @Override
    public String toString() {

        return title + "\n" + singers + "-" + years + "\n" + toStars() + URL;
    }
    public String toStars() {
        String repeated = new String(new char[stars]).replace("\0", "* ");
        return repeated;
    }
}


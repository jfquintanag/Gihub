package materialtest.jufequinta.navigationdrawer.Message.Entities;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by acer on 24/02/2016.
 */
@SuppressWarnings("serial")
public class ItunesApp implements Serializable{

    public ItunesApp(){}

    private String nameApp;
    public void setNameApp(String nameApp){
        this.nameApp = nameApp;
    }
    public String getNameApp(){
        return this.nameApp;
    }

    private String imageLink;

    public void setImageLink(String imageLink){
        this.imageLink = imageLink;
    }
    public String getImageLink(){
        return this.imageLink;
    }
    private String labelCategory;

    public void setLabelCategory(String labelCategory){
        this.labelCategory = labelCategory;
    }
    public  String getLabelCategory(){
        return this.labelCategory;
    }

    private String description;
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }

    private String released;
    public void setReleased(String released){
        this.released = released;
    }
    public  String getReleased(){
        return this.released;
    }

    private String artist;
    public void setArtist(String artist){
        this.artist = artist;
    }
    public  String getArtist(){ return this.artist; }

    private String releaseDate;
    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }
    public  String getReleaseDate(){
        return this.releaseDate;
    }

    private String link;
    public void setLink(String link) {
        this.link=link;
    }
    public String getLink(){
        return this.link;
    }
}

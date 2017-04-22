package edu.utah.blulab.domainontology;

import java.util.ArrayList;

/**
 * Created by melissa on 4/22/17.
 */
public class Annotation {

    ArrayList<String> spans;
    String annotationCategory, annotationText;

    public Annotation(){
        ArrayList<String> spans = new ArrayList<String>();
        String annotationCategory = "";
        String annotationText = "";
    }

    public ArrayList<String> getSpans(){
        return spans;
    }

    public void setSpans(ArrayList<String> spans){
        this.spans = spans;
    }

    public String getAnnotationCategory(){
        return annotationCategory;
    }

    public void setAnnotationCategory(String category){
        annotationCategory = category;
    }

    public void setAnnotationText(String text){
        annotationText = text;
    }

    public String getAnnotationText(){
        return annotationText;
    }
}

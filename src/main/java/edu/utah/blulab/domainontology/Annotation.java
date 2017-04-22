package edu.utah.blulab.domainontology;

import java.util.ArrayList;

/**
 * Created by melissa on 4/22/17.
 */
public interface Annotation {

    ArrayList<String> spans = new ArrayList<String>();
    Object annotationCategory = new Object();
    String annotationText = "";

    public ArrayList<String> getSpans();

    public void setSpans(ArrayList<String> spans);

    public Object getAnnotationCategory();

    public void setAnnotationCategory(Object category);

    public void setAnnotationText(String text);

    public String getAnnotationText();
}

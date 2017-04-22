package edu.utah.blulab.domainontology;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by melissa on 4/22/17.
 */
public class AnnotationVariable extends Annotation{

    ArrayList<String> spans;
    String annotationCategory, annotationText;


    public AnnotationVariable(){
        spans = new ArrayList<String>();
        annotationCategory = "";
        annotationText = "";
    }

    public AnnotationVariable(Variable variable){
        annotationCategory = variable.getURI();
        annotationText = "";
        spans = new ArrayList<String>();
    }

    public AnnotationVariable(String variableName){
        annotationCategory = variableName;
        annotationText = "";
        spans = new ArrayList<String>();
    }

    public AnnotationVariable(Variable variable, String text, ArrayList<String> spans){
        this.spans = spans;
        annotationCategory = variable.getURI();
        annotationText = text;
    }




}

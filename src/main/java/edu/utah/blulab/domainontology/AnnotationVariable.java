package edu.utah.blulab.domainontology;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by melissa on 4/22/17.
 */
public class AnnotationVariable extends Annotation{

    ArrayList<AnnotationModifier> modifierList;
    String documentID, corpusID;

    public AnnotationVariable(String annotationID){
        spans = new ArrayList<String>();
        annotationCategory = "";
        annotationText = "";
        this.annotationID = annotationID;
        modifierList = new ArrayList<AnnotationModifier>();
    }

    public AnnotationVariable(Variable variable, String annotationID){
        annotationCategory = variable.getURI();
        annotationText = "";
        spans = new ArrayList<String>();
        this.annotationID = annotationID;
        modifierList = new ArrayList<AnnotationModifier>();
    }

    public AnnotationVariable(String variableName, String annotationID){
        annotationCategory = variableName;
        annotationText = "";
        spans = new ArrayList<String>();
        this.annotationID = annotationID;
        modifierList = new ArrayList<AnnotationModifier>();
    }

    public AnnotationVariable(Variable variable, String text, ArrayList<String> spans){
        this.spans = spans;
        annotationCategory = variable.getURI();
        annotationText = text;
        modifierList = new ArrayList<AnnotationModifier>();
    }

    public void setDocumentID(String documentID){
        this.documentID = documentID;
    }

    public String getDocumentID(){
        return documentID;
    }

    public void setCorpusID(String corpusID){
        this.corpusID = corpusID;
    }

    public String getCorpusID(){
        return corpusID;
    }

    public void setModifierList(ArrayList<AnnotationModifier> modifierList){
        this.modifierList = modifierList;
    }

    public ArrayList<AnnotationModifier> getModifierList(){
        return modifierList;
    }

    public void addModifierAnnotation(AnnotationModifier modifierAnnotation){
        modifierList.add(modifierAnnotation);
    }

    public void addModifierAnnotation(ArrayList<AnnotationModifier> modifiers){
        modifierList.addAll(modifiers);
    }

}

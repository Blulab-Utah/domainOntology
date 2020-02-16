/*
 * Copyright  2016  Department of Biomedical Informatics, University of Utah
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.utah.blulab.domainontology;

import java.util.ArrayList;

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

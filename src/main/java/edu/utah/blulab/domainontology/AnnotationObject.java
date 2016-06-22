package edu.utah.blulab.domainontology;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import java.util.ArrayList;
import java.util.HashMap;

public class AnnotationObject {
    private String uri;
    private DomainOntology domain;

    public AnnotationObject(Object annotationType, String annotationID, DomainOntology domain) throws Exception{
        this.domain = domain;
        uri = domain.getDomainURI() + "#" + annotationID;

        if(annotationType instanceof Variable){
            Variable variable = (Variable) annotationType;
            domain.setMemberOf(domain.getFactory().getOWLNamedIndividual(IRI.create(uri)),
                    domain.getFactory().getOWLClass(IRI.create(variable.getURI())));
        }else if (annotationType instanceof Modifier){
            Modifier modifier = (Modifier) annotationType;
            domain.setMemberOf(domain.getFactory().getOWLNamedIndividual(IRI.create(uri)),
                    domain.getFactory().getOWLClass(IRI.create(modifier.getUri())));
        }else if (annotationType instanceof Term){
            Term term = (Term) annotationType;
            domain.setMemberOf(domain.getFactory().getOWLNamedIndividual(IRI.create(uri)),
                    domain.getFactory().getOWLClass(IRI.create(term.getURI())));
        }
    }

    public String getUri() {
        return uri;
    }

    public void setAnnotationType(String type) throws OWLOntologyStorageException {
        domain.setDataProperty(domain.getIndividual(uri), OntologyConstants.HAS_ANNOTATION_TYPE, type);
    }

    public void setCorpus(String corpus) throws OWLOntologyStorageException {
        domain.setDataProperty(domain.getIndividual(uri), OntologyConstants.HAS_CORPUS, corpus);
    }

    public void setDocumentID(String documentID) throws OWLOntologyStorageException {
        domain.setDataProperty(domain.getIndividual(uri), OntologyConstants.HAS_DOCUMENT_ID, documentID);
    }

    public void setSpan(ArrayList<String> spans) throws OWLOntologyStorageException{
        for(String span : spans){
            domain.setDataProperty(domain.getIndividual(uri), OntologyConstants.HAS_SPAN, span);
        }
    }

    public void setModifierProperties(HashMap<String, ArrayList<String>> propertyValueMap){
        //TODO: add method to add properties to annotation instance
    }


    public void setText(String text){
        //TODO: finish method once data property is added to Schema Ontology
    }
}

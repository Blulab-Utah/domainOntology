package edu.utah.blulab.domainontology;

import java.util.*;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;

public class Anchor {
	private String uri;
	private DomainOntology domain;
	
	public Anchor(){
    }


    public Anchor(String clsURI, DomainOntology domain){
		this.uri = clsURI;
		this.domain = domain;
	}
	
	public String getURI(){
		return uri;
	}
	
	public String getPrefTerm() {
		return domain.getAnnotationString(domain.getClass(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.PREF_LABEL)));
	}

	public void setPrefTerm(String prefTerm) throws Exception{
		domain.setAnnotation(domain.getClass(uri),
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.PREF_LABEL)), prefTerm);
	}
	
	public String getPrefCode() {
		return domain.getAnnotationString(domain.getClass(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.PREF_CUI)));
	}
	
	
	public ArrayList<String> getSynonym() {
		return domain.getAnnotationList(domain.getClass(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.ALT_LABEL)));
	}
	
	public ArrayList<String> getMisspelling() {
		return domain.getAnnotationList(domain.getClass(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.HIDDEN_LABEL)));
	}
	
	public ArrayList<String> getAbbreviation() {
		return domain.getAnnotationList(domain.getClass(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.ABR_LABEL)));
	}
	
	public ArrayList<String> getSubjExp() {
		return domain.getAnnotationList(domain.getClass(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.SUBJ_EXP_LABEL)));
	}
	
	public ArrayList<String> getRegex() {
		return domain.getAnnotationList(domain.getClass(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.REGEX)));
	}
	
	public ArrayList<String> getAltCode() {
		return domain.getAnnotationList(domain.getClass(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.ALT_CUI)));
	}

	public ArrayList<String> getSemanticType() {
		return domain.getAnnotationList(domain.getClass(uri),
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.SEMANTIC_TYPE)));
	}
	
	public ArrayList<Anchor> getPseudos(){
		ArrayList<Anchor> pseudos = new ArrayList<Anchor>();
		ArrayList<OWLClass> pseudoList = domain.getObjectPropertyFillerList(domain.getClass(uri), 
				domain.getFactory().getOWLObjectProperty(IRI.create(OntologyConstants.HAS_PSEUDO)));
		for(OWLClass pseudo : pseudoList){
			pseudos.add(new Anchor(pseudo.getIRI().toString(), domain));
		}
		return pseudos;
	}

	public ArrayList<String> getDefinition() {
		return domain.getAnnotationList(domain.getClass(uri),
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.DEFINITION)));
	}
	
	public ArrayList<Anchor> getDirectParents(){
		ArrayList<Anchor> parents = new ArrayList<Anchor>();
		ArrayList<String> clsStrings = domain.getDirectSuperClasses(domain.getClass(uri));
		for(String str : clsStrings){
			if(!domain.getClass(str).asOWLClass().getIRI().getNamespace().equalsIgnoreCase(OntologyConstants.SO_PM+"#") &&
					!domain.getClass(str).asOWLClass().getIRI().getNamespace().equalsIgnoreCase(OntologyConstants.CT_PM+"#")){
				parents.add(new Anchor(str, domain));
			}
		}
		return parents;
	}
	
	public ArrayList<Anchor> getDirectChildren(){
		ArrayList<Anchor> children = new ArrayList<Anchor>();
		ArrayList<String> clsStrings = domain.getDirectSubClasses(domain.getClass(uri));
		for(String str : clsStrings){
			if(!domain.getClass(str).asOWLClass().getIRI().getNamespace().equalsIgnoreCase(OntologyConstants.SO_PM+"#") &&
					!domain.getClass(str).asOWLClass().getIRI().getNamespace().equalsIgnoreCase(OntologyConstants.CT_PM+"#")){
				children.add(new Anchor(str, domain));
			}
		}
		return children;
	}

	public ArrayList<String> getAllParents(){
		ArrayList<String> parentAncestry = new ArrayList<String>();
		parentAncestry = domain.getAllSuperClasses(domain.getClass(uri), false);
		return parentAncestry;
	}

	public List<ClassPath> getClassPaths(){
		return domain.getRootClassPaths(domain.getClass(uri));
	}

	
	@Override
	public String toString() {
		return "Anchor [prefTerm=" + this.getPrefTerm() + ", prefCode=" + this.getPrefCode()
				//+ ", ancestry= " + this.getClassPaths()
				//+  ", synonym=" + this.getSynonym()
				//+ ", misspelling=" + this.getMisspelling()//+ ", abbreviation="
				//+ ", semanticType=" + this.getSemanticType()
				//+ this.getAbbreviation() + ", subjExp=" + this.getSubjExp() + ", regex=" + this.getRegex()
				//+ ", altCode=" + this.getAltCode()
				+ ", pseudos=" + this.getPseudos()
				//+ "\n\tPARENTS:  " + this.getAllParents()
				+ "]";
	}
	
}

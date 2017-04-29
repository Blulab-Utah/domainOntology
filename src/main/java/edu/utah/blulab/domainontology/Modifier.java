package edu.utah.blulab.domainontology;

import java.util.*;

import org.semanticweb.owlapi.model.*;
import sun.rmi.runtime.Log;

public class Modifier {
	private String uri;
	private DomainOntology domain;
	public final static String LINGUISTIC = "Linguistic";
	public final static String SEMANTIC = "Semantic";
	public final static String NUMERIC = "Numeric";
	public final static String QUALIFIER = "Qualifier";
	
	
	public Modifier(String clsURI, DomainOntology domain){
		uri = clsURI;
		this.domain = domain;
	}
	
	public String getModifierType(){
		String type = "";

		for(ClassPath path : this.getClassPaths()){
			if(path.contains(domain.getClass(OntologyConstants.LINGUISTIC_MODIFIER))){
				return LINGUISTIC;
			}else if(path.contains(domain.getClass(OntologyConstants.NUMERIC_MODIFIER))){
				return NUMERIC;
			}else if(path.contains(domain.getClass(OntologyConstants.SEMANTIC_MODIFIER))){
				return SEMANTIC;
			}else if(path.contains(domain.getClass(OntologyConstants.QUALIFIER))){
				return QUALIFIER;
			}
		}

		return type;
	}


	public String getModName() {
		return domain.getClass(uri).getIRI().getShortForm();
	}
	
	public String getUri() {
		return uri;
	}
	
	public ArrayList<LexicalItem> getItems() {
		ArrayList<LexicalItem> items = new ArrayList<LexicalItem>();
		for(String item : domain.getAllIndividualURIs(domain.getClass(uri))){
			items.add(new LexicalItem(item, domain));
		}
		return items;
	}
	
	public ArrayList<Modifier> getClosures() {
		ArrayList<Modifier> list = new ArrayList<Modifier>();
		ArrayList<OWLClass> clsList = domain.getObjectPropertyFillerList(domain.getClass(uri), 
				domain.getFactory().getOWLObjectProperty(IRI.create(OntologyConstants.HAS_TERMINATION)));
		for(OWLClass cls : clsList){
			list.add(new Modifier(cls.getIRI().toString(), domain));
		}
		return list;
	}
	
	public ArrayList<Modifier> getPseudos(){
		ArrayList<Modifier> list = new ArrayList<Modifier>();
		ArrayList<OWLClass> clsList = domain.getObjectPropertyFillerList(domain.getClass(uri), 
				domain.getFactory().getOWLObjectProperty(IRI.create(OntologyConstants.HAS_PSEUDO)));
		for(OWLClass cls : clsList){
			list.add(new Modifier(cls.getIRI().toString(), domain));
		}
		return list;
	}
	
	public ArrayList<Modifier> getDirectParents(){
		ArrayList<Modifier> parents = new ArrayList<Modifier>();
		ArrayList<String> clsStrings = domain.getDirectSuperClasses(domain.getClass(uri));
		for(String str : clsStrings){
			if(!domain.getClass(str).asOWLClass().getIRI().getNamespace().equalsIgnoreCase(OntologyConstants.SO_PM+"#") &&
					!domain.getClass(str).asOWLClass().getIRI().getNamespace().equalsIgnoreCase(OntologyConstants.CT_PM+"#")){
				parents.add(new Modifier(str, domain));
			}
		}
		return parents;
	}
	
	public ArrayList<Modifier> getDirectChildren(){
		ArrayList<Modifier> children = new ArrayList<Modifier>();
		ArrayList<String> clsStrings = domain.getDirectSubClasses(domain.getClass(uri));
		for(String str : clsStrings){
			if(!domain.getClass(str).asOWLClass().getIRI().getNamespace().equalsIgnoreCase(OntologyConstants.SO_PM+"#") &&
					!domain.getClass(str).asOWLClass().getIRI().getNamespace().equalsIgnoreCase(OntologyConstants.CT_PM+"#")){
				children.add(new Modifier(str, domain));
			}
		}
		return children;
	}

	public boolean isDefault(){
        boolean bool = false;
	    Set<OWLClassExpression> clsExpressions = domain.getClass(uri).getSuperClasses(domain.getOntology());

        for(OWLClassExpression exp : clsExpressions){
            if(exp.getClassExpressionType().equals(ClassExpressionType.DATA_SOME_VALUES_FROM)){
                OWLDataSomeValuesFrom isDefaultExp = (OWLDataSomeValuesFrom) exp;
                if(isDefaultExp.getProperty().asOWLDataProperty().equals(
                        domain.getFactory().getOWLDataProperty(IRI.create(OntologyConstants.CT_PM+"#isDefaultValue")))){
                    OWLDataOneOf dataValues = (OWLDataOneOf) isDefaultExp.getFiller();
                    for(OWLLiteral lit : dataValues.getValues()){
                        if(lit.getLiteral().compareToIgnoreCase("true") == 0){
                            bool = true;
                        }
                    }

                }
            }
        }

        return bool;
	}

	public ArrayList<String> getAllChildren(){
		ArrayList<String> childDescendents = new ArrayList<String>();
		ArrayList<OWLClass> allChildren = domain.getAllSubClasses(domain.getClass(uri), false);
		for(OWLClass cls : allChildren){
			childDescendents.add(cls.getIRI().toString());
		}

		return childDescendents;
	}

    public ArrayList<String> getAllParents(){
        ArrayList<String> parentAncestry = new ArrayList<String>();
        ArrayList<OWLClass> parentClasses= domain.getAllSuperClasses(domain.getClass(uri), false);
        for(OWLClass cls: parentClasses){
            parentAncestry.add(cls.getIRI().toString());
        }
        return parentAncestry;
    }

	public List<ClassPath> getClassPaths(){
		return domain.getRootClassPaths(domain.getClass(uri));
	}
	
	@Override
	public String toString() {
		return "\n\tModifier: " + this.getModName() + ", uri=" + uri
				+ ", type= " + this.getModifierType()
				+ ", classPath= " + this.getClassPaths()
				//+ ", ancestry= " + this.getClassPaths()
				//+ ", items=" + this.getItems()
				//+ "\n\t\t Pseudos=" + this.getPseudos()
				//+ "\n\t\t Closures=" + this.getClosures()
				+ ", isDefault? " + this.isDefault()
				//+ "\n\tPARENTS: " + this.getAllParents()
				//+ "\n\tCHILDREN: " + this.getAllChildren()
				+ "]";
	}
	
	
	
}

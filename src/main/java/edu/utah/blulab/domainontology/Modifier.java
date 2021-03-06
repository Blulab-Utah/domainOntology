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

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;

import java.util.*;

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
		LogicExpression<LogicExpression<Modifier>> list = this.getDefaultDefintion();
		for(LogicExpression<Modifier> expression : list){
			if(!expression.isEmpty()){
				bool = true;
			}
		}
		return bool;

	}

	public LogicExpression<LogicExpression<Modifier>> getDefaultDefintion(){
		LogicExpression<LogicExpression<Modifier>> defaultDef = new LogicExpression<LogicExpression<Modifier>>();
		LogicExpression<Modifier> defValues = new LogicExpression<Modifier>();

		HashMap<String, ArrayList<String>> defs = domain.getClassDefinition(domain.getClass(uri));
		Iterator iter = defs.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String, ArrayList<String>> entry = (Map.Entry<String, ArrayList<String>>) iter.next();
			defaultDef.setType(entry.getKey());
			ArrayList<String> list = entry.getValue();
			if(list.size() > 1){
				defValues.setType("OR");
			}else{
				defValues.setType("SINGLE");
			}
			for(String str : list){
				defValues.add(new Modifier(str, domain));
			}

		}

		defaultDef.add(defValues);
		return defaultDef;
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
		parentAncestry = domain.getAllSuperClasses(domain.getClass(uri), false);
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
				//+ ", isDefault? " + this.isDefault()
				//+ ", Default Definiton = " + this.getDefaultDefintion()
				//+ "\n\tPARENTS: " + this.getAllParents()
				//+ "\n\tCHILDREN: " + this.getAllChildren()
				+ "]";
	}
	
	
	
}

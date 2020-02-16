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

import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Melissa Castine
 *
 */
public class Variable {
	private String uri;
	private DomainOntology domain;
	private ArrayList<String> relationships; //May need feeback on the best representation here.
	private ArrayList<String> rules; //This may change once SWRL rules are implemented in ontology.
	
	/**
	 * @param clsURI A string representing the variable class URI
	 * @param domain The DomainOntology object containing variable
	 */
	public Variable(String clsURI, DomainOntology domain){
		this.domain = domain;
		uri = clsURI;
		relationships = new ArrayList<String>();
		rules = new ArrayList<String>();
	}

	/**
	 * Returns the ID associated with a variable
	 * @return A string of the variable ID
	 */
	public String getVarID() {
		return domain.getClassURIString(domain.getClass(uri));
	}

	public String getVarName() {
		return domain.getAnnotationString(domain.getClass(uri), domain.getFactory().getRDFSLabel());
		
	}

	public String getURI(){
		return uri;
	}

	public String getVariableType(){
        String type = "";
		ArrayList<OWLClassExpression> types = domain.getEquivalentObjectPropertyFillerList(domain.getClass(uri),
                domain.getFactory().getOWLObjectProperty(IRI.create(OntologyConstants.HAS_ANNOTATION_TYPE)));

        if(!types.isEmpty()){
            type = types.get(0).asOWLClass().getIRI().getShortForm();
        }


        return type;

	}

	public ArrayList<String> getSemanticCategory(){
		
		return domain.getDirectSuperClasses(domain.getFactory().getOWLClass(IRI.create(uri)));
	}
	
	public ArrayList<String> getSectionHeadings(){
		ArrayList<String> headings = domain.getAnnotationList(domain.getClass(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.SEC_HEADING)));
		return headings;
	}
	
	public ArrayList<String> getReportTypes(){
		ArrayList<String> types = domain.getAnnotationList(domain.getClass(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.DOC_TYPE)));
		return types;
	}
	
	public ArrayList<LogicExpression<Anchor>> getAnchor(){
		ArrayList<LogicExpression<Anchor>> anchorList = new ArrayList<LogicExpression<Anchor>>();
		ArrayList<OWLClassExpression>	 list = domain.getEquivalentObjectPropertyFillerList(domain.getClass(uri), 
				domain.getFactory().getOWLObjectProperty(IRI.create(OntologyConstants.HAS_ANCHOR)));
		for(OWLClassExpression cls : list){
			if(!cls.isAnonymous()){
				LogicExpression<Anchor> anchorExp = new LogicExpression<Anchor>("SINGLE");
				anchorExp.add(new Anchor(cls.asOWLClass().getIRI().toString(), domain));
				anchorList.add(anchorExp);
			}else{
				if(cls.getClassExpressionType().equals(ClassExpressionType.OBJECT_UNION_OF)){
					LogicExpression<Anchor> anchorExp = new LogicExpression<Anchor>("OR");
					OWLObjectUnionOf union = (OWLObjectUnionOf) cls;
					List<OWLClassExpression> filler = union.getOperandsAsList();
					for(OWLClassExpression c : filler){
						if(!c.isAnonymous()){
							anchorExp.add(new Anchor(c.asOWLClass().getIRI().toString(), domain));
						}
					}
					anchorList.add(anchorExp);
				}
			}
		}
		
		return anchorList;
	}
	
	public HashMap<String, LogicExpression<Modifier>> getModifiers(){
		HashMap<String, LogicExpression<Modifier>> mods = new HashMap<String, LogicExpression<Modifier>>();
		HashMap<String, ArrayList<OWLClassExpression>>	 list = domain.getEquivalentObjectPropertyFillerMap(domain.getClass(uri), domain.getNonNumericPropertyList());

		for(Map.Entry<String, ArrayList<OWLClassExpression>> entry : list.entrySet()){
			String property = entry.getKey();
			ArrayList<OWLClassExpression> expList = entry.getValue();

			for(OWLClassExpression cls : expList){
				if(!cls.isAnonymous()){
					LogicExpression<Modifier> modifierList =  new LogicExpression<Modifier>("SINGLE");
					modifierList.add(new Modifier(cls.asOWLClass().getIRI().toString(), domain));
					mods.put(property, modifierList);
				}else{
					if(cls.getClassExpressionType().equals(ClassExpressionType.OBJECT_UNION_OF)){
						LogicExpression<Modifier> modifierList = new LogicExpression<Modifier>("OR");
						OWLObjectUnionOf union = (OWLObjectUnionOf) cls;
						List<OWLClassExpression> filler = union.getOperandsAsList();
						for(OWLClassExpression c : filler){
							if(!c.isAnonymous()){
								modifierList.add(new Modifier(c.asOWLClass().getIRI().toString(), domain));
							}
						}
						mods.put(property, modifierList);
					}
				}
			}
		}

		return mods;
	}
	
	public HashMap<String, Variable> getRelationships(){
		HashMap<String, Variable> relations = new HashMap<String, Variable>();
		for(OWLObjectProperty prop : domain.getRelationsList()){
			ArrayList<OWLClass> list = domain.getObjectPropertyFillerList(domain.getClass(uri), prop);
			for(OWLClass cls: list){
				relations.put(prop.asOWLObjectProperty().getIRI().getShortForm(), new Variable(cls.getIRI().toString(), this.domain));
			}
			
		}
		return relations;
	}
	
	public ArrayList<NumericModifier> getNumericModifiers(){
		ArrayList<NumericModifier> mods = new ArrayList<NumericModifier>();
		ArrayList<OWLClassExpression>	 list = domain.getEquivalentObjectPropertyFillerList(domain.getClass(uri), domain.getNumericPropertyList());
		for(OWLClassExpression cls : list){
			if(!cls.isAnonymous()){
				mods.add(new NumericModifier(cls.asOWLClass().getIRI().toString(), domain));
			}
		}
		
		return mods;
	}
	
	public boolean hasNumericModifiers(){
		return !this.getNumericModifiers().isEmpty();
	}
	
	public ArrayList<Variable> getDirectParents(){
		ArrayList<Variable> parents = new ArrayList<Variable>();
		ArrayList<String> clsStrings = domain.getDirectSuperClasses(domain.getClass(uri));
		for(String str : clsStrings){
			if(!domain.getClass(str).asOWLClass().getIRI().getNamespace().equalsIgnoreCase(OntologyConstants.SO_PM+"#") &&
					!domain.getClass(str).asOWLClass().getIRI().getNamespace().equalsIgnoreCase(OntologyConstants.CT_PM+"#")){
				parents.add(new Variable(str, domain));
			}
		}
		return parents;
	}
	
	
	/**
	 * @return a list of variables
	 */
	public ArrayList<Variable> getDirectChildren(){
		ArrayList<Variable> children = new ArrayList<Variable>();
		ArrayList<String> clsStrings = domain.getDirectSubClasses(domain.getClass(uri));
		for(String str : clsStrings){
			if(!domain.getClass(str).asOWLClass().getIRI().getNamespace().equalsIgnoreCase(OntologyConstants.SO_PM+"#") &&
					!domain.getClass(str).asOWLClass().getIRI().getNamespace().equalsIgnoreCase(OntologyConstants.CT_PM+"#")){
				children.add(new Variable(str, domain));
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
		return "Variable [varID=" + this.getVarID() + ", varName=" + this.getVarName()
				//+ ", category=" + this.getSemanticCategory()
				//+ ", parentAncestry=" + this.getAllParents()
				//+ ", ancestry= " + this.getClassPaths()
				+ ", type= " + this.getVariableType()
				+ ", anchor=" + this.getAnchor().toString()
				+ "\n\t, modifiers=" + this.getModifiers()
				+ "\n\t, numerics=" + this.getNumericModifiers()
				//+ "\n\t, relations=" + this.getRelationships()
				+"]";
	}
	
	
	
	
}

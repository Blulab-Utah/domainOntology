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

import java.util.ArrayList;

public class LexicalItem {
	private DomainOntology domain;
	private String uri,  actionEn, actionSv, actionDe, prefTermEn; //, prefTermSv, prefTermDe
	private int windowSize;
	
	public LexicalItem(String itemURI, DomainOntology domain){
		uri = itemURI;
		this.domain = domain;
		

		
		
	}
	
	

	
	public String getUri() {
		return uri;
	}

	public String getPrefTerm(){
		return domain.getAnnotationString(domain.getIndividual(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.PREF_LABEL)), "en");
	}
	
	public ArrayList<String> getPrefCode(){
		return domain.getAnnotationStringList(domain.getIndividual(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.PREF_CUI)), "en");
	}
	
	public ArrayList<String> getSynonym(){
		return domain.getAnnotationStringList(domain.getIndividual(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.ALT_LABEL)), "en");
	}
	
	public ArrayList<String>getMisspelling(){
		return domain.getAnnotationStringList(domain.getIndividual(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.HIDDEN_LABEL)), "en");
	}
	
	public ArrayList<String>getAbbreviation(){
		return domain.getAnnotationStringList(domain.getIndividual(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.ABR_LABEL)), "en");
	}
	
	public ArrayList<String>getSubjExp(){
		return domain.getAnnotationStringList(domain.getIndividual(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.SUBJ_EXP_LABEL)), "en");
	}
	
	public ArrayList<String>getRegex(){
		return domain.getAnnotationStringList(domain.getIndividual(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.REGEX)), "en");
	}
	
	public ArrayList<String>getAltCode(){
		return domain.getAnnotationStringList(domain.getIndividual(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.ALT_CUI)), "en");
	}
	
	public String getActionEn(boolean returnDisplayName) {
		String str = domain.getObjectPropertyFiller(domain.getIndividual(uri),
				domain.getFactory().getOWLObjectProperty(IRI.create(OntologyConstants.ACTION_EN)));
		if(returnDisplayName){
			str = domain.getDisplayName(str);
		}
		return str;
	}

	public String getActionSv() {
		return actionSv;
	}

	public String getActionDe() {
		return actionDe;
	}
	
	public int getWindowSize(){
		String str = domain.getAnnotationString(domain.getIndividual(uri), 
				domain.getFactory().getOWLAnnotationProperty(IRI.create(OntologyConstants.WINDOW)));
		if(str.isEmpty() || str.equals(null)){
			return 8;
		}else{
			return Integer.parseInt(str);
		}
		
	}
	

	@Override
	public String toString() {
		return "LexicalItem [uri=" + uri + ", prefLabel=" + this.getPrefTerm() + 
				 ", regex=" + this.getRegex() +
				 ", altLabel=" + this.getSynonym() +
				", windowSize=" + this.getWindowSize() + ", actionEn=" + this.getActionEn(true) + "]";
	}
	
	
}

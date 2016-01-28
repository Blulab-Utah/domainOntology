package edu.utah.blulab.apitest;

import java.util.ArrayList;
import java.util.Collection;

import edu.utah.blulab.domainontology.DomainOntology;
import edu.utah.blulab.domainontology.Modifier;
import edu.utah.blulab.domainontology.Term;
import edu.utah.blulab.domainontology.Variable;

public class testAPI {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DomainOntology domain = new DomainOntology("/Users/melissa/git/useCases/98_heartFailure.owl");
		//DomainOntology domain = new DomainOntology("/Users/melissa/git/useCases/colonoscopyQuality.owl");
		//DomainOntology domain = new DomainOntology("/Users/melissa/Desktop/vincipneu.owl.xml");
		//DomainOntology domain = new DomainOntology("C:\\Users\\Bill\\Desktop\\carotid stenosis.owl"); 
		//DomainOntology domain = new DomainOntology("DomainOntologyAPI/src/main/resources/colonoscopy_20141001.owl");
		//DomainOntology domain = new DomainOntology("src/main/resources/colonoscopy_20141001.owl");
		//domain.getVariable("BowelPreparation");
		//domain.getVariable("KA247");
		
		ArrayList<Variable> domainVariables = domain.getAllVariables();
		System.out.println("********** Domain Variables: **********");
		for(Variable var : domainVariables){
			System.out.println(var.toString());
		}
		
		System.out.println("********** Modifier Dictionary: **********");
		Collection<Modifier> modifierDictionary = domain.getModifierDictionary();
		for(Modifier modifier : modifierDictionary){
			System.out.println(modifier.toString());
		}
		
		System.out.println("********** Anchor Dictionary: **********");
		Collection<Term> anchorDictionary = domain.getAnchorDictionary();
		for(Term term : anchorDictionary){
			System.out.println(term.toString());
		}
	}

}

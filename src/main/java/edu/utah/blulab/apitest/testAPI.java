package edu.utah.blulab.apitest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.utah.blulab.domainontology.*;

public class testAPI {

	public static void main(String[] args) throws Exception {
		//DomainOntology domain = new DomainOntology("/Users/melissa/git/useCases/98_heartFailure.owl", false);
		boolean bool = false;
		if(args[1].equalsIgnoreCase("true")){
			bool = true;
		}
		DomainOntology domain = new DomainOntology(args[0], bool);

        /*Anchor test1 = domain.createAnchor(domain.getDomainURI()+"#Test2");
        test1.setPrefTerm("prefTerm2");
        test1.setPrefCode("C999998");
        test1.setAltCode(new ArrayList<String>(Arrays.asList("C000001")));
        test1.setSynonym(new ArrayList<String>(Arrays.asList("syn1B", "syn2B", "syn3B")));*/


		/*ArrayList<Variable> domainVariables = domain.getAllVariables();
		System.out.println("********** Domain Variables: **********");
		for(Variable var : domainVariables){
			System.out.println(var.toString());
		}

        //AnnotationOntology annotationOntology = new AnnotationOntology(domain);

		/*System.out.println("********** Compound Anchor Dictionary: **********");
		ArrayList<CompoundAnchor> compoundAnchorDictionary = domain.createCompoundAnchorDictionary();
		for(CompoundAnchor anchor : compoundAnchorDictionary){
			System.out.println(anchor.toString());
		}*/


		/*System.out.println("********** Modifier Dictionary: **********");
		ArrayList<Modifier> modifierDictionary = domain.createModifierDictionary();
		for(Modifier modifier : modifierDictionary){
			System.out.println(modifier.toString());
		}
		
		/*System.out.println("********** Anchor Dictionary: **********");
		ArrayList<Anchor> anchorDictionary = domain.createAnchorDictionary();
		for(Anchor anchor : anchorDictionary){
			System.out.println(anchor.toString());
		}

		/*System.out.println("********** PseudoAnchor Dictionary: **********");
		ArrayList<Anchor> pseudoAnchorDictionary = domain.createPseudoAnchorDictionary();
		for(Anchor term : pseudoAnchorDictionary){
			System.out.println(term.toString());
		}

		System.out.println("********** PseudoModifier Dictionary: **********");
		ArrayList<Modifier> pseudoDictionary = domain.createPseudoModifierDictionary();
		for(Modifier term : pseudoDictionary){
			System.out.println(term.toString());
		}

		System.out.println("********** Closure Dictionary: **********");
		ArrayList<Modifier> closureDictionary = domain.createClosureDictionary();
		for(Modifier term : closureDictionary){
			System.out.println(term.toString());
		}*/



		System.out.println("********** Modifier Type Map: **********");
		HashMap<String, ArrayList<Modifier>> modifierMap = domain.createModifierTypeMap();
		Iterator iterator = modifierMap.entrySet().iterator();
		while (iterator.hasNext()){
			Map.Entry<String, ArrayList<Modifier>> modifierEntry =
					(Map.Entry<String, ArrayList<Modifier>>)iterator.next();
			System.out.print(modifierEntry.getKey() + ":\t");
			for(Modifier modifier : modifierEntry.getValue()){
				System.out.print(modifier.getModName() + "  ");
			}
			System.out.println("");
		}
	}



}

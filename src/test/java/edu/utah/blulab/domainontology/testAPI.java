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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class testAPI {

	public static void main(String[] args) throws Exception {
		DomainOntology domain = new DomainOntology("src/main/resources/colonoscopyQuality.owl", false);
		//DomainOntology domain = new DomainOntology("/Users/mtharp/use_cases/DomainOntologies/pneumonia.owl");
		//DomainOntology domain = new DomainOntology("/Users/mtharp/Desktop/vincipneu.owl.xml");
		//DomainOntology domain = new DomainOntology("C:\\Users\\Bill\\Desktop\\carotid stenosis.owl"); 
		//DomainOntology domain = new DomainOntology("DomainOntologyAPI/src/main/resources/colonoscopy_20141001.owl");
		//DomainOntology domain = new DomainOntology("src/main/resources/colonoscopy_20141001.owl");
		//domain.getVariable("leukocytosis");
		//domain.getVariable("KA247");
		
		ArrayList<Variable> domainVariables = domain.getAllVariables();
		System.out.println("********** Domain Variables: **********");
		for(Variable var : domainVariables){
			System.out.println(var.toString());
		}
		
		/**System.out.println("********** Modifier Dictionary: **********");
		ArrayList<Modifier> modifierDictionary = domain.createModifierDictionary();
		for(Modifier modifier : modifierDictionary){
			System.out.println(modifier.toString());
		}**/

		System.out.println("********** Modifier Map: **********");
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
		
		/**System.out.println("********** Target Dictionary: **********");
		ArrayList<Anchor> targetDictionary = domain.createAnchorDictionary();
		for(Anchor target : targetDictionary){
			System.out.println(target.toString());
		}**/
	}

}

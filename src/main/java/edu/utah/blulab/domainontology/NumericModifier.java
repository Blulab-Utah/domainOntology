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

import org.semanticweb.owlapi.model.IRI;

public class NumericModifier extends Modifier {
	private String type;
	private DomainOntology domain;
	private String uri;
	public final String QUANTITY = "Quantity";
	public final String RANGE = "RangeModifier";
	public final String RATIO = "Ratio";
	public final String DIMENSIONAL = "DimensionalMeasurement";
	public final String UNIT = "Unit";
	
	public NumericModifier(String clsURI, DomainOntology domain) {
		super(clsURI, domain);
		this.domain = domain;
		this.uri = clsURI;
		
		//set type
		for(ClassPath path : this.getClassPaths()){
			//System.out.println(domain.getFactory().getOWLClass(IRI.create(str)).getIRI().getShortForm());
			if(path.contains(domain.getClass(OntologyConstants.QUANTITY))){
				type= QUANTITY;
			}
			if(path.contains(domain.getClass(OntologyConstants.RANGE))){
				type= RANGE;
			}
			if(path.contains(domain.getClass(OntologyConstants.RATIO))){
				type= RATIO;
			}
			if(path.contains(domain.getClass(OntologyConstants.DIMENSION))){
				type= DIMENSIONAL;
			}
			if(path.contains(domain.getClass(OntologyConstants.UNIT))){
				type = UNIT;
			}
		}
	}

	
	public String getType(){
		return type;
	}
	
	public ArrayList<String> getQuantityValueRange(){
		ArrayList<String> values = new ArrayList<String>();
		if(this.equals(QUANTITY)){
			values = domain.getDataPropertyFiller(domain.getClass(uri),
					domain.getFactory().getOWLDataProperty(IRI.create(OntologyConstants.HAS_QUANTITY_VALUE)));

		}else{
			System.err.println("Numeric modifier is not type " + this.getType());
		}

		return values;

	}

	public ArrayList<String> getHighRangeValues(){
		ArrayList<String> values = new ArrayList<String>();
		if(this.equals(RANGE)){
			values = domain.getDataPropertyFiller(domain.getClass(uri),
					domain.getFactory().getOWLDataProperty(IRI.create(OntologyConstants.HAS_HIGH_VALUE)));

		}else{
			System.err.println("Numeric modifier is not type " + this.getType());
		}

		return values;
	}

	public ArrayList<String> getLowRangeValues(){
		ArrayList<String> values = new ArrayList<String>();
		if(this.equals(RANGE)){
			values = domain.getDataPropertyFiller(domain.getClass(uri),
					domain.getFactory().getOWLDataProperty(IRI.create(OntologyConstants.HAS_LOW_VALUE)));

		}else{
			System.err.println("Numeric modifier is not type " + this.getType());
		}

		return values;
	}

	public ArrayList<String> getNumeratorValues(){
		ArrayList<String> values = new ArrayList<String>();
		if(this.equals(RATIO)){
			values = domain.getDataPropertyFiller(domain.getClass(uri),
					domain.getFactory().getOWLDataProperty(IRI.create(OntologyConstants.HAS_NUMERATOR)));

		}else{
			System.err.println("Numeric modifier is not type " + this.getType());
		}

		return values;
	}

	public ArrayList<String> getDenominatorValues(){
		ArrayList<String> values = new ArrayList<String>();
		if(this.equals(RATIO)){
			values = domain.getDataPropertyFiller(domain.getClass(uri),
					domain.getFactory().getOWLDataProperty(IRI.create(OntologyConstants.HAS_DENOMINATOR)));

		}else{
			System.err.println("Numeric modifier is not type " + this.getType());
		}

		return values;
	}

	public ArrayList<String> get1DimensionValues(){
		ArrayList<String> values = new ArrayList<String>();
		if(this.equals(DIMENSIONAL)){
			values = domain.getDataPropertyFiller(domain.getClass(uri),
					domain.getFactory().getOWLDataProperty(IRI.create(OntologyConstants.HAS_1DIMENSION)));

		}else{
			System.err.println("Numeric modifier is not type " + this.getType());
		}

		return values;
	}

	public ArrayList<String> get2DimensionValues(){
		ArrayList<String> values = new ArrayList<String>();
		if(this.equals(DIMENSIONAL)){
			values = domain.getDataPropertyFiller(domain.getClass(uri),
					domain.getFactory().getOWLDataProperty(IRI.create(OntologyConstants.HAS_2DIMENSION)));

		}else{
			System.err.println("Numeric modifier is not type " + this.getType());
		}

		return values;
	}

	public ArrayList<String> get3DimensionValues(){
		ArrayList<String> values = new ArrayList<String>();
		if(this.equals(DIMENSIONAL)){
			values = domain.getDataPropertyFiller(domain.getClass(uri),
					domain.getFactory().getOWLDataProperty(IRI.create(OntologyConstants.HAS_3DIMENSION)));

		}else{
			System.err.println("Numeric modifier is not type " + this.getType());
		}

		return values;
	}



	
	public String toString(){
		return "Numeric Value: " + this.getModName() + ", uri= " + this.getUri() + ", type= " + this.getType()
		+ ", values= " + this.getQuantityValueRange();
	}

}

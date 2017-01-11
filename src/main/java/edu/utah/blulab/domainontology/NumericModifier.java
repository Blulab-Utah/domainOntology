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

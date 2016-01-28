package edu.utah.blulab.domainontology;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

public class DomainOntology {
	
	private OWLOntologyManager manager;
	private static OWLOntology ontology;
	private static OWLDataFactory factory;
	private PrefixManager pm;
	private File ontFile;
	private String ontURI;
	private HashMap<String, Term> anchorDictionary;
	private static HashMap<String, Modifier> modifierDictionary;
	private ArrayList<Modifier> closureDictionary;
	private ArrayList<Relation> relationshipDictionary;
	private Set<OWLOntology> imports;
	private final static String MODIFIERS = "Modifiers";
	private final String RULES = "Rules";
	private final static String RELATIONS = "Relationships";
	private ArrayList<OWLObjectProperty> modifierProps;
	
	public DomainOntology(String fileLocation) throws Exception{
		manager = OWLManager.createOWLOntologyManager();
		factory = manager.getOWLDataFactory();
		ontFile = new File(fileLocation);
		ontology = manager.loadOntologyFromOntologyDocument(ontFile);
		ontURI = ontology.getOntologyID().getOntologyIRI().toString();
		pm = new DefaultPrefixManager(ontURI + "#");
		anchorDictionary = new HashMap<String, Term>();
		modifierDictionary = new HashMap<String, Modifier>();
		closureDictionary = new ArrayList<Modifier>();
		relationshipDictionary = new ArrayList<Relation>();
		imports = manager.getImports(ontology);
		
		System.out.println("Loaded " + ontURI);
		for(OWLOntology ont : imports){
			System.out.println("Imported: " + ont.getOntologyID().getOntologyIRI().toString());
			
		}
		
		modifierProps = getAllProperties(factory.getOWLObjectProperty(IRI.create(OntologyConstants.SO_PM + "#hasModifier")));
		/**for(OWLObjectProperty p : modifierProps){
			System.out.println (p.toString());
		}**/
		
	}
	
	
	public Variable getVariable(String clsName){
		//Get OWL class
		OWLClass cls = factory.getOWLClass(clsName, pm);
		
		return getVariable(cls);
	}
	
	public Variable getVariable(OWLClass cls){
		Variable var = new Variable();
		Term term = new Term();

		//Set variable ID (aka URI)
		var.setVarID(cls.getIRI().toString());
		
		//Set variable name using RDF:label
		var.setVarName(getAnnotationString(cls, factory.getRDFSLabel()));
		
		//Extract defining classes/relationships (i.e. get anchor and defining modifiers)
		OWLClass anchor = null;
		Set<OWLClassExpression> exps = cls.getEquivalentClasses(ontology);
		for(OWLClassExpression e : exps){
			
			if(e.getClassExpressionType().equals(ClassExpressionType.OBJECT_SOME_VALUES_FROM)){
				//System.out.println(e);
				OWLObjectSomeValuesFrom objprop = (OWLObjectSomeValuesFrom) e;
				if(objprop.getProperty().equals(factory.getOWLObjectProperty(IRI.create(OntologyConstants.HAS_ANCHOR)))){
					//System.out.println("Anchor = " + objprop.getFiller());
					anchor = objprop.getFiller().asOWLClass();
				}else{
					ArrayList<Modifier> modifiers = var.getModifiers();
					Modifier mod = new Modifier(objprop.getFiller().asOWLClass().getIRI().toString(), manager, ontology);
					modifiers.add(mod);
					var.setModifiers(modifiers);
					if(!modifierDictionary.containsKey(mod.getUri())){
						modifierDictionary.put(mod.getUri(), mod);
					}
				}
			}
		}
		
		if(!anchor.equals(null)){
			//Set URI for anchor
			term.setURI(anchor.asOWLClass().getIRI().toQuotedString());
			
			//Set preferred label for anchor
			term.setPrefTerm(getAnnotationString(anchor, 
					factory.getOWLAnnotationProperty(IRI.create(OntologyConstants.PREF_TERM))));
			
			//Set preferred CUIs for variable concept
			term.setPrefCode(getAnnotationString(anchor,
					factory.getOWLAnnotationProperty(IRI.create(OntologyConstants.PREF_CODE))));
			
			//Set alternate CUIs for variable concept
			term.setAltCode(getAnnotationList(anchor,
					factory.getOWLAnnotationProperty(IRI.create(OntologyConstants.ALT_CODE))));
			
			//Set alternate labels
			term.setSynonym(getAnnotationList(anchor, 
					factory.getOWLAnnotationProperty(IRI.create(OntologyConstants.SYNONYM))));
			
			//Set hidden labels
			term.setMisspelling(getAnnotationList(anchor, 
					factory.getOWLAnnotationProperty(IRI.create(OntologyConstants.MISSPELLING))));
			
			//Set abbreviation labels
			term.setAbbreviation(getAnnotationList(anchor,
					factory.getOWLAnnotationProperty(IRI.create(OntologyConstants.ABBREVIATION))));
			
			//Set subjective expression labels
			term.setSubjExp(getAnnotationList(anchor,
					factory.getOWLAnnotationProperty(IRI.create(OntologyConstants.SUBJ_EXP))));
			
			//Set regex
			term.setRegex(getAnnotationList(anchor, 
					factory.getOWLAnnotationProperty(IRI.create(OntologyConstants.REGEX))));
			
			//Add concept to variable and concept dictionary
			var.setAnchor(term);
			if(!anchorDictionary.containsKey(term.getURI())){
				anchorDictionary.put(term.getURI(), term);
			}
		}
		
		
		//Set section headings
		/**var.setSectionHeadings(getAnnotationList(cls, 
				factory.getOWLAnnotationProperty(IRI.create(OntologyConstants.SEC_HEADING))));
		
		//Set document types
		var.setReportTypes(getAnnotationList(cls, 
				factory.getOWLAnnotationProperty(IRI.create(OntologyConstants.DOC_TYPE))));**/
		
		//Get semantic categories of class
		ArrayList<String> cats = new ArrayList<String>();
		Set<OWLClassExpression> parents = cls.getSuperClasses(ontology);
		for(OWLClassExpression parentCls : parents){
			//System.out.println("TYPE: " + parentCls.getClassExpressionType().getName() + "   "  + parentCls.getClassExpressionType().compareTo(ClassExpressionType.OWL_CLASS));
			if(parentCls.getClassExpressionType().compareTo(ClassExpressionType.OWL_CLASS) == 0){
				cats.add(parentCls.toString());
			}
		}
		var.setSemanticCategory(cats);
		
		HashMap<String, ArrayList<String>> details = getClassDetails(cls);
		
		return var;
	}
	
	public ArrayList<Variable> getAllVariables() {
		ArrayList<Variable> variables = new ArrayList<Variable>();
		ArrayList<OWLClass> elements = new ArrayList<OWLClass>();
		getVariableList(factory.getOWLClass(IRI.create(OntologyConstants.SO_PM + "#Event")), new ArrayList<OWLClass>(), elements);
		//System.out.println("THESE ARE THE ELEMENTS IN THE DOMAIN ONTOLOGY...");
		for(OWLClass cls : elements){
			//System.out.println(cls.toString());
			variables.add(getVariable(cls));
		}
		return variables;
	}
	
	private void getVariableList(OWLClass cls, ArrayList<OWLClass> elements, ArrayList<OWLClass> varList){
		//make sure class exists and hasn't already been visited
		OWLClass c = factory.getOWLClass(cls.getIRI());
		if(cls == null || elements.contains(cls)){
			return;
		}
		
		Set<OWLClassExpression> subExp = cls.getSubClasses(manager.getOntologies());
		//System.out.println("Class " + cls.asOWLClass().getIRI());
		for(OWLClassExpression subCls : subExp){
			//System.out.println("Expression: " + subCls.asOWLClass().toString());
			if(!elements.contains(cls.asOWLClass())){
				elements.add(cls.asOWLClass());
			}
			if(!subCls.asOWLClass().getIRI().getNamespace().equalsIgnoreCase(OntologyConstants.SO_PM+"#")){
				varList.add(subCls.asOWLClass());
			}
			
			getVariableList(subCls.asOWLClass(), elements, varList);
		}
		
		
		
	}

	private static HashMap<String,ArrayList<String>> getClassDetails(OWLClass cls){
		HashMap<String, ArrayList<String>> details = new HashMap<String, ArrayList<String>>();
		ArrayList<String> modifiers = new ArrayList<String>();
		ArrayList<String> relations = new ArrayList<String>();
		
		Set<OWLClassExpression> exp = cls.getSuperClasses(ontology);
		for(OWLClassExpression ce : exp){
			if(ce.getClassExpressionType().compareTo(ClassExpressionType.OBJECT_SOME_VALUES_FROM) == 0){
				OWLObjectSomeValuesFrom obj = (OWLObjectSomeValuesFrom) ce;
				OWLObjectPropertyExpression propExp = obj.getProperty();
				
				if(propExp.asOWLObjectProperty().equals(factory.getOWLObjectProperty(IRI.create(OntologyConstants.HAS_SEM_ATTRIBUTE))) |
						propExp.asOWLObjectProperty().equals(factory.getOWLObjectProperty(IRI.create(OntologyConstants.HAS_LING_ATTRIBUTE)))){
					OWLClassExpression modClass = obj.getFiller();
					//System.out.println(modClass.toString());
					modifiers.add(modClass.toString());
					details.put(MODIFIERS, modifiers);
					//add modifier to dictionary list
					/**if(!modifierDictionary.contains(modClass.toString())){
						//modifierDictionary.add(modClass.toString());
					}**/
				}else{
					//Get remaining axioms and parse out the relation and object to add to variable description
					//System.out.println(obj.toString());
					String relation = obj.getProperty().getNamedProperty().getIRI().getShortForm();
					String object = obj.getFiller().toString();
					//System.out.println(object);
					relations.add(relation + "|" + object);
					details.put(RELATIONS, relations);
				}
				
			}

		}
		
		return details;
	}
	
	private static String getAnnotationString(OWLClass cls, OWLAnnotationProperty annotationProperty){
		String str = "";
		Set<OWLAnnotation> labels = cls.getAnnotations(ontology, annotationProperty);
		if(!labels.isEmpty()){
			Iterator<OWLAnnotation> iter = labels.iterator();
			while(iter.hasNext()){
				OWLAnnotation label = iter.next();
				String temp = label.getValue().toString();
				temp = temp.substring(temp.indexOf("\"")+1, temp.lastIndexOf("\""));
				str = temp;
				break;
			}
			
		}
		return str;
	}
	

	
	private static ArrayList<String> getAnnotationList(OWLClass cls, OWLAnnotationProperty annotationProperty){
		ArrayList<String> labelSet = new ArrayList<String>();
		Set<OWLAnnotation> annotations = cls.getAnnotations(ontology, annotationProperty);
		if(!annotations.isEmpty()){
			Iterator<OWLAnnotation> iter = annotations.iterator();
			while(iter.hasNext()){
				OWLAnnotation ann = iter.next();
				String temp = ann.getValue().toString();
				temp = temp.substring(temp.indexOf("\"")+1, temp.lastIndexOf("\""));
				labelSet.add(temp);
			}
		}
		return labelSet;
	}
	
	public Collection<Term> getAnchorDictionary(){
		return anchorDictionary.values();
	}
	
	public Collection<Modifier> getModifierDictionary() throws Exception{
		return modifierDictionary.values();
	}
	
	public ArrayList<Modifier> getClosureDictionary(){
		return closureDictionary;
	}
	
	private void getPropertyList(OWLObjectProperty prop, ArrayList<OWLObjectProperty> properties, ArrayList<OWLObjectProperty> propList){
		//make sure class exists and hasn't already been visited
		//OWLObjectProperty c = factory.getOWLClass(cls.getIRI());
		if(prop == null || properties.contains(prop)){
			return;
		}
		
		
		Set<OWLObjectPropertyExpression> subExp = prop.getSubProperties(manager.getOntologies());//cls.getSubClasses(manager.getOntologies());
		//System.out.println("Class " + cls.asOWLClass().getIRI());
		for(OWLObjectPropertyExpression subProp : subExp){
			//System.out.println("Expression: " + subCls.asOWLClass().toString());
			if(!properties.contains(prop.asOWLObjectProperty())){
				properties.add(prop.asOWLObjectProperty());
			}
			if(!propList.contains(subProp.asOWLObjectProperty())){
				propList.add(subProp.asOWLObjectProperty());
			}
			
			getPropertyList(subProp.asOWLObjectProperty(), properties, propList);
		}
		
		
		
	}
	
	public ArrayList<OWLObjectProperty> getAllProperties(OWLObjectProperty prop){
		ArrayList<OWLObjectProperty> props = new ArrayList<OWLObjectProperty>();
		getPropertyList(prop, new ArrayList<OWLObjectProperty>(), props);
		
		return props;
	}
	
	
	
}

package edu.utah.blulab.domainontology;

import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

public class OntologyConstants {
	public static final String CT_PM = "http://blulab.chpc.utah.edu/ontologies/v2/ConText.owl";
	public static final String SO_PM = "http://blulab.chpc.utah.edu/ontologies/v2/Schema.owl";
	//public static final String MO_PM = "http://blulab.chpc.utah.edu/ontologies/ModifierOntology.owl";
	public static final String TM_PM = "http://blulab.chpc.utah.edu/ontologies/TermMapping.owl";
	public static final String PREF_LABEL = TM_PM + "#preferredTerm"; //SO_PM + "#prefLabel";
	public static final String ALT_LABEL = TM_PM + "#synonym"; //SO_PM + "#altLabel";
	public static final String HIDDEN_LABEL = TM_PM + "#misspelling"; //SO_PM + "#hiddenLabel";
	public static final String ABR_LABEL = TM_PM + "#abbreviation"; //SO_PM + "#abbrLabel";
	public static final String SUBJ_EXP_LABEL = TM_PM + "#subjectiveExpression"; //SO_PM + "#subjExpLabel";
	public static final String REGEX = TM_PM + "#regex"; //SO_PM + "#regex";
	public static final String PREF_CUI = TM_PM + "#code"; //SO_PM + "#prefCUI";
	public static final String ALT_CUI = TM_PM + "#alternateCode"; //SO_PM + "#altCUI";
	public static final String DEFINITION = TM_PM + "#definition";
	public static final String SEMANTIC_TYPE = TM_PM + "#semanticType";
	public static final String SEC_HEADING = SO_PM + "#sectionHeader";
	public static final String DOC_TYPE = SO_PM + "#documentType";
	public static final String WINDOW = CT_PM + "#windowSize";
	public static final String ACTION_EN = CT_PM + "#hasActionEn";
	public static final String ACTION_DE = CT_PM + "#hasActionDe";
	public static final String ACTION_SV = CT_PM + "#hasActionSv";
	public static final String HAS_TERMINATION = CT_PM + "#hasTermination";
	public static final String HAS_PSEUDO = CT_PM + "#hasPseudo";
	public static final String ANNOTATION_TYPE = SO_PM + "#hasAnnotationType";
	
	
	
	public static final String HAS_SEM_ATTRIBUTE = SO_PM + "#hasSemanticModifier";
	public static final String HAS_LING_ATTRIBUTE = SO_PM + "#hasLinguisticModifier";
	public static final String HAS_NUM_ATTRIBUTE = SO_PM + "#hasNumericModifier";
	public static final String HAS_RELATION = SO_PM + "#hasRelation";
	public static final String HAS_ANCHOR = SO_PM + "#hasAnchor";
	
	public static final String HAS_QUANTITY_VALUE = SO_PM + "#hasQuantityValue";
	public static final String HAS_LOW_VALUE = SO_PM + "#hasLowValue";
	public static final String HAS_HIGH_VALUE = SO_PM + "#hasHighValue";
	public static final String HAS_NUMERATOR = SO_PM + "#hasNumeratorValue";
	public static final String HAS_DENOMINATOR = SO_PM + "#hasDenominatorValue";
	public static final String HAS_1DIMENSION = SO_PM + "#has1DimensionValue";
	public static final String HAS_2DIMENSION = SO_PM + "#has2DimensionValue";
	public static final String HAS_3DIMENSION = SO_PM + "#has3DimensionValue";
	public static final String QUANTITY = CT_PM + "#Quantity";
	public static final String RANGE = CT_PM + "#RangeModifier";
	public static final String DIMENSION = CT_PM + "#DimensionalMeasurement";
	public static final String RATIO = CT_PM + "#Ratio";
	
	public static final String FORWARD_ACTION = "forward";
	public static final String BACKWARD_ACTION = "backward";
	public static final String BIDIRECTIONAL_ACTION = "bidirectional";
	public static final String DISCONTINUOUS_ACTION = "discontinuous";
	public static final String TERMINATE_ACTION = "terminate";
	
	public static final String ANNOTATION = SO_PM + "#AnnotationOntology";
	public static final String EVENT = SO_PM + "#Event";
	public static final String ENTITY = SO_PM + "#Entity";
	public static final String PATIENT = SO_PM + "#Patient";
	public static final String ALLERGY = SO_PM + "#AllergyIntolerance";
	public static final String CONDITION = SO_PM + "#Condition";
	public static final String DISEASE = SO_PM + "#DiseaseDisorder";
	public static final String SYMPTOM = SO_PM + "#SignSymptom";
	public static final String FINDING = SO_PM + "#Finding";
	public static final String OBSERVATION = SO_PM + "#Observation";
	public static final String ENCOUNTER = SO_PM + "#Encounter";
	public static final String MEDICATION = SO_PM + "#MedicationStatement";
	public static final String PROCEDURE = SO_PM + "#Procedure";
	public static final String DIAGNOSTIC_PROCEDURE = SO_PM + "#DiagnosticProcedure";
	public static final String THERAPEUTIC_PROCEDURE = SO_PM + "#TherapeuticProcedure";
	public static final String ANCHOR = SO_PM + "#Anchor";
	public static final String CLOSURE = CT_PM + "#Closure";
	public static final String PSEUDO_MODIFIER = CT_PM + "#PseudoModifier";
	public static final String PSEUDO_ANCHOR = CT_PM + "#PseudoAnchor";
	public static final String SECTION_TYPE = SO_PM + "DocumentSection";
	
	public static final String LINGUISTIC_MODIFIER = CT_PM + "#LinguisticModifier";
	public static final String SEMANTIC_MODIFIER = CT_PM + "#SemanticModifier";
	public static final String NUMERIC_MODIFIER = CT_PM + "#NumericModifier";
	public static final String UNIT = CT_PM + "#Unit";


	public static final String HAS_CORPUS = SO_PM + "#hasCorpus";
	public static final String HAS_ANNOTATION_TYPE = SO_PM + "#hasAnnotationType";
	public static final String HAS_DOCUMENT_ID = SO_PM + "#hasDocumentID";
	public static final String HAS_SPAN = SO_PM + "#hasSpan";

	public static final String COMPOUND_ANCHOR = SO_PM + "#CompoundAnchor";
	public static final String HAS_COMPOUND_ARGUMENT = SO_PM + "#hasCompoundArgument";
	public static final String HAS_COMPOUND_ARGUMENT1 = SO_PM + "#hasCompoundArgument1";
	public static final String HAS_COMPOUND_ARGUMENT2 = SO_PM + "#hasCompoundArgument2";
	public static final String HAS_COMPOUND_ARGUMENT3 = SO_PM + "#hasCompoundArgument3";
	public static final String HAS_COMPOUND_ARGUMENT4 = SO_PM + "#hasCompoundArgument4";
	public static final String HAS_COMPOUND_ARGUMENT5 = SO_PM + "#hasCompoundArgument5";

}

package edu.utah.blulab.domainontology;

import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by melissa on 12/14/16.
 */
public class CompoundAnchor extends Anchor {
    private String uri;
    private DomainOntology domain;

    public CompoundAnchor(String uri, DomainOntology domain){
        this.uri = uri;
        this.domain = domain;

    }

    public boolean isOrdered(){
        //TODO: If numbered obj props used, then return true; else return false

        return false;
    }

    public ArrayList<LogicExpression<Anchor>> getOrderedArgumentList(){
        ArrayList<LogicExpression<Anchor>> arguments = new ArrayList<LogicExpression<Anchor>>();

        Set<OWLClassExpression> clsExpressions = domain.getClass(uri).getEquivalentClasses(domain.getOntology());
        for(OWLClassExpression exp : clsExpressions){
            LogicExpression<Anchor> arg1 = this.getArgumentExpression(OntologyConstants.HAS_COMPOUND_ARGUMENT1, exp);
            LogicExpression<Anchor> arg2 = this.getArgumentExpression(OntologyConstants.HAS_COMPOUND_ARGUMENT2, exp);
            LogicExpression<Anchor> arg3 = this.getArgumentExpression(OntologyConstants.HAS_COMPOUND_ARGUMENT3, exp);
            LogicExpression<Anchor> arg4 = this.getArgumentExpression(OntologyConstants.HAS_COMPOUND_ARGUMENT4, exp);
            LogicExpression<Anchor> arg5 = this.getArgumentExpression(OntologyConstants.HAS_COMPOUND_ARGUMENT5, exp);
            if(arg1 != null || !arg1.isEmpty()){
                arguments.add(0, arg1);
            }
            if(arg2 != null || !arg2.isEmpty()){
                arguments.add(1, arg2);
            }
            if(arg3 != null || !arg3.isEmpty()){
                arguments.add(2, arg3);
            }
            if(arg4 != null || !arg4.isEmpty()){
                arguments.add(3, arg4);
            }
            if(arg5 != null || !arg5.isEmpty()){
                arguments.add(4, arg5);
            }

        }



        return arguments;
    }

    public LogicExpression<Anchor> getArgumentExpression(String objProp, OWLClassExpression exp){
        LogicExpression<Anchor> value = null;
        OWLObjectProperty hasArgument = domain.getFactory().getOWLObjectProperty(IRI.create(
                objProp));

        if(exp.getClassExpressionType().equals(ClassExpressionType.OBJECT_SOME_VALUES_FROM)){

            OWLObjectSomeValuesFrom someValuesFrom = (OWLObjectSomeValuesFrom) exp;
            if(hasArgument.equals(someValuesFrom.getProperty().asOWLObjectProperty())){

                OWLClassExpression fillerExpression = someValuesFrom.getFiller();
                if(fillerExpression.getClassExpressionType().equals(ClassExpressionType.OWL_CLASS)){
                    value = new LogicExpression<Anchor>(LogicExpression.SINGLE);
                    value.add(new Anchor(fillerExpression.asOWLClass().getIRI().toString(), domain));
                }else if(fillerExpression.getClassExpressionType().equals(ClassExpressionType.OBJECT_UNION_OF)){
                    OWLObjectUnionOf objectUnionOf = (OWLObjectUnionOf) fillerExpression;
                    value = new LogicExpression<Anchor>(LogicExpression.OR);
                    List<OWLClassExpression> objectFiller = objectUnionOf.getOperandsAsList();
                    for(OWLClassExpression clsExp : objectFiller){
                        value.add(new Anchor(clsExp.asOWLClass().getIRI().toString(), domain));
                    }
                }



            }
        }/*else if(exp.getClassExpressionType().equals(ClassExpressionType.OBJECT_UNION_OF)){

            OWLObjectUnionOf objectUnionOf = (OWLObjectUnionOf) exp;
            if(objectUnionOf.getObjectPropertiesInSignature().contains(hasArgument)){
                value = new LogicExpression<Anchor>(LogicExpression.OR);
                List<OWLClassExpression> objectFiller = objectUnionOf.getOperandsAsList();
                for(OWLClassExpression clsExp : objectFiller){
                    value.add(new Anchor(clsExp.asOWLClass().getIRI().toString(), domain));
                }
            }
        }*/

        return value;
    }



    public ArrayList<LogicExpression<Anchor>> getArgumentList(){
        ArrayList<LogicExpression<Anchor>> arguments = new ArrayList<LogicExpression<Anchor>>();

        Set<OWLClassExpression> clsExpressions = domain.getClass(uri).getEquivalentClasses(domain.getOntology());
        for(OWLClassExpression exp : clsExpressions){
            LogicExpression<Anchor> argument = this.getArgumentExpression(OntologyConstants.HAS_COMPOUND_ARGUMENT, exp);
            if(argument != null || !argument.isEmpty()){
                arguments.add(argument);
            }

        }

        return arguments;

    }

    @Override
    public String toString(){
        return "CompoundAnchor: " +
                " isOrdered = " + this.isOrdered() +
                " unordered arguments = " + this.getArgumentList().toString() +
               // " ordered arguments = " + this.getOrderedArgumentList() +
                "]";
    }
}

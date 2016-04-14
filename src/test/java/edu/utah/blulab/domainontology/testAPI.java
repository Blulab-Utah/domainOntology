package edu.utah.blulab.domainontology;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class testAPI {

    @Test
    public void example1() throws Exception {
        // TODO Auto-generated method stub
        DomainOntology domain = new DomainOntology("src/main/resources/98_heartFailure.owl", false);
        //DomainOntology domain = new DomainOntology("/Users/mtharp/use_cases/DomainOntologies/pneumonia.owl");
        //DomainOntology domain = new DomainOntology("/Users/mtharp/Desktop/vincipneu.owl.xml");
        //DomainOntology domain = new DomainOntology("C:\\Users\\Bill\\Desktop\\carotid stenosis.owl");
        //DomainOntology domain = new DomainOntology("DomainOntologyAPI/src/main/resources/colonoscopy_20141001.owl");
        //DomainOntology domain = new DomainOntology("src/main/resources/colonoscopy_20141001.owl");
        //domain.getVariable("leukocytosis");
        //domain.getVariable("KA247");

        ArrayList<Variable> domainVariables = domain.getAllVariables();
        System.out.println("********** Domain Variables: **********");
        for (Variable var : domainVariables) {
            System.out.println(var.toString());
        }

        System.out.println("********** Modifier Dictionary: **********");
        ArrayList<Modifier> modifierDictionary = domain.createModifierDictionary();
        for (Modifier modifier : modifierDictionary) {
            System.out.println(modifier.toString());
        }

        System.out.println("********** Target Dictionary: **********");
        ArrayList<Term> targetDictionary = domain.createAnchorDictionary();
        for (Term target : targetDictionary) {
            System.out.println(target.toString());
        }
    }

    @Test
    public void example2() throws Exception {
        DomainOntology domain = new DomainOntology("src/main/resources/98_heartFailure.owl", true);
        System.out.println("domain's methods");
        domain.getDisplayName("");
        for (Method m : domain.getClass().getMethods()) {
            if (m.getParameterTypes().length == 0 && !m.getName().equals("wait") && !m.getName().startsWith("notif")) {
                System.out.print(m.getName());
                System.out.print(":\t" + m.invoke(domain) + "\n");
                if (m.getReturnType().equals(ArrayList.class)) {
                    System.out.println("\tReturned list size: " + ((ArrayList) m.invoke(domain)).size());
                }
            } else {
                System.out.println("Not called: " + m.getName());
                System.out.println("Parameter length: " + m.getParameterTypes().length);
            }

        }

        System.out.println("\nvariable's methods");
        Variable var = domain.getVariable("KA127");
        for (Method m : var.getClass().getMethods()) {
            if (m.getParameterTypes().length == 0 && !m.getName().equals("wait") && !m.getName().startsWith("notif")) {
                System.out.println(m.getName() + ":\t" + m.invoke(var));
            } else {
                System.out.println("Not called: " + m.getName());
                System.out.println("Parameter length: " + m.getParameterTypes().length);
            }
        }
    }


}

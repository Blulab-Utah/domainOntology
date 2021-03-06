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


import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class testAPI {


    public static void main(String[] args) throws Exception {
        DomainOntology domain = new DomainOntology("/Users/melissa/git/useCases/colonoscopyQuality.owl", false);
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

        /**System.out.println("********** Modifier Dictionary: **********");
         ArrayList<Modifier> modifierDictionary = domain.createModifierDictionary();
         for(Modifier modifier : modifierDictionary){
         System.out.println(modifier.toString());
         }**/

        System.out.println("********** Modifier Map: **********");
        HashMap<String, ArrayList<Modifier>> modifierMap = domain.createModifierTypeMap();
        Iterator iterator = modifierMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ArrayList<Modifier>> modifierEntry =
                    (Map.Entry<String, ArrayList<Modifier>>) iterator.next();
            System.out.print(modifierEntry.getKey() + ":\t");
            for (Modifier modifier : modifierEntry.getValue()) {
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

    @Test
    public void example1() throws Exception {
        // TODO Auto-generated method stub
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
        for (Variable var : domainVariables) {
            System.out.println(var.toString());
        }

        System.out.println("********** Modifier Dictionary: **********");
        ArrayList<Modifier> modifierDictionary = domain.createModifierDictionary();
        for (Modifier modifier : modifierDictionary) {
            System.out.println(modifier.toString());
        }

        System.out.println("********** Target Dictionary: **********");
        ArrayList<Anchor> targetDictionary = domain.createAnchorDictionary();
        for (Anchor target : targetDictionary) {
            System.out.println(target.toString());
        }
    }



    @Test
    public void example3() throws Exception {
        DomainOntology domain = new DomainOntology("src/main/resources/colonoscopyQuality.owl", false);
        System.out.println("domain's methods");
        ArrayList<Variable> domainVariables = domain.getAllEvents();
        for (Variable var : domainVariables) {
            System.out.println("Variable Name:" + var.getVarName());
            ArrayList<LogicExpression<Anchor>> logicExpressions = var.getAnchor();
            for (LogicExpression<Anchor> logicExpression : logicExpressions) {
                if (logicExpression.isSingleExpression()) {
                    for (Anchor Anchor : logicExpression) {
                        String targetTypeName = var.getVarName();
                        targetTypeName = targetTypeName.replaceAll(" +", "_").toUpperCase();
                        if (Anchor.getSynonym().size() > 0) {
                            System.out.println("\tSynonyms:");
                            for (String s : Anchor.getSynonym()) {
                                System.out.println("\t\t" + s);
                            }
                        }
                        System.out.println("\tAbbreviation:");
                        if (Anchor.getAbbreviation().size() > 0) {
                            for (String s : Anchor.getAbbreviation())
                                System.out.println("\t\t" + s);
                        }
                        System.out.println("\tMisspelling:");
                        if (Anchor.getMisspelling().size() > 0) {
                            for (String s : Anchor.getMisspelling())
                                System.out.println("\t\t" + s);
                        }
                        HashMap<String, LogicExpression<Modifier>> modifiers = var.getModifiers();
                        for (LogicExpression<Modifier> modifier : modifiers.values()) {
                            System.out.println("\tModifier type: " + modifier.getType() + "\t" + modifier.isSingleExpression());
                            for (Modifier ele : modifier) {
                                System.out.println("\tModifier name: " + ele.getModName());
                                System.out.println("\t\tModifier get pseudo " + ele.getPseudos());
                                System.out.println("\t\tModifier direct children: " + ele.getDirectChildren());
                                System.out.println("\t\tIs default? " + ele.isDefault());
                            }
                        }

                    }
                }
            }
        }
    }

    @Test
    public void example4() throws Exception {
        DomainOntology domain = new DomainOntology("src/main/resources/colonoscopyQuality.owl", true);
        System.out.println("domain's methods");
        ArrayList<Variable> domainVariables = domain.getAllVariables();
        for (Variable var : domainVariables) {
            System.out.println("Variable Name:" + var.getVarName());
            ArrayList<LogicExpression<Anchor>> logicExpressions = var.getAnchor();
            for (LogicExpression<Anchor> logicExpression : logicExpressions) {
                System.out.println("\tlogicExpression type: " + logicExpression.getType());
                if (!logicExpression.isSingleExpression()) {
                    for (Anchor Anchor : logicExpression) {
                        String targetTypeName = var.getVarName();
                        targetTypeName = targetTypeName.replaceAll(" +", "_").toUpperCase();
                        if (Anchor.getSynonym().size() > 0) {
                            System.out.println("\tSynonyms:");
                            for (String s : Anchor.getSynonym()) {
                                System.out.println("\t\t" + s);
                            }
                        }
                        System.out.println("\tAbbreviation:");
                        if (Anchor.getAbbreviation().size() > 0) {
                            for (String s : Anchor.getAbbreviation())
                                System.out.println("\t\t" + s);
                        }
                        System.out.println("\tMisspelling:");
                        if (Anchor.getMisspelling().size() > 0) {
                            for (String s : Anchor.getMisspelling())
                                System.out.println("\t\t" + s);
                        }
                    }
                }
            }
        }
    }

    /**
     * Use reflection to test methods
     *
     * @throws Exception
     */
    @Test
    public void testReflectionMethods() throws Exception {
        DomainOntology domain = new DomainOntology("src/main/resources/colonoscopyQuality.owl", false);
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

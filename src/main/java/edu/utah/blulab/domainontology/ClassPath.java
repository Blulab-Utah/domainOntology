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

import org.semanticweb.owlapi.model.OWLClass;

import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by melissa on 10/27/16.
 */
public class ClassPath extends ArrayList<OWLClass> {
    public ClassPath(){
        super();
    }

    public ClassPath(Collection<OWLClass> c){
        super(c);
    }

    public TreePath toTreePath(){
        return new TreePath(toArray(new OWLClass[0]));
    }

    public String toString(){
        String s = super.toString();
        s = s.substring(1,s.length()-1);
        return s.replaceAll(","," ->");
    }
}

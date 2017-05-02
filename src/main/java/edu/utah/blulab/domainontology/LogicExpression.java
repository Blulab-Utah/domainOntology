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

@SuppressWarnings("serial")
public class LogicExpression<E> extends ArrayList<E> {
	private String type;
	public static final String AND = "AND";
	public static final String OR = "OR";
	public static final String COMPLEMENT = "COMPLEMENT";
	public static final String SINGLE = "SINGLE";
	
	public LogicExpression(String type){
		super();
		this.type = type;
	}
	
	public LogicExpression(){
		type = "";
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}

	public boolean isAndExpression(){
		if(type.equalsIgnoreCase(AND)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isOrExpression(){
		if(type.equalsIgnoreCase(OR)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isComplementExpression(){
		if(type.equalsIgnoreCase(COMPLEMENT)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isSingleExpression(){
		if(type.equalsIgnoreCase(SINGLE)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String toString() {
		return "LogicExpression [type=" + type +  ", toString()=" + super.toString() + "]";
	}
	
}

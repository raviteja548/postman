package com.tmo.postman.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.tmo.postman.model.RowData;
import com.tmo.postman.util.DataMappingReader;

public class GeneratePostMan {
	public static void main(String args[]) {
		DataMappingReader reader = new DataMappingReader();
		List<RowData> rowData = reader.readDataMappingFile("src/main/resources/request.xls");
		String json = new GeneratePostMan().generateJSONRequest(rowData);
		System.out.println(json);

	}

	public String generateJSONRequest(List<RowData> rowData) {
		List<RowData> stack = new ArrayList<RowData>();
		Map<String, Object> jsonObjects = new LinkedHashMap<String, Object>();

		JSONObject rootObject = new JSONObject();
		String rootObjectName = null;
		int count = 0;
		for (RowData rd : rowData) {
			count++;
			// root element

			if (rd.getTyp().equals("object")) {

				if (rd.getLastElement().equals(rd.getPreviousElement())) {
					rootObjectName = rd.getLastElement();
					//rootObject.put(rd.getLastElement(), null);
					stack.add(rd);
					jsonObjects.put(rd.getLastElement(), rootObject);
				} else {
					// not a root element, but this is a object/simple element
					// which is under previously processed root
					int size = stack.size();
					if (rd.getPreviousElement().equals(stack.get(size-1).getLastElement()) && rd.getTyp().contains("object")) {

						JSONObject object = new JSONObject();
					//	object.put(rd.getLastElement(), null);
						stack.add(rd);
						jsonObjects.put(rd.getLastElement(), object);

					}
					if (!(rd.getPreviousElement().equals(stack.get(size-1).getLastElement())) && rd.getTyp().contains("object")) {

						for (int i = stack.size(); i > 0; i--) {
							if (stack.get(i - 1).getLastElement().equals(rd.getPreviousElement())) {

								break;

							} else {
								
								
									String lastElementInStack = stack.get(i - 1).getLastElement();
									String previousElementInStack = stack.get(i - 1).getPreviousElement();
	
									JSONObject toBeRemoved = (JSONObject) jsonObjects.get(lastElementInStack);
									JSONObject toBeInAdded = (JSONObject) jsonObjects.get(previousElementInStack);
									toBeInAdded.put(lastElementInStack, toBeRemoved);
									jsonObjects.put(previousElementInStack, toBeInAdded);
									jsonObjects.remove(lastElementInStack);
	
									stack.remove(i - 1);
								}	
							

						}
						JSONObject object = new JSONObject();
						stack.add(rd);
						jsonObjects.put(rd.getLastElement(), object);
						

					}

				}

			}// object condition ends
			else {
				// it is simple type
				// fetch the object of simpletype
				JSONObject simpleElementToBeAdded = (JSONObject) jsonObjects.get(rd.getPreviousElement());

				// add child object to parent object
				if(rd.getLastElement().contains("@") || rd.getLastElement().contains("#"))
				simpleElementToBeAdded.put(rd.getLastElement(), "{" + rd.getPreviousElement()+"."+rd.getLastElement().substring(1,rd.getLastElement().length()) + "}");
				else
					simpleElementToBeAdded.put(rd.getLastElement(), "{" + rd.getLastElement() + "}");
				// re-add object in map

				jsonObjects.put(rd.getPreviousElement(), simpleElementToBeAdded);
				
				if(rowData.size()==count){
					System.out.println("------------------");
					for (int i = stack.size(); i > 0; i--) {
						if (stack.get(i - 1).getLastElement().equals(rd.getPreviousElement())) {

							break;

						} else {
							String lastElementInStack = stack.get(i - 1).getLastElement();
							String previousElementInStack = stack.get(i - 1).getPreviousElement();

							JSONObject toBeRemoved = (JSONObject) jsonObjects.get(lastElementInStack);
							JSONObject toBeInAdded = (JSONObject) jsonObjects.get(previousElementInStack);
							toBeInAdded.put(lastElementInStack, toBeRemoved);
							jsonObjects.put(previousElementInStack, toBeInAdded);
							jsonObjects.remove(lastElementInStack);

							stack.remove(i - 1);

						}

					}
				}

			}
		}
		
		//all rows processing done
		
		JSONObject finalObject = (JSONObject) jsonObjects.get(rootObjectName);
		JSONObject root = new JSONObject();
		root.put(rootObjectName, finalObject);
		
		
		//System.out.println(jsonObjects);

		return root.toJSONString();
	}

}

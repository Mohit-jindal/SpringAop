package org.koushik.javabrains.service;

import org.koushik.javabrains.model.Circle;
import org.koushik.javabrains.model.Triangle;

public class FactoryService {
	
	public Object getBean(String beanType)
	{
		if("shapeService".equals(beanType)) return new ShapeServiceProxy();
		if("circle".equals(beanType)) return new Circle();
		if("triangle".equals(beanType)) return new Triangle();
		return null;
	}

}

package org.eclipse.epsilon.webchecker;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;
import org.jsoup.nodes.Element;

class Classes {
	private Element element;
	public Classes(Element element) {
		this.element = element;
	}
	
	public boolean includes(String className) {
		if (element.hasClass(className) || element.parent().hasClass(className)) {
			return true;
		} else {
			return false;
		}
	}
}

class Parent {
	private Classes classes;
	public Parent(Element element) {
		classes = new Classes(element);
	}
	
	public Classes getClasses() {
		return classes;
	}
}


class GuardedElement {
	public Element element; 
	 
	GuardedElement(Element element) {
		this.element = element;
	}
	public boolean includes(String className) {	
		if (element.getElementsByClass(className).size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}

public class WebCheckerPropertyGetter extends JavaPropertyGetter {

	@Override
	public Object invoke(Object object, String property) throws EolRuntimeException {
		
//		System.out.println("Entering invoke() in WebCheckerPropertyGetter class");
		
		Element element = (Element) object;
		
		if (property.equals("class")) {
			GuardedElement htmlClass = new GuardedElement(element);
			return htmlClass;	
		} else if (property.equals("parent")) {
			Parent parent = new Parent(element);
			return parent;
		} else return null;
				
	}
}

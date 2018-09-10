package org.eclipse.epsilon.webchecker;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;


class Classes {
	private Element element;
	public Classes(Element element) {
		this.element = element;
	}
	
	public boolean includes(String className) {
		if (className.endsWith("-*")) {
			String cn = className.substring(0, className.length() - 1);
			for (String c : element.classNames()) {
				return c.startsWith(cn);	 
			}
		}
		return (element.hasClass(className) || element.parent().hasClass(className));
	}
}

class Parent {
	private Classes classes;
	private Element element;
	public Parent(Element element) {
		this.element = element;
		classes = new Classes(element);
	}
	
	public Classes getClasses() {
		return classes;
	}
	/**
	 * Return <code>true</code> if element parent is the specified tag.
	 * @param tagName The tag name to check
	 * @return
	 */
	public boolean is(String tagName) {
		return element.parent().tagName().equals(tagName);
	}
}


class GuardedElement {
	private Element element;
	private Classes classes;
	
	GuardedElement(Element element) {
		this.element = element;
		classes = new Classes(element);
	}
	/**
	 * Returns true if the element includes the class name, false otherwise. Supports generic class names such as <b>img-*</b>, where <b>*</b> is a wild card.
	 * @param className The class name to check.
	 * @return
	 */
	public boolean includes(String className) {		
		if (className.endsWith("-*")) {
			String cn = className.substring(0, className.length() - 1);
			for (String c : element.classNames()) {
				return c.startsWith(cn);	 
			}
		} 
		return (element.hasClass(className));
	}
	public Classes getClasses() {
		return classes;
	}
	
	/**
	 * Convenient method to guard the element class. Always return true.
	 * @return <code>true</code>
	 */
	public boolean exists() {
		return true;
	}
}

public class WebCheckerPropertyGetter extends JavaPropertyGetter {

	@Override
	public Object invoke(Object object, String property) throws EolRuntimeException {
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

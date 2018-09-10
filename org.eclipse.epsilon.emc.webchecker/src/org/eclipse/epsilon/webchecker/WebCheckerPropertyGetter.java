package org.eclipse.epsilon.webchecker;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

class Type {
	private Element element;
	public Type(Element element) {
		this.element = element;
	}
	public boolean exists() {
		return true;
	}
	public boolean hasChild(String tagName) {	
		for (Element e : element.children()) {
			return e.tagName().equals(tagName);
		}
		return false;
	}
}
class Classes {
	private Element element;
	public Classes(Element element) {
		this.element = element;
	}
	
	public boolean includes(String className) {
		if (className.endsWith("-*")) {
			String cn = className.substring(0, className.length() - 1);
			//Check the element itself
			for (String c : element.parent().classNames()) {
				return c.startsWith(cn);	 
			}
		}
		return element.parent().hasClass(className);
	}
}
//parent element of the selected type
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

//Selected type such as t_div, t_img...etc.
class GuardedElement {
	private Element element;
	
	GuardedElement(Element element) {
		this.element = element;
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
			return new GuardedElement(element);
		} else if (property.equals("parent")) {
			return new Parent(element);
		} else if (property.equals("type")) {
			return  new Type(element);
		} else return null;
				
	}
}

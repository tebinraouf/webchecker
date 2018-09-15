package org.eclipse.epsilon.webchecker;
import java.util.Set;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;
import org.jsoup.nodes.Element;


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
	public boolean hasClass(String className) {
		for (Element e : element.children()) {
			return e.hasClass(className);
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
			boolean doesStart = false;
			for (String c : element.parent().classNames()) {
				doesStart = c.startsWith(cn);	 
			}
			return doesStart;
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
	public boolean hasClass(String className) {
		return element.parent().hasClass(className);
	}
	/**
	 * Return <code>true</code> if the parent element contains the classes. Classes here are separated by a space.
	 * @param className list of class names separated by a space.
	 * @return
	 */
	public boolean hasClasses(String className) {
		
		String[] classNames = className.split("\\s+");
		Set<String> parentClasses = element.parent().classNames();
		
		boolean doesContain = false;
		for (String aClass : classNames) {		
			if (aClass.endsWith("-*")) {
				String cn = aClass.substring(0, aClass.length() - 1);
				for (String aParentClass : parentClasses) {
					doesContain = aParentClass.startsWith(cn);
				}
			} else {
				doesContain = parentClasses.contains(aClass);
			}
		}
		return doesContain;
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
			boolean doesStart = false;
			for (String c : element.classNames()) {
				doesStart = c.startsWith(cn);	 
			}
			return doesStart;
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
		} else if (property.equals("type") || property.equals("children")) {
			return  new Type(element);
		} else return null;
				
	}
}

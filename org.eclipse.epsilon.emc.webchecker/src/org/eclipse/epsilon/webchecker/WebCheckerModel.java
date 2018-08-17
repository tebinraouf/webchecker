package org.eclipse.epsilon.webchecker;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.net.URISyntaxException;
import java.net.URI;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.plainxml.PlainXmlType;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.models.CachedModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class WebCheckerModel extends CachedModel<Element>  {

	protected static String ELEMENT_TYPE = "Element";	
	protected File file = null;
	protected String uri = null;
	public static String PROPERTY_FILE = "file";
	public static String PROPERTY_URI = "uri";
	
	protected WebCheckerPropertyGetter propertyGetter = new WebCheckerPropertyGetter();
	protected WebCheckerPropertySetter propertySetter = new WebCheckerPropertySetter();
		
	/* Objects in this model are a list of elements */
	protected List<Element> elements;
	
	private Document document;
	//SECTION 2 Parse Model
	@Override
	protected void loadModel() throws EolModelLoadingException {
		try {
			document = Jsoup.parse(file, "UTF-8");		
			elements = document.getAllElements();
		}
		catch (Exception ex) {
			throw new EolModelLoadingException(ex, this);
		}
	}
	//SECTION 1 Load Model
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);
		String fileProperty = properties.getProperty(PROPERTY_FILE);
		
		if (fileProperty != null && fileProperty.length() > 0) {			
			file = new File(resolver.resolve(fileProperty));
		}
		else {
			uri = properties.getProperty(PROPERTY_URI);
			if (uri.startsWith("file:")) {
				try {
					file = new File(new URI(uri));
				} catch (URISyntaxException e) {
					throw new EolModelLoadingException(e, this);
				}
			}
		}
		load();
	}
	//SECTION 3 Identify Type
	@Override
	public boolean hasType(String type) {
		boolean result = ELEMENT_TYPE.equals(type) || PlainXmlType.parse(type) != null;
		return result;
	}
	//SECTION 4 Handle EVL properties 
	@Override
	public IPropertyGetter getPropertyGetter() {
		return propertyGetter;
	}
	@Override
	public IPropertySetter getPropertySetter() {
		return propertySetter;
	}
	//SECTION Identify EVL context type
	@Override
	public String getTypeNameOf(Object instance) {
		String result = "t_" + ((Element) instance).tagName();
		return result;
	}
	
	@Override
	protected Collection<Element> allContentsFromModel() {
		return elements;
	}

	@Override
	protected Collection<Element> getAllOfTypeFromModel(String type) throws EolModelElementTypeNotFoundException {
		return allContents();
	}
	//SECTION Get the specific section based on type/kind
	@Override
	protected Collection<Element> getAllOfKindFromModel(String kind) throws EolModelElementTypeNotFoundException {
		return document.select(PlainXmlType.parse(kind).getTagName());
	}
	//SECTION Check if the instance is of type the model, Element.
	@Override
	public boolean owns(Object instance) {
		if (instance instanceof Element) {
			return ((Element) instance).ownerDocument() == document;
		}
		else return false;
	}
	
	@Override
	public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
		return null;
	}

	@Override
	public Object getElementById(String id) {
		return null;
	}
	
	@Override
	public String getElementId(Object instance) {
		return null;
	}

	@Override
	public void setElementId(Object instance, String newId) {
		
	}

	@Override
	public boolean isInstantiable(String type) {
		return true;
	}

	@Override
	public boolean store(String location) {
		return false;
	}

	@Override
	public boolean store() {
		return false;
	}

	@Override
	protected Element createInstanceInModel(String type)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		return null;
	}

	@Override
	protected void disposeModel() {
		
	}

	@Override
	protected boolean deleteElementInModel(Object instance) throws EolRuntimeException {
		return false;
	}

	@Override
	protected Object getCacheKeyForType(String type) throws EolModelElementTypeNotFoundException {
		return type;
	}

	@Override
	protected Collection<String> getAllTypeNamesOf(Object instance) {
		return Collections.singleton(getTypeNameOf(instance));
	}
}

package org.eclipse.epsilon.webchecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.evl.*;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.webchecker.WebCheckerModel;

public class WebChecker {

	private WebCheckerModel model = null;
	private EvlModule module = null;
	private String source;
	private String evl;

	public WebChecker() {

		model = new WebCheckerModel();
		
	}
	public WebChecker(String modelSource, String evl) {
		model = new WebCheckerModel();
		module = new EvlModule();
				
		this.source = modelSource;
		this.evl = evl;
	}
	
	public void check() {	
			
		try {
			model.setName("M");
			model.load();

			//Create an EVL instance
			module = new EvlModule();
			module.parse(new File(this.evl));
			
			//Add the model to the EVL context.
			module.getContext().getModelRepository().addModel(model);
			module.getContext().setModule(module);
			module.execute();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public WebCheckerModel getModel() {
		return model;
	}

	public EvlModule getModule() {
		return module;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
		if (Utility.isValidURI(source) || Utility.isValidURL(source)) {
			model.setURI(source);			
		}
		else {
			model.setCode(source);			
		}
	}

	public String getValidation() {
		return evl;
	}

	public void setValidation(String evl) {
		this.evl = evl;
	}
	
	public boolean isValid() {
		return module.getContext().getUnsatisfiedConstraints().size() == 0 ? true : false;
	}
	public List<String> errors() {
		ArrayList<String> msgs = new ArrayList<>();
		List<UnsatisfiedConstraint> unConstraints = module.getContext().getUnsatisfiedConstraints();
		for (UnsatisfiedConstraint uc : unConstraints) {
			msgs.add(uc.toString());
		}
		return msgs;
	}

}

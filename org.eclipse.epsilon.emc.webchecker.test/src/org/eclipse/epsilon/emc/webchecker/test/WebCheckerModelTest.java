package org.eclipse.epsilon.emc.webchecker.test;

import org.eclipse.epsilon.evl.*;
import org.eclipse.epsilon.webchecker.WebCheckerModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WebCheckerModelTest {
	
	protected WebCheckerModel model;
	protected EvlModule module;
	
	@Before
	public void setup() throws Exception {
		
	}
	@Test
	public void testInButtonGroup() throws Exception {
		String html = 
				"<div>" + newline() +
				"  <button type='button' class='btn btn-secondary'>Left</button>" + newline() +
				"</div>" + newline() + 
				"<div class='btn-group' role='group'>" + newline() + 
				"  <button type='button' class='btn btn-secondary'>Middle</button>" + newline() + 
				"  <button type='button' class='btn btn-secondary'>Right</button>" + newline() +
				"</div>";
		String evl = 
				"context t_div {" + newline() + 
				"	constraint InButtonGroup {" + newline() + 
				"		guard : self.class.includes('btn-secondary')" + newline() + 
				"		check : self.parent.classes.includes('btn-group')" + newline() + 
				"		message : 'btn-secondary divs should be contained under btn-group'" + newline() +
				"	}" + newline() + 
				"}";
		setModelAndModule(html, evl);
		
		int moduleResult = module.getContext().getUnsatisfiedConstraints().size();
		assertEquals(1, moduleResult);
	}
	@Test
	public void testColUnderRow() throws Exception {
		
		String html = 
				"<div class='col-sm-4'>Col 1</div>" + newline() + 
				"<div class='col-sm-4'>Col 2</div>" + newline() + 
				"<div class='row'>" + newline() + 
				"  	<div class='col-sm-4'>Col 3</div>" + newline() + 
				"  	<div class='col-sm-4'>Col 4</div>" + newline() + 
				"</div>";
		
		String evl = 
				"context t_div {" + newline() +  
				"    constraint ColUnderRow {" + newline() +  
				"		guard : self.class.includes('col-sm-4')" + newline() +  
				"		check : self.parent.classes.includes('row')" + newline() +  
				"		message : 'col-sm-4 divs should be contained under row'" + newline() + 
				"	}" + newline() + 
				"}";
		
		setModelAndModule(html, evl);
		
		int moduleResult = module.getContext().getUnsatisfiedConstraints().size();
		assertEquals(2, moduleResult);
	}
	@Test
	public void testNavItemUnderNav() throws Exception {
		String html = 
				"   <div>" + newline() + 
				"	   <ul class=''>" + newline() + 
				"			<li class='nav-item'>" + newline() + 
				"				<a class='nav-link active' href='#'>Active</a> </li>" + newline() + 
				"			<li class='nav-item'>" + newline() + 
				"				<a class='nav-link' href='#'>Link</a>" + newline() + 
				"			</li>" + newline() + 
				"			<li class='nav-item'>" + newline() + 
				"				<a class='nav-link' href='#'>Link</a> </li>" + newline() + 
				"			<li class='nav-item'>" + newline() + 
				"				<a class='nav-link disabled' href='#'>Disabled</a>" + newline() + 
				"			</li> " + newline() + 
				"		</ul>" + newline() + 
				"	</div>";
		
		String evl = 
				"context t_ul {" + newline() + 
				"	constraint NavItemUnderNav {" + newline() + 
				"			guard : self.class.includes('nav-item')" + newline() + 
				"			check : self.parent.classes.includes('nav')" + newline() + 
				"			message : 'nav-item should be contained under nav'" + newline() + 
				"		}" + newline() + 
				"}";
		setModelAndModule(html, evl);
		
		int moduleResult = module.getContext().getUnsatisfiedConstraints().size();
		assertEquals(1, moduleResult);
	}
	
	public void setModelAndModule(String html, String EVL) throws Exception {
		//Create a WebCheckerModel instance
		model = new WebCheckerModel();
		model.setName("M");		
		model.setCode(html);		
		model.load();
		
		//Create an EVL instance
		module = new EvlModule();
		module.parse(EVL);
		
		//Add the model to the EVL context.
		module.getContext().getModelRepository().addModel(model);
		module.getContext().setModule(module);
		module.execute();
	}
	public String newline() {
		return System.getProperty("line.separator");
	}
}

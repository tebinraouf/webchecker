/*******************************************************************************
 * Almost all the code here is taken from PlainXMLModel EMC driver by Dimitrios Kolovos
 ******************************************************************************/
package org.eclipse.epsilon.webchecker.dt;


import java.awt.Color;

import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractCachedModelConfigurationDialog;
import org.eclipse.epsilon.webchecker.WebCheckerModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class WebCheckerModelConfigurationDialog extends AbstractCachedModelConfigurationDialog {

	protected Label fileTextLabel;
	protected Text fileText;
	protected Label uriTextLabel;
	protected Text uriText;
	protected Button browseModelFile;
	protected Button filebasedButton;
	
	protected String getModelName() {
		return "Web Checker HTML";
	}

	
	protected String getModelType() {
		return "WebChecker";
	}
	
	@Override
	protected void createGroups(Composite control) {
		super.createGroups(control);
		createFileGroup(control);
	}
	
	private void createFileGroup(Composite parent) {
		final Composite groupContent = createGroupContainer(parent, "Files/URI", 3);
		
		
		filebasedButton = new Button(groupContent, SWT.CHECK);
		GridData filebasedButtonGridData = new GridData(GridData.FILL_HORIZONTAL);
		filebasedButtonGridData.horizontalSpan = 3;
		filebasedButton.setSelection(true);
		filebasedButton.setText("Workspace file");
		filebasedButton.setLayoutData(filebasedButtonGridData);
		filebasedButton.addListener(SWT.Selection, new Listener() {
			
			public void handleEvent(Event event) {
				toggleEnabledFields();
			}
			
		});
		
		
		fileTextLabel = new Label(groupContent, SWT.NONE);
		fileTextLabel.setText("File: ");
		
		fileText = new Text(groupContent, SWT.BORDER);
		fileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		browseModelFile = new Button(groupContent, SWT.NONE);
		browseModelFile.setText("Browse Workspace...");
		browseModelFile.addListener(SWT.Selection, new BrowseWorkspaceForModelsListener(fileText, "XML Documents in the workspace", "Select an XML document"));

		uriTextLabel = new Label(groupContent, SWT.NONE);
		uriTextLabel.setText("URI: ");
		
		uriText = new Text(groupContent, SWT.BORDER);
		GridData uriTextGridData = new GridData(GridData.FILL_HORIZONTAL);
		uriTextGridData.horizontalSpan = 2;
		uriText.setLayoutData(uriTextGridData);
		
		groupContent.layout();
		groupContent.pack();
	}
	
	private void toggleEnabledFields() {
		if (filebasedButton.getSelection()) {
			fileTextLabel.setEnabled(true);
			fileText.setEnabled(true);
			uriTextLabel.setEnabled(false);
			uriText.setEnabled(false);
			uriText.setText("");
		}
		else {
			fileTextLabel.setEnabled(false);
			fileText.setEnabled(false);
			uriTextLabel.setEnabled(true);
			uriText.setEnabled(true);
			fileText.setText("");
			storeOnDisposalCheckbox.setSelection(false);
		}
	}
	
	@Override
	protected void storeProperties() {
		super.storeProperties();
		System.out.println("hi there");
		properties.setProperty(WebCheckerModel.PROPERTY_FILE, fileText.getText());
		properties.setProperty(WebCheckerModel.PROPERTY_URI, uriText.getText());
	}
	
}

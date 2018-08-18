/*******************************************************************************
 * Almost all the code here is taken from PlainXMLModel EMC driver by Dimitrios Kolovos
 ******************************************************************************/
package org.eclipse.epsilon.webchecker.dt;


import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractCachedModelConfigurationDialog;
import org.eclipse.epsilon.webchecker.WebCheckerModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class WebCheckerModelConfigurationDialog extends AbstractCachedModelConfigurationDialog {

	protected Label fileTextLabel;
	protected Label uriTextLabel;
	protected Label urlTimeoutLabel;
	
	protected Text fileText;
	protected Text uriText;
	protected Text urlTimeoutText;
	
	protected Button browseModelFile;
	protected Button filebasedButton;
	
	private Color bgColorGray = new Color(null, 241, 241, 241);
	private Color bgColorWhite = new Color(null, 255, 255, 255);
	
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
		
		//FILE
		fileTextLabel = new Label(groupContent, SWT.NONE);
		fileTextLabel.setText("File: ");
		
		fileText = new Text(groupContent, SWT.BORDER);
		fileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fileText.setBackground(bgColorWhite);
		fileText.setEnabled(true);
		
		browseModelFile = new Button(groupContent, SWT.NONE);
		browseModelFile.setText("Browse Workspace...");
		browseModelFile.addListener(SWT.Selection, new BrowseWorkspaceForModelsListener(fileText, "XML Documents in the workspace", "Select an XML document"));

		//URI
		uriTextLabel = new Label(groupContent, SWT.NONE);
		uriTextLabel.setText("URI: ");
		
		GridData uriTextGridData = new GridData(GridData.FILL_HORIZONTAL);
		uriTextGridData.horizontalSpan = 2;

		uriText = new Text(groupContent, SWT.BORDER);
		uriText.setLayoutData(uriTextGridData);
		uriText.setBackground(bgColorGray);
		uriText.setEnabled(false);
		
		//URL
		urlTimeoutLabel = new Label(groupContent, SWT.NONE);
		urlTimeoutLabel.setText("URL Timeout in Min: ");
		
		urlTimeoutText = new Text(groupContent, SWT.BORDER);
		urlTimeoutText.setLayoutData(uriTextGridData);
		urlTimeoutText.setText("1");
		urlTimeoutText.setBackground(bgColorGray);
		urlTimeoutText.setEnabled(false);
		
		groupContent.layout();
		groupContent.pack();
	}
	
	private void toggleEnabledFields() {

		
		if (filebasedButton.getSelection()) {
			fileText.setEnabled(true);
			fileText.setBackground(bgColorWhite);

			browseModelFile.setEnabled(true);
			
			uriText.setEnabled(false);
			uriText.setText("");
			uriText.setBackground(bgColorGray);
			
			urlTimeoutText.setEnabled(false);
			urlTimeoutText.setBackground(bgColorGray);			
		}
		else {
			fileText.setEnabled(false);
			fileText.setText("");
			fileText.setBackground(bgColorGray);
			
			browseModelFile.setEnabled(false);
			
			uriText.setEnabled(true);
			uriText.setBackground(bgColorWhite);

			urlTimeoutText.setEnabled(true);
			urlTimeoutText.setBackground(bgColorWhite);	
			
			storeOnDisposalCheckbox.setSelection(false);
		}
	}
	
	@Override
	protected void storeProperties() {
		super.storeProperties();
		System.out.println("hi there");
		properties.setProperty(WebCheckerModel.PROPERTY_FILE, fileText.getText());
		properties.setProperty(WebCheckerModel.PROPERTY_URI, uriText.getText());
		properties.setProperty(WebCheckerModel.Property_URL_Timeout, urlTimeoutText.getText());
	}
	
}

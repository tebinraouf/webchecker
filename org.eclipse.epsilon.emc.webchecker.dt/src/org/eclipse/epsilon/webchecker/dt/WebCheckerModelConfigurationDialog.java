package org.eclipse.epsilon.webchecker.dt;


import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractCachedModelConfigurationDialog;
import org.eclipse.epsilon.webchecker.WebCheckerModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class WebCheckerModelConfigurationDialog extends AbstractCachedModelConfigurationDialog {

	private Text fileText;
	private Text idFieldText;
	private Button knownHeadersBtn;
	private Button varargsHeadersBtn;
	
	
	protected String getModelName() {
		return "Web Checker HTML";
	}

	
	protected String getModelType() {
		return "WebChecker";
	}
	
	@Override
	protected void createGroups(Composite control) {
		// TODO Auto-generated method stub
		super.createGroups(control);
		createFileGroup(control);
	}
	private void createFileGroup(Composite parent) {
		final Composite groupContent = createGroupContainer(parent, "Files", 3);
		final Label modelFileLabel = new Label(groupContent, SWT.NONE);
		modelFileLabel.setText("Model file: ");
		
		fileText = new Text(groupContent, SWT.BORDER);
		fileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Button browseFile = new Button(groupContent, SWT.NONE);
		browseFile.setText("Browse Workspace...");
		browseFile.addListener(SWT.Selection, new BrowseWorkspaceForModelsListener(fileText, "HTML files in the workspace", "Select an HTML file"));
	}
	
	@Override
	protected void storeProperties() {
		// TODO Auto-generated method stub
		super.storeProperties();
		System.out.println("hi there");
		properties.setProperty(WebCheckerModel.PROPERTY_FILE, fileText.getText());
//		properties.setProperty(CsvModel.PROPERTY_FIELD_SEPARATOR, fieldSeparatorText.getText());
//		properties.setProperty(CsvModel.PROPERTY_QUOTE_CHARACTER, quoteCharacterText.getText());
		
//		System.out.println("The value is: "+ idFieldText);
//		String idFieldValue = idFieldText.getText();
//		
//		if(idFieldValue.isEmpty()) {
//			properties.remove(WebCheckerModel.PROPERTY_ID_FIELD);
//		}
//		else {
//			properties.setProperty(WebCheckerModel.PROPERTY_ID_FIELD, idFieldValue);
//		}
//		properties.setProperty(WebCheckerModel.PROPERTY_HAS_KNOWN_HEADERS, String.valueOf(knownHeadersBtn.getSelection()));
//		properties.setProperty(WebCheckerModel.PROPERTY_HAS_VARARGS_HEADERS, String.valueOf(varargsHeadersBtn.getSelection()));
	}
	
}

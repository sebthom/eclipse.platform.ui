package org.eclipse.ui.tests.dialogs;

import junit.framework.TestCase;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectReferencePage;
import org.eclipse.ui.help.WorkbenchHelp;
import org.eclipse.ui.internal.IHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.ExportWizard;
import org.eclipse.ui.internal.dialogs.ImportWizard;
import org.eclipse.ui.internal.dialogs.NewWizard;
import org.eclipse.ui.test.harness.DialogCheck;
import org.eclipse.ui.wizards.datatransfer.DataTransferTestStub;
import org.eclipse.ui.wizards.newresource.BasicNewFileResourceWizard;
import org.eclipse.ui.wizards.newresource.BasicNewFolderResourceWizard;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

public class UIWizardsAuto extends TestCase {
	private static final int SIZING_WIZARD_WIDTH    = 470;
	private static final int SIZING_WIZARD_HEIGHT   = 550;
	private static final int SIZING_WIZARD_WIDTH_2  = 500;
	private static final int SIZING_WIZARD_HEIGHT_2 = 500;
	
	public UIWizardsAuto(String name) {
		super(name);
	}
	private Shell getShell() {
		return DialogCheck.getShell();
	}
	private IWorkbench getWorkbench() {
		return WorkbenchPlugin.getDefault().getWorkbench();
	}
	
	private WizardDialog exportWizard(IWizardPage page) {
		ExportWizard wizard = new ExportWizard();
		wizard.init(getWorkbench(), null);
		IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings wizardSettings = workbenchSettings.getSection("ExportResourcesAction");
		if(wizardSettings == null)
			wizardSettings = workbenchSettings.addNewSection("ExportResourcesAction");
		wizard.setDialogSettings(wizardSettings);
		wizard.setForcePreviousAndNextButtons(true);
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize( Math.max(SIZING_WIZARD_WIDTH, dialog.getShell().getSize().x), SIZING_WIZARD_HEIGHT );
		WorkbenchHelp.setHelp(dialog.getShell(), new Object[]{IHelpContextIds.EXPORT_WIZARD});

		if (page != null) {
			page.setWizard(wizard);
			dialog.showPage(page);
		}
		return dialog;
	}
	private WizardDialog importWizard(IWizardPage page) {
		ImportWizard wizard = new ImportWizard();
		wizard.init(getWorkbench(), null);
		IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings wizardSettings = workbenchSettings.getSection("ImportResourcesAction");
		if(wizardSettings==null)
			wizardSettings = workbenchSettings.addNewSection("ImportResourcesAction");
		wizard.setDialogSettings(wizardSettings);
		wizard.setForcePreviousAndNextButtons(true);
		
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize( Math.max(SIZING_WIZARD_WIDTH, dialog.getShell().getSize().x), SIZING_WIZARD_HEIGHT );
		WorkbenchHelp.setHelp(dialog.getShell(), new Object[]{IHelpContextIds.IMPORT_WIZARD});
		
		if (page != null) {
			page.setWizard(wizard);
			dialog.showPage(page);
		}
		return dialog;
	}

	public void testExportResources() {//reference: ExportResourcesAction
		Dialog dialog = exportWizard(null);
		DialogCheck.assertDialogTexts(dialog, this);
	}
	public void testFileSystemExport() {
		Dialog dialog = exportWizard( DataTransferTestStub.newFileSystemResourceExportPage1(null) );
		DialogCheck.assertDialogTexts(dialog, this);
	}
	public void testZipFileExport() {
		Dialog dialog = exportWizard( DataTransferTestStub.newZipFileResourceExportPage1(null) );
		DialogCheck.assertDialogTexts(dialog, this);
	}
	public void testImportResources() {//reference: ImportResourcesAction
		Dialog dialog = importWizard(null);
		DialogCheck.assertDialogTexts(dialog, this);
	}
	public void testFileSystemImport() {
		Dialog dialog = importWizard( DataTransferTestStub.newFileSystemResourceImportPage1(WorkbenchPlugin.getDefault().getWorkbench(), StructuredSelection.EMPTY) );
		DialogCheck.assertDialogTexts(dialog, this);
	}
	public void testZipFileImport() {
		Dialog dialog = importWizard( DataTransferTestStub.newZipFileResourceImportPage1(null) );
		DialogCheck.assertDialogTexts(dialog, this);
	}
	public void testNewFile() {
		BasicNewFileResourceWizard wizard = new BasicNewFileResourceWizard();
		wizard.init( PlatformUI.getWorkbench(), new StructuredSelection() );
		wizard.setNeedsProgressMonitor(true);
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setText(WorkbenchMessages.getString("CreateFileAction.title")); //$NON-NLS-1$
		WorkbenchHelp.setHelp(dialog.getShell(), new Object[]{IHelpContextIds.NEW_FILE_WIZARD});
		DialogCheck.assertDialogTexts(dialog, this);
	}
	public void testNewFolder() {
		BasicNewFolderResourceWizard wizard = new BasicNewFolderResourceWizard();
		wizard.init( PlatformUI.getWorkbench(), new StructuredSelection() );
		wizard.setNeedsProgressMonitor(true);
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setText(WorkbenchMessages.getString("CreateFolderAction.title")); //$NON-NLS-1$
		WorkbenchHelp.setHelp(dialog.getShell(), new Object[]{IHelpContextIds.NEW_FOLDER_WIZARD});
		DialogCheck.assertDialogTexts(dialog, this);
	}
	public void testNewProjectPage1() {
		BasicNewProjectResourceWizard wizard = new BasicNewProjectResourceWizard();
		wizard.init(PlatformUI.getWorkbench(), null);
		wizard.setNeedsProgressMonitor(true);
		
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize( Math.max(SIZING_WIZARD_WIDTH_2, dialog.getShell().getSize().x), SIZING_WIZARD_HEIGHT_2 );
		dialog.getShell().setText(WorkbenchMessages.getString("CreateFileAction.title")); //$NON-NLS-1$
		WorkbenchHelp.setHelp(dialog.getShell(), new Object[]{IHelpContextIds.NEW_PROJECT_WIZARD});
		DialogCheck.assertDialogTexts(dialog, this);
	}
	public void testNewProjectPage2() {
		BasicNewProjectResourceWizard wizard = new BasicNewProjectResourceWizard();
		wizard.init(PlatformUI.getWorkbench(), null);
		wizard.setNeedsProgressMonitor(true);
		
		WizardNewProjectReferencePage page = new WizardNewProjectReferencePage("basicReferenceProjectPage");//$NON-NLS-1$
		page.setTitle(ResourceMessagesCopy.getString("NewProject.refeerenceTitle")); //$NON-NLS-1$
		page.setDescription(ResourceMessagesCopy.getString("NewProject.referenceDescription")); //$NON-NLS-1$				
		page.setWizard(wizard);
		
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize( Math.max(SIZING_WIZARD_WIDTH_2, dialog.getShell().getSize().x), SIZING_WIZARD_HEIGHT_2 );
		dialog.getShell().setText(WorkbenchMessages.getString("CreateFileAction.title")); //$NON-NLS-1$			
		dialog.showPage(page);
		WorkbenchHelp.setHelp(dialog.getShell(), new Object[]{IHelpContextIds.NEW_PROJECT_WIZARD});
		DialogCheck.assertDialogTexts(dialog, this);
	}
	public void testNewProject() {
		// Create wizard selection wizard.
		NewWizard wizard = new NewWizard();
		wizard.setProjectsOnly(true);
		ISelection selection = getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		IStructuredSelection selectionToPass = null;
		if (selection instanceof IStructuredSelection)
			selectionToPass = (IStructuredSelection) selection;
		else
			selectionToPass = StructuredSelection.EMPTY;
		wizard.init(getWorkbench(), selectionToPass);
		IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings wizardSettings = workbenchSettings.getSection("NewWizardAction");//$NON-NLS-1$
		if(wizardSettings==null)
			wizardSettings = workbenchSettings.addNewSection("NewWizardAction");//$NON-NLS-1$
		wizard.setDialogSettings(wizardSettings);
		wizard.setForcePreviousAndNextButtons(true);
	
		// Create wizard dialog.
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize( Math.max(SIZING_WIZARD_WIDTH_2, dialog.getShell().getSize().x), SIZING_WIZARD_HEIGHT_2 );
		WorkbenchHelp.setHelp(dialog.getShell(), new Object[]{IHelpContextIds.NEW_PROJECT_WIZARD});	
		DialogCheck.assertDialogTexts(dialog, this);
	}
	public void testNewResource() {
		NewWizard wizard = new NewWizard();
		ISelection selection = getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		IStructuredSelection selectionToPass = null;
		if (selection instanceof IStructuredSelection)
			selectionToPass = (IStructuredSelection) selection;
		else
			selectionToPass = StructuredSelection.EMPTY;
		wizard.init(getWorkbench(), selectionToPass);
		IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings wizardSettings = workbenchSettings.getSection("NewWizardAction");//$NON-NLS-1$
		if(wizardSettings==null)
			wizardSettings = workbenchSettings.addNewSection("NewWizardAction");//$NON-NLS-1$
		wizard.setDialogSettings(wizardSettings);
		wizard.setForcePreviousAndNextButtons(true);
		
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize( Math.max(SIZING_WIZARD_WIDTH_2, dialog.getShell().getSize().x), SIZING_WIZARD_HEIGHT_2 );
		WorkbenchHelp.setHelp(dialog.getShell(), new Object[]{IHelpContextIds.NEW_WIZARD});
		DialogCheck.assertDialogTexts(dialog, this);
	}

}


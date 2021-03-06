package de.saxsys.mvvmfx.examples.contacts.ui.editcontact;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.examples.contacts.events.OpenEditContactDialogEvent;
import de.saxsys.mvvmfx.examples.contacts.ui.contactdialog.ContactDialogView;
import de.saxsys.mvvmfx.examples.contacts.util.DialogHelper;

@Singleton
public class EditContactDialog implements FxmlView<EditContactDialogViewModel> {
	
	@FXML
	private ContactDialogView contactDialogViewController;
	
	
	private Parent root;
	
	@Inject
	private Stage primaryStage;
	
	@InjectViewModel
	private EditContactDialogViewModel viewModel;
	
	@Inject
	EditContactDialog() {
		root = FluentViewLoader
				.fxmlView(EditContactDialog.class)
				.codeBehind(this)
				.load()
				.getView();
	}
	
	public void initialize() {
		viewModel.setContactDialogViewModel(contactDialogViewController.getViewModel());
		
		DialogHelper.initDialog(viewModel.dialogOpenProperty(), primaryStage, () -> root);
	}
	
	public void open(@Observes OpenEditContactDialogEvent event) {
		viewModel.openDialog(event.getContactId());
	}
}

package de.saxsys.mvvmfx.examples.contacts.ui.contactdialog;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ObservableBooleanValue;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.contacts.ui.addressform.AddressFormViewModel;
import de.saxsys.mvvmfx.examples.contacts.ui.contactform.ContactFormViewModel;

public class ContactDialogViewModel implements ViewModel {
	
	private IntegerProperty dialogPage = new SimpleIntegerProperty(0);
	
	private ObjectProperty<ContactFormViewModel> contactFormViewModel = new SimpleObjectProperty<>();
	private ObjectProperty<AddressFormViewModel> addressFormViewModel = new SimpleObjectProperty<>();
	
	private ReadOnlyBooleanWrapper valid = new ReadOnlyBooleanWrapper();
	
	private StringProperty titleText = new SimpleStringProperty();
	
	private Runnable okAction;
	
	// we need this field to prevent the binding from beeing garbage collected
	private BooleanBinding viewModelsInitialized;
	
	public ContactDialogViewModel() {
		viewModelsInitialized = contactFormViewModel.isNotNull().and(addressFormViewModel.isNotNull());
		
		// as soon as both viewModels are set we add a binding that is true only when both viewModels are valid.
		viewModelsInitialized.addListener((obs, oldV, newV) -> {
			if (newV) {
				valid.bind(Bindings.and(contactFormViewModel.get().validProperty(), addressFormViewModel.get()
						.validProperty()));
			} else {
				valid.unbind();
			}
		});
	}
	
	public void okAction() {
		if (okAction != null) {
			okAction.run();
		}
	}
	
	public void setOkAction(Runnable okAction) {
		this.okAction = okAction;
	}
	
	public void previousAction() {
		if (dialogPage.get() == 1) {
			dialogPage.set(0);
		}
	}
	
	public void nextAction() {
		if (dialogPage.get() == 0) {
			dialogPage.set(1);
		}
	}
	
	public void resetDialogPage() {
		dialogPage.set(0);
	}
	
	public void resetForms() {
		contactFormViewModel.get().resetForm();
		addressFormViewModel.get().resetForm();
	}
	
	public void setContactFormViewModel(ContactFormViewModel contactFormViewModel) {
		this.contactFormViewModel.set(contactFormViewModel);
	}
	
	public ContactFormViewModel getContactFormViewModel() {
		return contactFormViewModel.get();
	}
	
	public void setAddressFormViewModel(AddressFormViewModel addressFormViewModel) {
		this.addressFormViewModel.set(addressFormViewModel);
	}
	
	public AddressFormViewModel getAddressFormViewModel() {
		return addressFormViewModel.get();
	}
	
	public IntegerProperty dialogPageProperty() {
		return dialogPage;
	}
	
	
	public ObservableBooleanValue okButtonDisabledProperty() {
		return Bindings.and(contactFormViewModel.get().validProperty(), addressFormViewModel.get().validProperty())
				.not();
	}
	
	public ObservableBooleanValue okButtonVisibleProperty() {
		return dialogPage.isEqualTo(1);
	}
	
	public ObservableBooleanValue nextButtonDisabledProperty() {
		return contactFormViewModel.get().validProperty().not();
	}
	
	public ObservableBooleanValue nextButtonVisibleProperty() {
		return dialogPage.isEqualTo(0);
	}
	
	public ObservableBooleanValue previousButtonVisibleProperty() {
		return dialogPage.isEqualTo(1);
	}
	
	public ObservableBooleanValue previousButtonDisabledProperty() {
		return addressFormViewModel.get().validProperty().not();
	}
	
	public ReadOnlyBooleanProperty validProperty() {
		return valid;
	}
	
	public StringProperty titleTextProperty() {
		return titleText;
	}
}

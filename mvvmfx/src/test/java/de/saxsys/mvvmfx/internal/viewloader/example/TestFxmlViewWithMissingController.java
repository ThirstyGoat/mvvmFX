package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestFxmlViewWithMissingController implements FxmlView<TestViewModel> {
	
	@InjectViewModel
	public TestViewModel viewModel;
}

package de.saxsys.mvvmfx.internal.viewloader;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.example.TestViewModel;

public class ViewLoaderReflectionUtilsTest {
	
	
	@Test
	public void testCreateViewModel() {
		class TestView implements View<TestViewModel> {
		}
		
		ViewModel viewModel = ViewLoaderReflectionUtils.createViewModel(new TestView());
		
		assertThat(viewModel).isNotNull().isInstanceOf(TestViewModel.class);
	}
	
	@Test
	public void testCreateViewModelWithoutViewModelType() {
		class TestView implements View {
		}
		
		ViewModel viewModel = ViewLoaderReflectionUtils.createViewModel(new TestView());
		
		assertThat(viewModel).isNull();
	}
	
	@Test
	public void testCreateViewModelWithGeneticViewModelAsType() {
		class TestView implements View<ViewModel> {
		}
		
		ViewModel viewModel = ViewLoaderReflectionUtils.createViewModel(new TestView());
		
		assertThat(viewModel).isNull();
	}
}

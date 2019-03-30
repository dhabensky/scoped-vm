package com.dhabensky.scopedvm.subscription;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelHack;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * Created on 29.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class Subscriptions {

	public static @NonNull OwnerSubscriptions owner(@NonNull ViewModelStoreOwner storeOwner) {
		return getOrNewInstance(
				OwnerSubscriptions.class,
				storeOwner.getViewModelStore()
		);
	}

	public static @NonNull UserSubscriptions user(@NonNull ViewModelStoreOwner storeOwner) {
		return Subscriptions.getOrNewInstance(
				UserSubscriptions.class,
				storeOwner.getViewModelStore()
		);
	}

	private static <T extends ViewModel> T getOrNewInstance(@NonNull Class<T> modelClass,
	                                                        @NonNull ViewModelStore store) {
		return getOrNewInstance(ViewModelHack.defaultKey(modelClass), modelClass, store);
	}

	@SuppressWarnings("unchecked")
	private static <T extends ViewModel> T getOrNewInstance(@NonNull String key,
	                                                        @NonNull Class<T> modelClass,
	                                                        @NonNull ViewModelStore store) {

		ViewModel viewModel = ViewModelHack.getViewModel(key, store);

		if (modelClass.isInstance(viewModel)) {
			return (T) viewModel;
		}
		else {
			if (viewModel != null) {
				// TODO: log a warning.
			}
		}

		try {
			viewModel = modelClass.newInstance();
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException("Cannot create an instance of " + modelClass, e);
		}
		catch (InstantiationException e) {
			throw new RuntimeException("Cannot create an instance of " + modelClass, e);
		}

		ViewModelHack.putViewModel(key, viewModel, store);

		return (T) viewModel;
	}

}

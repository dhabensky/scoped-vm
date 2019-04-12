package com.dhabensky.scopedvm.subscription;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelHack;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * Created on 29.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class Subscriptions {

	public static @NonNull ViewModelStore getScopedStore(@NonNull ViewModelStoreOwner hostOwner,
	                                                     @Nullable String scope,
	                                                     @NonNull ViewModelStoreOwner clientOwner) {
		if (scope != null) {
			SubscriptionHost host = Subscriptions.host(hostOwner);
			SubscriptionClient client = Subscriptions.client(clientOwner);

			Subscription sub = new Subscription(host, scope, client);
			if (!client.hasSubscription(sub)) {
				host.addSubscription(sub);
				client.addSubscription(sub);
			}

			return host.getScopedStore(scope);
		}
		else {
			return hostOwner.getViewModelStore();
		}
	}

	public static @NonNull SubscriptionHost host(@NonNull ViewModelStoreOwner hostOwner) {
		return getOrNewInstance(
				SubscriptionHost.class,
				hostOwner.getViewModelStore()
		);
	}

	public static @NonNull SubscriptionClient client(@NonNull ViewModelStoreOwner clientOwner) {
		return getOrNewInstance(
				SubscriptionClient.class,
				clientOwner.getViewModelStore()
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

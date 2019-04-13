package com.dhabensky.scopedvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
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
		return getViewModel(hostOwner.getViewModelStore(), SubscriptionHost.class);
	}

	public static @NonNull SubscriptionClient client(@NonNull ViewModelStoreOwner clientOwner) {
		return getViewModel(clientOwner.getViewModelStore(), SubscriptionClient.class);
	}

	@NonNull
	private static <T extends ViewModel> T getViewModel(@NonNull ViewModelStore store,
	                                                    @NonNull Class<T> modelClass) {

		ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
		ViewModelProvider provider = new ViewModelProvider(store, factory);
		return provider.get(modelClass);
	}

}

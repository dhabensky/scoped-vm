package com.dhabensky.scopedvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
		return ViewModelHack.getOrNewInstance(
				SubscriptionHost.class,
				hostOwner.getViewModelStore()
		);
	}

	public static @NonNull SubscriptionClient client(@NonNull ViewModelStoreOwner clientOwner) {
		return ViewModelHack.getOrNewInstance(
				SubscriptionClient.class,
				clientOwner.getViewModelStore()
		);
	}

}

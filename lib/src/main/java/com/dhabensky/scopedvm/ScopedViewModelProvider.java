package com.dhabensky.scopedvm;

import com.dhabensky.scopedvm.subscription.OwnerSubscriptions;
import com.dhabensky.scopedvm.subscription.Subscription;
import com.dhabensky.scopedvm.subscription.Subscriptions;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelHack;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * Created on 30.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class ScopedViewModelProvider {

	private final ViewModelStoreOwner storeOwner;
	private final ViewModelProvider.Factory factory;
	private final String scope;
	private final Fragment requester;
	private ViewModelProvider provider;

	public ScopedViewModelProvider(@NonNull ViewModelStoreOwner storeOwner,
	                               @NonNull ViewModelProvider.Factory factory,
	                               @Nullable String scope,
	                               @NonNull Fragment requester) {
		this.storeOwner = storeOwner;
		this.factory = factory;
		this.scope = scope;
		this.requester = requester;
	}


	@MainThread
	public <T extends ViewModel> T get(@NonNull Class<T> modelClass) {
		return get(ViewModelHack.defaultKey(modelClass), modelClass);
	}

	@MainThread
	public <T extends ViewModel> T get(@NonNull String key, @NonNull Class<T> modelClass) {
		return getProvider().get(key, modelClass);
	}


	private ViewModelStore getScopedStore() {
		if (scope != null) {
			OwnerSubscriptions owner = Subscriptions.owner(storeOwner);
			// subscriptions
			Subscription subscription = new Subscription(owner, scope);
			owner.addSubscription(subscription);
			Subscriptions.user(requester).addSubscription(subscription);

			return owner.getScopedStore(scope);
		}
		else {
			return storeOwner.getViewModelStore();
		}
	}

	private ViewModelProvider getProvider() {
		if (provider == null) {
			provider = new ViewModelProvider(getScopedStore(), factory);
		}
		return provider;
	}

}

package com.dhabensky.scopedvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * Created on 30.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class ScopedViewModelProviders {

	private final @NonNull ViewModelStoreOwner client;
	private final @Nullable String scope;

	private ScopedViewModelProviders(@NonNull ViewModelStoreOwner client, @Nullable String scope) {
		this.client = client;
		this.scope = scope;
	}

	public static ScopedViewModelProviders forScope(@NonNull Fragment fragment,
	                                                @Nullable String scope) {

		if (fragment.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
			throw new IllegalArgumentException(
					"Cannot create ScopedViewModelProvider for destroyed client");
		}
		return new ScopedViewModelProviders(fragment, scope);
	}


	public ViewModelProvider of(@NonNull FragmentActivity activity) {
		Factory factory = AndroidViewModelFactory.getInstance(activity.getApplication());
		return of(activity, factory);
	}

	public ViewModelProvider of(@NonNull FragmentActivity activity, @NonNull Factory factory) {
		return ofStore(activity, factory);
	}

	public ViewModelProvider of(@NonNull Fragment fragment) {
		Factory factory = AndroidViewModelFactory
				.getInstance(fragment.getActivity().getApplication());
		return of(fragment, factory);
	}

	public ViewModelProvider of(@NonNull Fragment fragment, @NonNull Factory factory) {
		return ofStore(fragment, factory);
	}

	private ViewModelProvider ofStore(@NonNull ViewModelStoreOwner host, @NonNull Factory factory) {
		ViewModelStore store = Subscriptions.getScopedStore(host, scope, client);
		return new ViewModelProvider(store, factory);
	}

}

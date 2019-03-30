package com.dhabensky.scopedvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory;

/**
 * Created on 30.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class ScopedViewModelProviders {

	private final @NonNull Fragment requester;
	private final @Nullable String scope;

	private ScopedViewModelProviders(@NonNull Fragment requester, @Nullable String scope) {
		this.requester = requester;
		this.scope = scope;
	}

	public static ScopedViewModelProviders forScope(@NonNull Fragment requester,
	                                                @Nullable String scope) {

		if (requester.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
			throw new IllegalArgumentException(
					"Cannot create ScopedViewModelProvider for destroyed requester");
		}
		return new ScopedViewModelProviders(requester, scope);
	}


	public ScopedViewModelProvider of(@NonNull FragmentActivity activity,
	                                  @NonNull Factory factory) {

		return new ScopedViewModelProvider(activity, factory, scope, requester);
	}

	public ScopedViewModelProvider of(@NonNull FragmentActivity activity) {
		Factory factory = AndroidViewModelFactory
				.getInstance(activity.getApplication());
		return new ScopedViewModelProvider(activity, factory, scope, requester);
	}

	public ScopedViewModelProvider of(@NonNull Fragment fragment,
	                                  @NonNull Factory factory) {
		return new ScopedViewModelProvider(fragment, factory, scope, requester);
	}

	public ScopedViewModelProvider of(@NonNull Fragment fragment) {
		Factory factory = AndroidViewModelFactory
				.getInstance(fragment.getActivity().getApplication());
		return new ScopedViewModelProvider(fragment, factory, scope, requester);
	}

}

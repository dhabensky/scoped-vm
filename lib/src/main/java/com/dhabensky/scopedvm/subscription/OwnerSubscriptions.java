package com.dhabensky.scopedvm.subscription;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStore;

/**
 * Created on 30.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class OwnerSubscriptions extends ViewModel {

	private static class Scope {
		Set<Subscription> subscriptions = new LinkedHashSet<>();
		ViewModelStore store = new ViewModelStore();
	}

	private Map<String, Scope> scopeMap = new HashMap<>();


	public @NonNull
	ViewModelStore getScopedStore(@NonNull String scopeName) {
		return getOrCreateScope(scopeName).store;
	}

	public void addSubscription(@NonNull Subscription subscription) {
		Scope scope = getOrCreateScope(subscription.scope);
		scope.subscriptions.add(subscription);
	}

	public void removeSubscription(@NonNull Subscription subscription) {
		String name = subscription.scope;
		Scope scope = scopeMap.get(name);
		if (scope != null) {
			scope.subscriptions.remove(subscription);
			if (scope.subscriptions.isEmpty()) {
				scope.store.clear();
				scopeMap.remove(name);
			}
		}
	}

	private @NonNull Scope getOrCreateScope(@NonNull String scopeName) {
		Scope scope = scopeMap.get(scopeName);
		if (scope == null) {
			scope = new Scope();
			scopeMap.put(scopeName, scope);
		}
		return scope;
	}

	@Override
	protected void onCleared() {
		super.onCleared();
		for (Scope scope : scopeMap.values()) {
			scope.store.clear();
		}
		scopeMap.clear();
	}

}

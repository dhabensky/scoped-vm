package com.dhabensky.scopedvm;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStore;

/**
 * Created on 30.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class SubscriptionHost extends ViewModel {

	private static class Scope {
		Set<Subscription> subscriptions = new LinkedHashSet<>();
		ViewModelStore store = new ViewModelStore();
	}

	private Map<String, Scope> scopeMap = new HashMap<>();


	public @NonNull ViewModelStore getScopedStore(@NonNull String scopeName) {
		return getOrCreateScope(scopeName).store;
	}

	public void addSubscription(@NonNull Subscription subscription) {
		if (subscription.host != this) {
			throw new IllegalArgumentException("Cannot add subscription from another client");
		}
		Scope scope = getOrCreateScope(subscription.scope);
		scope.subscriptions.add(subscription);
	}

	public void removeSubscription(@NonNull Subscription subscription) {
		String name = subscription.scope;
		Scope scope = scopeMap.get(name);
		if (scope != null) {
			boolean removed = scope.subscriptions.remove(subscription);
			if (!removed) {
				Log.w("SCOPED-VM",
						"subscription " + subscription + " was not found in " + this);
			}
			if (scope.subscriptions.isEmpty()) {
				scope.store.clear();
				scopeMap.remove(name);
			}
		}
	}

	public List<Subscription> getSubscriptions() {
		List<Subscription> res = new ArrayList<>();
		for (Scope scope : scopeMap.values()) {
			res.addAll(scope.subscriptions);
		}
		return res;
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

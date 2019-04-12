package com.dhabensky.scopedvm.subscription;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

/**
 * Created on 30.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class SubscriptionClient extends ViewModel {

	private List<Subscription> subscriptions = new LinkedList<>();

	public boolean hasSubscription(@NonNull Subscription subscription) {
		if (subscription.client != this) {
			return false;
		}
		for (Subscription sub : subscriptions) {
			if (sub.host == subscription.host && sub.scope.equals(subscription.scope)) {
				return true;
			}
		}
		return false;
	}

	public void addSubscription(@NonNull Subscription subscription) {
		if (subscription.client != this) {
			throw new IllegalArgumentException("Cannot add subscription from another client");
		}
		subscriptions.add(subscription);
	}

	@Override
	protected void onCleared() {
		super.onCleared();
		for (Subscription sub : subscriptions) {
			sub.host.removeSubscription(sub);
		}
	}

}

package com.dhabensky.scopedvm.subscription;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

/**
 * Created on 30.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class UserSubscriptions extends ViewModel {

	private List<Subscription> subscriptions = new LinkedList<>();

	public void addSubscription(@NonNull Subscription subscription) {
		subscriptions.add(subscription);
	}

	@Override
	protected void onCleared() {
		super.onCleared();
		for (Subscription sub : subscriptions) {
			sub.ownerSubscriptions.removeSubscription(sub);
		}
	}

}

package com.dhabensky.svm.subscription;

import androidx.annotation.NonNull;

/**
 * Created on 30.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class Subscription {

	public final OwnerSubscriptions ownerSubscriptions;
	public final String scope;

	public Subscription(@NonNull OwnerSubscriptions vmOwner, @NonNull String scope) {
		this.ownerSubscriptions = vmOwner;
		this.scope = scope;
	}

}

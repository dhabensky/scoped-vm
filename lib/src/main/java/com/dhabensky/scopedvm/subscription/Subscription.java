package com.dhabensky.scopedvm.subscription;

import androidx.annotation.NonNull;

/**
 * Created on 30.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class Subscription {

	public final @NonNull SubscriptionHost host;
	public final @NonNull String scope;
	public final @NonNull SubscriptionClient client;

	public Subscription(@NonNull SubscriptionHost host,
	                    @NonNull String scope,
	                    @NonNull SubscriptionClient client) {
		this.host = host;
		this.scope = scope;
		this.client = client;
	}

	@Override
	public String toString() {
		return "Subscription{" +
				"host=" + host +
				", scope='" + scope + '\'' +
				", client=" + client + '}';
	}

}

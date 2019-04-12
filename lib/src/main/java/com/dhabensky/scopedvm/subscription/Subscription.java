package com.dhabensky.scopedvm.subscription;

import androidx.annotation.NonNull;

/**
 * Created on 30.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class Subscription {

	public final SubscriptionHost host;
	public final String scope;
	public final SubscriptionClient client;

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

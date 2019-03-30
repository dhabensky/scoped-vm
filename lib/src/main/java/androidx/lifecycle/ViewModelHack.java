package androidx.lifecycle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created on 29.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class ViewModelHack {

	public static void putViewModel(@NonNull String key,
	                                @NonNull ViewModel viewModel,
	                                @NonNull ViewModelStore store) {
		store.put(key, viewModel);
	}

	public static @Nullable ViewModel getViewModel(@NonNull String key,
	                                               @NonNull ViewModelStore store) {
		return store.get(key);
	}


	private static final String DEFAULT_KEY = "androidx.lifecycle.ViewModelProvider.DefaultKey";

	public static @NonNull String defaultKey(Class<?> modelClass) {
		String canonicalName = modelClass.getCanonicalName();
		if (canonicalName == null) {
			throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
		}
		return DEFAULT_KEY + ":" + canonicalName;
	}

}

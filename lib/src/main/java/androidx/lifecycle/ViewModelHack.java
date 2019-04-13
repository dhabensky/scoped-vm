package androidx.lifecycle;

import androidx.annotation.NonNull;

/**
 * Created on 29.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class ViewModelHack {

	private static final String DEFAULT_KEY = "androidx.lifecycle.ViewModelProvider.DefaultKey";

	private static @NonNull String defaultKey(Class<?> modelClass) {
		String canonicalName = modelClass.getCanonicalName();
		if (canonicalName == null) {
			throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
		}
		return DEFAULT_KEY + ":" + canonicalName;
	}

	/**
	 * Just a way to get {@link ViewModel} without creating {@link ViewModelProvider}
	 * and {@link ViewModelProvider.Factory}.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends ViewModel> T getOrNewInstance(@NonNull Class<T> modelClass,
	                                                       @NonNull ViewModelStore store) {
		String key = defaultKey(modelClass);
		ViewModel viewModel = store.get(key);

		if (modelClass.isInstance(viewModel)) {
			return (T) viewModel;
		}

		try {
			viewModel = modelClass.newInstance();
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException("Cannot create an instance of " + modelClass, e);
		}
		catch (InstantiationException e) {
			throw new RuntimeException("Cannot create an instance of " + modelClass, e);
		}

		store.put(key, viewModel);

		return (T) viewModel;
	}

}

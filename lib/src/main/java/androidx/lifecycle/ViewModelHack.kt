package androidx.lifecycle

/**
 * Created on 16.03.2019.
 * @author dhabensky <dhabensky@yandex.ru>
 */

fun clearViewModel(viewModel: ViewModel?) {
    viewModel?.onCleared()
}

fun putViewModel(key: String, viewModel: ViewModel, store: ViewModelStore) {
    store.put(key, viewModel)
}

fun getViewModel(key: String, store: ViewModelStore): ViewModel? {
    return store.get(key)
}

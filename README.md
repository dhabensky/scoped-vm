[![](https://jitpack.io/v/dhabensky/scoped-vm.svg)](https://jitpack.io/#dhabensky/scoped-vm)
[![Build Status](https://travis-ci.com/dhabensky/scoped-vm.svg?branch=master)](https://travis-ci.com/dhabensky/scoped-vm)

# scoped-vm
### What? 
#### Provide viewmodels scoped to a list of fragments instead of entire activity.

Google stands for single activity architecture.  
In case you need to share data across multiple fragments - use viewmodel scoped to an activity.  
But it is the only activity, so viewmodel will live till ethernity. Just like a global singleton object!  
  
Usually you dont need to share data across all of your screens, but only across small subflows.  
When subflow ends, the data should be freed and never reused.
So, viewmodel should be scoped to a list of fragments, which is the task this library solves!

### How?
#### Request viewmodels via ScopedViewModelProviders.

```
ScopedViewModelProviders
     .forScope(fragment, "scope")
     .of(activity)
     .get(MyViewModel::class.java)
```
So what is `ScopedViewModelProviders.forScope(fragment, "scope")`?  
`"scope"` is just a string, used to identify scope  
`fragment` is the fragment that requests viewmodel, usually you will use `this`.  

When fragment requests viewmodel a subscription is created. Subscription is released automatically when the fragment is destroyed. When the last fragment of scope is destroyed, all viewmodels for this scope get cleared.

#### Add dependencies
Add this to your root build.gradle
```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
Add this to your module build.gradle
```
dependencies {
    implementation 'com.github.dhabensky:scoped-vm:1.0'
}
```

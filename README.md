# voiqtest

This is my solution for the VOIQ test exercise. Following the instructions, here is a little explanation of what was done.

Libraries
---------
Here are some of the libraries I used, and why did I used them.

### Retrofit
Retrofit allows to create quick, simple and efficient REST clients. I created one for the API requests, using POJO's to serialize the responses. Since they were coming in JSON format, I used GSON serializer, which is the default setting for Retrofit. I would have used the same for the requests, but they were only working with URL-Encoded parameters.

Though creating the classes for the GSON serializing might seem like overkill for the exercise, I wanted to code it in a way that was scalable for the future, I like to use the separate classes to keep the requests contained in a single package and being able to call them and modify them easily, not fearing that anything else might break in the UI code. Keep in mind as well, that some of these POJO's might as well be thought as models, depending on the business logic.

### Otto
Right now, the app only works in portrait orientation, as there were no designs for landscape. However, there may be designs for it in the future, so I used an architecture that allowed the developers to add the orientation without concerns about orientation changes.

Enter Otto, an Event Bus that allows to avoid the issues with AsyncTasks and IntentServices for API calls, and other multi-thread scenarios. Using a simple, annotation-based system of subscriptions to events, Otto allows the communication between threads, without excessive boilerplate, or leakages. Otto+Retrofit power the Asynchronous requests for the app.

### Dagger
Dagger is a DI library that helps to keep everything uncoupled and helps to make the testing process a lot easier. I use it here to forget about hidden dependencies and keep the architecture clean. It also allows me to validate the UI behavior (fragments, activities, etc.) in isolation.

### Robolectric
Robolectric just makes the testing process quick, instead of the eternal pain of the regular Instrumentation Test Cases. Combining it with Hamcrest for more readable assertions, the test cases are easier to read and maintain.

### Mockito
Mockito allows to create mocks and partial mocks for testing. In this particular case, the partial mocks allow me to check calls to the Event Bus for each test case separately, and allows me to check the parameters and times a method from a mock object is called.

### ButterKnife
The usual "findviewbyid", "onclicklisteners", etc., are a real drag on code readability, lots of boilerplate for just referencing a xml layout element. Butterknife helps to keep this a lot more readable, both in implementation and test files.

General Architecture
--------------------
No real surprises here, the first screen has a fragment, just in case it is required somewhere else in the future, it validates the fields prior to sending the request. If the entered info is valid, it sends the request, keeping a progress dialog on the screen. If the request is successful, the app will show the token screen. If not, the app will show an alert with the reason for failure.

The registration screen operates in a similar fashion. However, the three proposed spinners(combo boxes) seemed a little inefficient to me. On the one hand, just loading the adapters and info for the spinners could be bad, if not fault-prone. On the other hand, having the user fill the information for three spinners is bad UX, people don´t like to be entering lots of info in a single form. I decided to use a DatePickerDialog for it, I think it looks, and works better.

The couple of UISwitches for the device type were implemented with checkboxes, being a little more faithful to the Android Design. They however do not have any functionality so far, as the API for registration seemed to hardcode the device for Android. I probably should have asked about it, but I didn't think it would be a big deal.

The registration screen validates that the two password fields have the same string. The token screen additionally has a "Log Out" button, I had already created it before I received response on it not being necessary, so I decided to keep it. It does not do much though.

Testing
-------
Besides Robolectric, I tested the app in the Android Emulator, with an Intel image for Android 4.3. Unfortunately I don't have an Android device for testing here.

Layouts/Graphics
----------------
I don't have the Adobe Suite installed, so I used some of the assets found on VOIQ's Facebook Page. However, I did not use the same colors overall and missed a couple of image assets. I didn't add any other fonts either.

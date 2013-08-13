Gradle Android Unit Testing Plugin
==================================

A Gradle plugin which enables good 'ol fashioned unit tests for Android builds.


Usage
-----

Add the plugin to your `buildscript`'s `dependencies` section:
```groovy
classpath 'com.squareup.gradle:gradle-android-test-plugin:0.9.+'
```

Apply the `android-test` plugin:
```groovy
apply plugin: 'android-test'
```

Add test-only dependencies using the `testCompile` configuration:
```groovy
testCompile 'junit:junit:4.10'
testCompile 'org.roboelctric:robolectric:2.1.+'
testCompile 'com.squareup:fest-android:1.0.+'
```

Write your tests in `src/test/java/`! You can also add per-build type and per-flavor tests by using
the same folder naming conventions (e.g., `src/testFree/java/`, `src/testDebug/java/`).


Robolectric
-----------

The Android framework is not built with unit testing in mind. As such, the canonical framework to
facilitate unit testing on the JVM is [Robolectric][robo]. Version 2.2 of Robolectric will support
this plugin out of the box. Until then, you can use the following test runner:
```java
import java.lang.reflect.Method;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

public class RobolectricGradleTestRunner extends RobolectricTestRunner {
  private final Config overlay;

  public RobolectricGradleTestRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
    String manifestFile = System.getProperty("android.manifest", Config.DEFAULT);
    overlay = new Config.Implementation(-1, manifestFile, "", -1, new Class[0]);
  }

  @Override public Config getConfig(Method method) {
    Config config = super.getConfig(method);
    if (config.manifest().equals(Config.DEFAULT)) {
      config = new Config.Implementation(config, overlay);
    }
    return config;
  }
}
```

Just annotate your test classes with `@RunWith(RobolectricGradleTestRunner.class)` until version
2.2 is released.


Plugin Development
------------------

The `example/` dir contains a project which covers a few configurations for the plugin to work with.

 1. Run `./gradlew install` in the root. This will build the plugin and install it into a local Maven
    repository.
 2. In the `example/` folder, run `../gradlew clean check` to build and run the unit tests.
 3. Open `example/build/test-reports/index.html` in the browser.


License
-------

    Copyright 2013 Square, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



 [robo]: http://robolectric.org

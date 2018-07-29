# Versioning [![Build Status](https://travis-ci.org/tslamic/versioning.svg?branch=master)](https://travis-ci.org/tslamic/versioning)

A Grade plugin that makes versioning easier:

```groovy
android {
  defaultConfig {
    versionCode versioning.versionCode() // e.g. 42
    versionName versioning.versionName() // e.g. 1.0.0
  }
}
```

The only prerequisite is using Git as your VCS.

## How to use it

Build script snippet for plugins DSL for Gradle 2.1 and later:

```groovy
plugins {
  id "me.tadej.versioning" version "0.1.1"
}
```

Build script snippet for use in older Gradle versions or where dynamic configuration is required:

```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.me.tadej:versioning:0.1.1"
  }
}

apply plugin: "me.tadej.versioning"
```

## How does it work?

The plugin assumes you use [tags](https://git-scm.com/book/en/v2/Git-Basics-Tagging) to mark your releases. The version name corresponds to the git command:

```shell
$ git describe --tags --dirty --always
```

The version code is simply the number of commits:

```shell
$ git rev-list --count HEAD
```

## Decorators

You can tweak the version name and/or version code:

```groovy
versioning {
  decorateVersionCode { code -> code * 1000 }
  decorateVersionName { name -> "$name-SNAPSHOT" }
}
```

## License

    Copyright 2018 Tadej Slamic
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

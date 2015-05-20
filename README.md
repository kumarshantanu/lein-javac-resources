# lein-javac-resources

Leiningen plugin to work with resource files in `:java-source-paths`.

This plugin is meant to be used on projects with legacy Java code bases where
resource files are placed with Java source files.

## Usage

Include the plugin in the `:plugins` vector in `project.clj`:

```clojure
:plugins [[lein-javac-resources "0.1.1"]]
:omit-source true  ; excludes .java and .clj files from the generated JAR file
                   ; you may not want to set this unless all code is AOT-compiled
:hooks [leiningen.javac-resources]
```

## License

Copyright © 2014-2015 Shantanu Kumar

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

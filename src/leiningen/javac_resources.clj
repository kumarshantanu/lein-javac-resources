(ns leiningen.javac-resources
  (:require [clojure.java.io :as io]
            [leiningen.compile :as lcompile]
            [leiningen.javac :as ljavac]
            [robert.hooke :as hooke])
  (:import [java.io File]))

(defn- java-source?
  [^File f]
  (and (.isFile f)
       (-> ^File f
           (.getName)
           (.endsWith ".java"))))

(defn copy-resources
  "Copy non Java source files into compiled path"
  [project]
  (let [compile-path  (:compile-path project)
        java-src-dirs (:java-source-paths project)]
    (doseq [dir java-src-dirs
            ^File resource (filter #(and (.isFile %) (not (java-source? %)))
                                   (file-seq (io/file dir)))]
      (let [rel-resource (.substring (.getPath resource) (inc (count dir)))
            compiled (io/file compile-path rel-resource)]
        (when (>= (.lastModified resource) (.lastModified compiled))
          (.mkdirs (.getParentFile compiled))
          (io/copy resource compiled))))))

(defn compile-hook
  [task & args]
  (apply task args)
  (copy-resources (first args)))

(defn java-resources
  [project]
  (copy-resources project))

(defn activate
  []
  (hooke/add-hook #'lcompile/compile #'compile-hook))


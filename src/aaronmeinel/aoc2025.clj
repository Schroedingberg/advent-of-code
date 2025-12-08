(ns aaronmeinel.aoc2025
  (:gen-class)
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [aaronmeinel.01.solution :as sol-01]
            [aaronmeinel.02.part-1 :as sol-02-01]
            [aaronmeinel.02.part-2 :as sol-02-02])
  )



;; Read entire file as a single string
(defn read-input
  [filename]
  (slurp (io/resource filename)))

;; Read file as a vector of lines
(defn read-lines
  [filename]
  (vec (line-seq (io/reader (io/resource filename)))))

;; Read file as a vector of lines, removing empty lines
(defn read-lines-trim
  [filename]
  (->> (read-lines filename)
       (map str/trim)
       (remove str/blank?)
       vec))

;; Read file and parse each line as an integer
(defn read-ints
  [filename]
  (->> (read-lines-trim filename)
       (mapv parse-long)))

;; Read file as a grid (vector of vectors of characters)
(defn read-grid
  [filename]
  (->> (read-lines-trim filename)
       (mapv vec)))

;; Read file split by blank lines (useful for grouped data)
(defn read-blocks
  [filename]
  (->> (slurp (io/resource filename))
       str/trim
       (#(str/split % #"\n\n"))
       (mapv str/split-lines)))

;; Day 1
;; Part 1
(let [data (read-lines "01/input.txt")]
  (sol-01/count-zero-rotations data))
;; Part 2
(let [data (read-lines "01/input.txt")]
  (sol-01/count-zero-rotations-with-transient-zeros data))

;; Day 2
;; Part 1
(let [data (read-input "02/input.txt")]
  (sol-02-01/solve data ))

;; Part 2
(let [data (read-input "02/input.txt")]
  (sol-02-02/solve data))



(defn greet
  "Callable entry point to the application."
  [data]
  (println (str "Hello, " (or (:name data) "World") "!")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (greet {:name (first args)}))

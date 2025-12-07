(ns aoc.utils
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

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

;; Example usage:
;; Example usage:
(comment
  ;; Place your input file in resources/day01.txt

  (read-input "day01.txt")           ; => "whole file as string"
  (read-lines "day01.txt")           ; => ["line1" "line2" ...]
  (read-ints "day01.txt")            ; => [123 456 789]
  (read-grid "day01.txt")            ; => [[\# \. \.] [\. \# \.]]
  (read-blocks "day01.txt")          ; => [["block1" "line2"] ["block2"]]
  )

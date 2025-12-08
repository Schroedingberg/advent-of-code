(ns aaronmeinel.02.part-1
  (:require [clojure.string :as str]))

(defn parse
  [s]
  (->> (str/split s #",")
       (map str/trim)
       (map #(str/split % #"-"))
       (map (fn [[a b]] {:start (parse-long a) :end (parse-long b)}))))




(defn expand-range [{:keys [start end]}]
  (range start (inc end)))



(defn invalid-id? [id]
  (if-let [[m g] (re-matches #"^(\d+)\1$" id)]
    (parse-long m)
    0))


(defn solve [data]
  (let [data-as-ranges (->> data
                            parse
                            (map expand-range))]
    (->> data-as-ranges
         (map #(map str %))
         (map #(map invalid-id? %))
         (map #(reduce + 0 %))
         (reduce + 0)
         )))
(comment
  (def test-data "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
1698522-1698528,446443-446449,38593856-38593862,565653-565659,
824824821-824824827,2121212118-2121212124")

  (def test-result 1227775554)
  (def data
    (parse test-data))

  (def data-as-ranges
    (->> data
         (map expand-range)))

  (->> data-as-ranges
       (map #(map str %))
       (map #(map invalid-id? %))
       (map #(reduce + 0 %))
       (reduce + 0 )
       (= test-result)
       )


  (solve test-data)
  ())

(ns aaronmeinel.03.solution
  (:require [clojure.string :as str]))





(defn parse-digit-lines-vec
  [s]
  (->> (str/split-lines s)
       (map str/trim)
       (remove str/blank?)
       (mapv (fn [line]
               (mapv #(Character/digit % 10) line)))))


(defn get-top-two-set [data] (->>
                              data
                              (map sort)
                              (map reverse)
                              (map #(take 2 %))
                              (map set)))

(comment

  (def test-data "987654321111111
811111111111119
234234234234278
818181911112111")

  (def cleaned-data (parse-digit-lines-vec test-data))

(def sets (get-top-two-set cleaned-data))
(let [doit (fn [s d]
             (filter s d))]
  (map doit cleaned-data))

  ())

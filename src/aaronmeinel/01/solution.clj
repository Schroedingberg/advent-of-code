(ns aaronmeinel.01.solution
  (:require [clojure.string :as str]))



(defn parse [input-string]
  (let [[_ dir num] (re-matches #"([A-Z](\d+))" input-string)]
    {:direction (first dir)
     :distance (parse-long num)}))


(defn direction->operator [s]
  (let [arithmetic-operator (if (= s \L) - +)]
    arithmetic-operator))

(defn data->action [{:keys [direction distance]}]
  (let [op (direction->operator direction)]
    (fn [initial]  (mod (op initial distance) 100))))



(defn apply-chain [fns value]
  (reductions (fn [v f] (f v)) value fns))

(defn count-zero-rotations [data]
  (as-> data v
    (map parse v)
    (map data->action v)
    (apply-chain v 50)
    (filterv zero? v)
    (count v)))


(comment
  (def test-data ["R50", "L30", "R30"])


  (as-> test-data v
    (map parse v)
    (map data->action v)
    (apply-chain v 50)
    (filterv zero? v)
    (count v))

(count-zero-rotations test-data)
  ();;
  )

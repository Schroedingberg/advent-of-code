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



(defn data->action-v2 [{:keys [direction distance]}]
  (let [op (direction->operator direction)
        intermediate-zeros (quot distance 100)
        normalized-distance (mod distance 100)]
    (case direction
      \R (fn [{:keys [state transient-zero-count]}]
           {:transient-zero-count (+ (if (and
                                           (not (zero? state))
                                           (>= (+ state normalized-distance) 100)) 1 0) intermediate-zeros)
            :state (mod (op state normalized-distance) 100)})
      \L (fn [{:keys [state transient-zero-count]}]
           {:transient-zero-count (+ (if (and (not (zero? state))
                                              (<= (- state normalized-distance) 0)) 1 0) intermediate-zeros)
            :state (mod (op state normalized-distance) 100)}))))


(defn apply-chain [fns value]
  (reductions (fn [v f] (f v)) value fns))

(defn count-zero-rotations [data]
  (as-> data v
    (map parse v)
    (map data->action v)
    (apply-chain v 50 )
    (filterv zero? v)
    (count v)))



(defn count-zero-rotations-with-transient-zeros [data]
  (let [chain (as-> data v
                (map parse v)
                (map data->action-v2 v)
                (apply-chain v {:state 50 :transient-zero-count 0}))

        transient-zeros (->> chain
                             (map :transient-zero-count)
                             (reduce + 0))
        spot-on-zeros (->> chain
                           (remove (comp not zero? :transient-zero-count));; Dont count those that have already been summed up
                           (map :state)
                           (filter zero?)
                           count)]
    (+ transient-zeros spot-on-zeros)))

(comment
  (def test-data-01 ["R50", "L30", "R30"])

  (def test-data-02 ["R50"])



  (remove (comp zero? :state) [{:name "i have zero state" :state 0}, {:name "I have state>0" :state 10}])

  (quot (- 1 2) 100)
  (mod (- 1 2) 100)
  (mod 1 100)

  (as-> ["L68","L30","R48","L5","R60","L55","L1","L99","R14","L82"] v
    (map parse v)
    (map data->action-v2 v)
    (apply-chain v {:state 50 :transient-zero-count 0}))



  (as-> ["L50", "L400"] v
    (map parse v)
    (map data->action-v2 v)
    (apply-chain v {:state 50 :transient-zero-count 0})
    (group-by (comp zero? :state) v)
    )



  (as-> ["R49","L98"] v
    (map parse v)
    (map data->action-v2 v)
    (apply-chain v {:state 50 :transient-zero-count 0})
)

  (count-zero-rotations-with-transient-zeros ["L49", "L2"])
  (count-zero-rotations-with-transient-zeros ["R1000"])

  (as-> ["R100"] v
    (map parse v)
    (map data->action-v2 v)
    (apply-chain v {:state 50 :transient-zero-count 0}))
  (count-zero-rotations-with-transient-zeros  ["L68","L30","R48","L5","R60","L55","L1","L99","R14","L82"])
  (count-zero-rotations-with-transient-zeros ["R150"])
  (count-zero-rotations-with-transient-zeros ["L50", "R101"])

  (count-zero-rotations-with-transient-zeros ["R49", "L98"])
  (count-zero-rotations-with-transient-zeros ["R49", "R1"])
  (count-zero-rotations-with-transient-zeros ["R49" "R1" "R1"])
  (count-zero-rotations-with-transient-zeros ["R49" "R1" "L1"])
  (count-zero-rotations-with-transient-zeros ["L50" "L100"])
  (count-zero-rotations-with-transient-zeros ["R50" "R100"])
  (count-zero-rotations-with-transient-zeros ["L50" "L400"])
  (count-zero-rotations-with-transient-zeros ["L50", "R400"])
  (count-zero-rotations-with-transient-zeros ["R1000"])


  ();;

  )

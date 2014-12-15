(ns match-v-multimethod
  (:require [clojure.core.match :refer [match]]
            [clojure.test.check.generators :as gen]))

(def kws
  ;; test dispatch against 26 keywords (:a - :z)
  (map (comp keyword str char)
       (range (int \a) (inc (int \z)))))

#_
(def kws
  ;; test dispatch against 1000 random keywords
  (gen/sample gen/keyword 1000))

;; ----------------------------------------
;; multimethods
(defmulti mmtest
  (fn [x] (first x)))

;; defmethod for each of [:a ...] to [:z ...].
(dorun
 (map #(.addMethod mmtest % (constantly 1))
      kws))

;; ----------------------------------------
;; core.match
(def match-do (constantly 1))

(defmacro make-match-dispatch
  "[:a] (match-do)
   ...
   [:z] (match-do)
  "
  []
  `(match [~'(first x)]
          ~@(mapcat #(vector [%] '(match-do)) kws)))

(defn match-dispatch [x]
  (make-match-dispatch))

;; ----------------------------------------
;; FIGHT.
(defn fight!
  ([] (fight! 1000000))
  ([n]
     (let [data (gen/sample (gen/tuple (gen/elements kws) (gen/vector gen/int)) n)]
       (println :n (count data)) ;; This is here to realize all the data before we test dispatch times.
       (println :multimethod) (time (dorun (for [x data] (mmtest x))))
       (println :core.match) (time (dorun (for [x data] (match-dispatch x)))))))

(defn -main [& args]
  (fight! (or (Integer/parseInt (first args)) 1000000)))

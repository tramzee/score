(ns score.freq)

(def note-vals {\C 0 \D 2 \E 4 \F 5 \G 7 \A 9 \B 11} )

(defn- convert-modifier
  ([mod-str]
   (let [mod-len (.length mod-str)] 
     (loop [v 0 indx 0]
       (if (>= indx mod-len)
         v
         (let [c (get mod-str indx)]
           (case c
             \B (recur (+ v -1) (inc indx))
             \# (recur (+ v 1) (inc indx))
             (throw (Throwable. (str "Unknown note modifier: " c))))))))))

(defn keyword->notenum
  "Convert keyword to MIDI notenum (i.e. :C4 is 60, :C#4 is 61)"
  [sym]
  {:pre [(keyword? sym)]}
  (let [sym-str (.toUpperCase (name sym))
        sym-len (.length sym-str)
        note-name (get sym-str 0)
        note (note-vals note-name)
        modifier (.substring sym-str 1 (- sym-len 1)) 
        octave (Integer/parseInt (.substring sym-str (- sym-len 1))) ]
    (+ note (* 12 (- 4 octave)) 60 (convert-modifier modifier)) 
    ))

